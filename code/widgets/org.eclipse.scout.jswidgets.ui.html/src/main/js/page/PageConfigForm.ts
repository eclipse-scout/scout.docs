/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, Page, PageWithTable} from '@eclipse-scout/core';
import {PageConfigFormData, PageConfigFormModel, PageConfigFormWidgetMap} from '../index';
import model from './PageConfigFormModel';

export class PageConfigForm extends Form implements PageConfigFormModel {
  declare model: PageConfigFormModel;
  declare widgetMap: PageConfigFormWidgetMap;
  declare data: PageConfigFormData;

  page: Page;

  constructor() {
    super();
    this.page = null;
  }

  protected override _jsonModel(): FormModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    // Allow save without changing anything
    this.widget('OkMenu').on('action', event => {
      this.touch();
    });

    // Ensure at least one checkbox is checked
    let detailFormCheckBox = this.widget('DetailFormVisibleCheckBox');
    let detailTableCheckBox = this.widget('DetailTableVisibleCheckBox');
    let ensureCheckboxHandler = event => {
      let checkedCount = (detailFormCheckBox.value ? 1 : 0) + (detailTableCheckBox.value ? 1 : 0);
      if (checkedCount === 1) {
        detailFormCheckBox.setEnabled(!detailFormCheckBox.value);
        detailTableCheckBox.setEnabled(!detailTableCheckBox.value);
      } else {
        detailFormCheckBox.setEnabled(true);
        detailTableCheckBox.setEnabled(true);
      }
    };
    detailFormCheckBox.on('propertyChange:value', ensureCheckboxHandler);
    detailTableCheckBox.on('propertyChange:value', ensureCheckboxHandler);

    this.widget('PageTypeField').on('propertyChange:value', event => {
      if (detailFormCheckBox.saveNeeded || detailTableCheckBox.saveNeeded) {
        return;
      }
      detailFormCheckBox.setValue(true);
      detailTableCheckBox.setValue(true);
      if (event.newValue === 'PageWithTable') {
        detailFormCheckBox.setValue(false);
      }
      detailFormCheckBox.markAsSaved();
      detailTableCheckBox.markAsSaved();
    });

    // Existing pages don't support changing certain flags
    this.widget('PageTypeField').setEnabled(!this.page);
    this.widget('DrillDownOnRowClickCheckBox').setEnabled(!this.page);
    this.widget('LazyExpandingEnabledCheckBox').setEnabled(!this.page);
  }

  override importData() {
    this.widget('NameField').setValue(this.data.name);
    this.widget('PageTypeField').setValue(this.data.pageType);
    this.widget('PageTypeField').modeSelector.selectModeByRef(this.data.pageType); // TODO [js-table] bsh: Check why ModeSelectorField#setValue and mandatory indicator is not implemented
    this.widget('DetailFormVisibleCheckBox').setValue(this.data.detailFormVisible);
    this.widget('DetailTableVisibleCheckBox').setValue(this.data.detailTableVisible);
    this.widget('LeafCheckBox').setValue(this.data.leaf);
    this.widget('DrillDownOnRowClickCheckBox').setValue(this.data.drillDownOnRowClick);
    this.widget('LazyExpandingEnabledCheckBox').setValue(this.data.lazyExpandingEnabled);

    // Don't show checkboxes for flags that cannot be changed and are currently not set
    if (!this.widget('DrillDownOnRowClickCheckBox').enabled && !this.data.drillDownOnRowClick) {
      this.widget('DrillDownOnRowClickCheckBox').setVisible(false);
    }
    if (!this.widget('LazyExpandingEnabledCheckBox').enabled && !this.data.lazyExpandingEnabled) {
      this.widget('LazyExpandingEnabledCheckBox').setVisible(false);
    }
  }

  override exportData(): PageConfigFormData {
    return {
      name: this.widget('NameField').value,
      pageType: this.widget('PageTypeField').value,
      detailFormVisible: this.widget('DetailFormVisibleCheckBox').value,
      detailTableVisible: this.widget('DetailTableVisibleCheckBox').value,
      leaf: this.widget('LeafCheckBox').value,
      drillDownOnRowClick: this.widget('DrillDownOnRowClickCheckBox').value,
      lazyExpandingEnabled: this.widget('LazyExpandingEnabledCheckBox').value
    };
  }

  protected override _load(): JQuery.Promise<PageConfigFormData> {
    if (this.page) {
      let data: PageConfigFormData = {
        name: this.page.text,
        pageType: this.page instanceof PageWithTable ? 'PageWithTable' : 'PageWithNodes',
        detailFormVisible: this.page.detailFormVisible,
        detailTableVisible: this.page.detailTableVisible,
        leaf: this.page.leaf,
        drillDownOnRowClick: this.page.drillDownOnRowClick,
        lazyExpandingEnabled: this.page.lazyExpandingEnabled
      };
      return $.resolvedPromise(data);
    }
    return super._load();
  }

  protected override _save(data: PageConfigFormData): JQuery.Promise<void> {
    if (this.page) {
      let page = this.page;
      page.text = data.name;
      page.detailFormVisible = data.detailFormVisible;
      page.detailTableVisible = data.detailTableVisible;
      page.leaf = data.leaf;
      page.drillDownOnRowClick = data.drillDownOnRowClick;
      page.lazyExpandingEnabled = data.lazyExpandingEnabled;
      let outline = page.getOutline();
      outline.updateNode(page);
      outline.pageChanged(page);
      return $.resolvedPromise();
    }
    return super._save(data);
  }
}
