/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import LabelFormModel from './LabelFormModel';
import {LabelFormWidgetMap} from '../index';
import {Form, FormModel, GridData, GroupBox, InitModelOf, LabelAppLinkActionEvent, MessageBoxes, models} from '@eclipse-scout/core';

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

  protected _onLabelAppLinkAction(event: LabelAppLinkActionEvent) {
    MessageBoxes.createOk(this)
      .withBody(this.session.text('ThanksForClickingMe'))
      .withYes(this.session.text('YoureWelcome'))
      .buildAndOpen();
  }
}
