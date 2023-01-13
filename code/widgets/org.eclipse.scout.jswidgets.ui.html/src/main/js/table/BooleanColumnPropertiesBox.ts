/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
