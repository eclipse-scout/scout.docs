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
import {BooleanColumn, GroupBox, GroupBoxModel, InitModelOf, models} from '@eclipse-scout/core';
import BooleanColumnPropertiesBoxModel from './BooleanColumnPropertiesBoxModel';
import {BooleanColumnPropertiesBoxWidgetMap} from '../index';

export class BooleanColumnPropertiesBox extends GroupBox {
  declare widgetMap: BooleanColumnPropertiesBoxWidgetMap;

  column: BooleanColumn;

  constructor() {
    super();
    this.column = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(BooleanColumnPropertiesBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setColumn(this.column);
  }

  setColumn(column: BooleanColumn) {
    this.setProperty('column', column);
  }

  protected _setColumn(column: BooleanColumn) {
    this._setProperty('column', column);
    if (!this.column) {
      return;
    }

    let triStateEnabledField = this.widget('TriStateEnabledField');
    triStateEnabledField.setValue(this.column.triStateEnabled);
    triStateEnabledField.on('propertyChange:value', event => this.column.setTriStateEnabled(event.newValue));
  }
}
