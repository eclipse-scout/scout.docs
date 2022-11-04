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
import {GroupBox, GroupBoxModel, InitModelOf, models, SmartField} from '@eclipse-scout/core';
import SmartFieldPropertiesBoxModel from './SmartFieldPropertiesBoxModel';
import {SmartFieldPropertiesBoxWidgetMap} from '../index';

export class SmartFieldPropertiesBox extends GroupBox {
  declare widgetMap: SmartFieldPropertiesBoxWidgetMap;

  field: SmartField<any>;

  constructor() {
    super();
    this.field = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(SmartFieldPropertiesBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setField(this.field);
  }

  setField(field: SmartField<any>) {
    this.setProperty('field', field);
  }

  protected _setField(field: SmartField<any>) {
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }

    let lookupCallField = this.widget('LookupCallField');
    lookupCallField.setValue(this.field.lookupCall);
    lookupCallField.on('propertyChange:value', event => this.field.setLookupCall(event.newValue));
    this.field.on('propertyChange:lookupCall', event => this.widget('LookupCallField').setValue(event.newValue));

    let displayStyleField = this.widget('DisplayStyleField');
    displayStyleField.setValue(this.field.displayStyle);
    displayStyleField.on('propertyChange:value', event => {
      this.field.displayStyle = event.newValue;
      (this.field.parent as GroupBox).rerenderControls();
    });

    let browseMaxRowCountField = this.widget('BrowseMaxRowCountField');
    browseMaxRowCountField.setValue(this.field.browseMaxRowCount);
    browseMaxRowCountField.on('propertyChange:value', event => this.field.setBrowseMaxRowCount(event.newValue));

    let searchRequiredField = this.widget('SearchRequiredField');
    searchRequiredField.setValue(this.field.searchRequired);
    searchRequiredField.on('propertyChange:value', event => this.field.setSearchRequired(event.newValue));

    let activeFilterEnabledField = this.widget('ActiveFilterEnabledField');
    activeFilterEnabledField.setValue(this.field.activeFilterEnabled);
    activeFilterEnabledField.on('propertyChange:value', event => this.field.setActiveFilterEnabled(event.newValue));

    let activeFilterField = this.widget('ActiveFilterField');
    activeFilterField.setValue(this.field.activeFilter);
    activeFilterField.on('propertyChange:value', event => this.field.setActiveFilter(event.newValue));
  }
}
