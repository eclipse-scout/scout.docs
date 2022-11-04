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
