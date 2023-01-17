/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import TreeSmartFieldPropertiesBoxModel from './TreeSmartFieldPropertiesBoxModel';
import {GroupBoxModel, InitModelOf, models, SmartField} from '@eclipse-scout/core';
import {SmartFieldPropertiesBox, TreeSmartFieldPropertiesBoxWidgetMap} from '../../index';

export class TreeSmartFieldPropertiesBox extends SmartFieldPropertiesBox {
  declare widgetMap: TreeSmartFieldPropertiesBoxWidgetMap;

  constructor() {
    super();
    this.field = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.extend(TreeSmartFieldPropertiesBoxModel, super._jsonModel());
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setField(this.field);
    this.widget('DisplayStyleField').setVisible(false);
  }

  override setField(field: SmartField<any>) {
    this.setProperty('field', field);
  }

  protected override _setField(field: SmartField<any>) {
    super._setField(field);
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }

    let browseAutoExpandAllField = this.widget('BrowseAutoExpandAllField');
    browseAutoExpandAllField.setValue(this.field.browseAutoExpandAll);
    browseAutoExpandAllField.on('propertyChange:value', event => this.field.setBrowseAutoExpandAll(event.newValue));

    let browseLoadIncrementalField = this.widget('BrowseLoadIncrementalField');
    browseLoadIncrementalField.setValue(this.field.browseLoadIncremental);
    browseLoadIncrementalField.on('propertyChange:value', event => this.field.setBrowseLoadIncremental(event.newValue));
  }
}
