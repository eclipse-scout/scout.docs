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
import {GroupBox, models} from '@eclipse-scout/core';
import WidgetPopupPropertiesBoxModel from './WidgetPopupPropertiesBoxModel';

export default class WidgetPopupPropertiesBox extends GroupBox {

constructor() {
  super();
  this.field = null;
}


_jsonModel() {
  return models.get(WidgetPopupPropertiesBoxModel);
}

_init(model) {
  super._init( model);

  this._setField(this.field);
}

setField(field) {
  this.setProperty('field', field);
}

_setField(field) {
  this._setProperty('field', field);
  this.setEnabled(this.field);
  if (!this.field) {
    return;
  }
  var closableField = this.widget('ClosableField');
  closableField.setValue(this.field.closable);
  closableField.on('propertyChange', this._onPropertyChange.bind(this));

  var movableField = this.widget('MovableField');
  movableField.setValue(this.field.movable);
  movableField.on('propertyChange', this._onPropertyChange.bind(this));

  var resizableField = this.widget('ResizableField');
  resizableField.setValue(this.field.resizable);
  resizableField.on('propertyChange', this._onPropertyChange.bind(this));
}

_onPropertyChange(event) {
  if (event.propertyName === 'value' && event.source.id === 'ClosableField') {
    this.field.setClosable(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'MovableField') {
    this.field.setMovable(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'ResizableField') {
    this.field.setResizable(event.newValue);
  }
}
}
