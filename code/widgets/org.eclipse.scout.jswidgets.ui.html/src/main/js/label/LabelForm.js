/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import LabelFormModel from './LabelFormModel';
import {Form, MessageBoxes, GridData, models} from '@eclipse-scout/core';

export default class LabelForm extends Form {

constructor() {
  super();
}


_jsonModel() {
  return models.get(LabelFormModel);
}

_init(model) {
  super._init( model);

  var label = this.widget('Label');
  label.on('appLinkAction', this._onLabelAppLinkAction.bind(this));

  var valueField = this.widget('ValueField');
  valueField.setValue(label.value);
  valueField.on('propertyChange', this._onValuePropertyChange.bind(this));

  var htmlEnabledField = this.widget('HtmlEnabledField');
  htmlEnabledField.setValue(label.htmlEnabled);
  htmlEnabledField.on('propertyChange', this._onHtmlEnabledPropertyChange.bind(this));

  var scrollableField = this.widget('ScrollableField');
  scrollableField.setValue(label.scrollable);
  scrollableField.on('propertyChange', this._onScrollablePropertyChange.bind(this));

  this.widget('WidgetActionsBox').setField(label);
  this.widget('EventsTab').setField(label);
}

_onValuePropertyChange(event) {
  if (event.propertyName === 'value') {
    this.widget('Label').setValue(event.newValue);
  }
}

_onHtmlEnabledPropertyChange(event) {
  if (event.propertyName === 'value') {
    this.widget('Label').setHtmlEnabled(event.newValue);
  }
}

_onScrollablePropertyChange(event) {
  if (event.propertyName === 'value') {
    var label = this.widget('Label');
    label.setScrollable(event.newValue);

    // Fix height if it is scrollable
    var gridData = new GridData(label.parent.gridDataHints);
    gridData.heightInPixel = label.scrollable ? 50 : -1;
    label.parent.setGridDataHints(gridData);
  }
}

_onLabelAppLinkAction(event) {
  MessageBoxes.createOk(this)
    .withBody(this.session.text('ThanksForClickingMe'))
    .withYes(this.session.text('YoureWelcome'))
    .buildAndOpen();
}
}
