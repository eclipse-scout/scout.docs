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
import {GroupBox, GroupBoxModel, InitModelOf, models, WidgetPopup} from '@eclipse-scout/core';
import WidgetPopupPropertiesBoxModel from './WidgetPopupPropertiesBoxModel';
import {WidgetPopupPropertiesBoxWidgetMap} from '../index';

export class WidgetPopupPropertiesBox extends GroupBox {
  declare widgetMap: WidgetPopupPropertiesBoxWidgetMap;

  field: WidgetPopup;

  constructor() {
    super();
    this.field = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(WidgetPopupPropertiesBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setField(this.field);
  }

  setField(field: WidgetPopup) {
    this.setProperty('field', field);
  }

  protected _setField(field: WidgetPopup) {
    this._setProperty('field', field);
    this.setEnabled(!!this.field);
    if (!this.field) {
      return;
    }
    let closableField = this.widget('ClosableField');
    closableField.setValue(this.field.closable);
    closableField.on('propertyChange:value', event => this.field.setClosable(event.newValue));

    let movableField = this.widget('MovableField');
    movableField.setValue(this.field.movable);
    movableField.on('propertyChange:value', event => this.field.setMovable(event.newValue));

    let resizableField = this.widget('ResizableField');
    resizableField.setValue(this.field.resizable);
    resizableField.on('propertyChange:value', event => this.field.setResizable(event.newValue));
  }
}
