/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {GroupBox, GroupBoxModel, InitModelOf, LookupCallColumn} from '@eclipse-scout/core';
import model, {LookupCallColumnPropertiesBoxWidgetMap} from './LookupCallColumnPropertiesBoxModel';

export class LookupCallColumnPropertiesBox extends GroupBox {
  declare widgetMap: LookupCallColumnPropertiesBoxWidgetMap;

  column: LookupCallColumn<any>;

  constructor() {
    super();
    this.column = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setColumn(this.column);
  }

  setColumn(column: LookupCallColumn<any>) {
    this.setProperty('column', column);
  }

  protected _setColumn(column: LookupCallColumn<any>) {
    this._setProperty('column', column);
    if (!this.column) {
      return;
    }

    this.widget('LookupCallField').setValue(this.column.lookupCall);
    this.widget('LookupCallField').on('propertyChange:value', event => this.column.setLookupCall(event.newValue));

    this.widget('BrowseMaxRowCountField').setValue(this.column.browseMaxRowCount);
    this.widget('BrowseMaxRowCountField').on('propertyChange:value', event => this.column.setBrowseMaxRowCount(event.newValue));

    this.widget('BrowseHierarchyField').setValue(this.column.browseHierarchy);
    this.widget('BrowseHierarchyField').on('propertyChange:value', event => this.column.setBrowseHierarchy(event.newValue));
  }
}
