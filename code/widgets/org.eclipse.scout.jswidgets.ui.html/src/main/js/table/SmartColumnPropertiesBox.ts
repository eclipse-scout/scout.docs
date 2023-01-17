/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {GroupBox, GroupBoxModel, InitModelOf, models, SmartColumn} from '@eclipse-scout/core';
import SmartColumnPropertiesBoxModel from './SmartColumnPropertiesBoxModel';
import {SmartColumnPropertiesBoxWidgetMap} from '../index';

export class SmartColumnPropertiesBox extends GroupBox {
  declare widgetMap: SmartColumnPropertiesBoxWidgetMap;

  column: SmartColumn<any>;

  constructor() {
    super();
    this.column = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(SmartColumnPropertiesBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setColumn(this.column);
  }

  setColumn(column: SmartColumn<any>) {
    this.setProperty('column', column);
  }

  protected _setColumn(column: SmartColumn<any>) {
    this._setProperty('column', column);
    if (!this.column) {
      return;
    }

    let browseMaxRowCountField = this.widget('BrowseMaxRowCountField');
    browseMaxRowCountField.setValue(this.column.browseMaxRowCount);
    browseMaxRowCountField.on('propertyChange:value', event => this.column.setBrowseMaxRowCount(event.newValue));
  }
}
