/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import LabelFormModel from './LabelFormModel';
import {LabelFormWidgetMap} from '../index';
import {AppLinkActionEvent, Form, FormModel, GridData, GroupBox, InitModelOf, MessageBoxes, models} from '@eclipse-scout/core';

export class LabelForm extends Form {
  declare widgetMap: LabelFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(LabelFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let label = this.widget('Label');
    label.on('appLinkAction', this._onLabelAppLinkAction.bind(this));

    let valueField = this.widget('ValueField');
    valueField.setValue(label.value);
    valueField.on('propertyChange:value', event => this.widget('Label').setValue(event.newValue));

    let htmlEnabledField = this.widget('HtmlEnabledField');
    htmlEnabledField.setValue(label.htmlEnabled);
    htmlEnabledField.on('propertyChange:value', event => this.widget('Label').setHtmlEnabled(event.newValue));

    let scrollableField = this.widget('ScrollableField');
    scrollableField.setValue(label.scrollable);
    scrollableField.on('propertyChange:value', event => {
      let label = this.widget('Label');
      label.setScrollable(event.newValue);

      // Fix height if it is scrollable
      let parent = label.parent as GroupBox,
        gridData = new GridData(parent.gridDataHints);
      gridData.heightInPixel = label.scrollable ? 50 : -1;
      parent.setGridDataHints(gridData);
    });

    this.widget('WidgetActionsBox').setField(label);
    this.widget('EventsTab').setField(label);
  }

  protected _onLabelAppLinkAction(event: AppLinkActionEvent) {
    MessageBoxes.createOk(this)
      .withBody(this.session.text('ThanksForClickingMe'))
      .withYes(this.session.text('YoureWelcome'))
      .buildAndOpen();
  }
}
