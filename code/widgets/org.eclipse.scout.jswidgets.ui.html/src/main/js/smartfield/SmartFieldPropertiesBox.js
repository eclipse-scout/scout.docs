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
import SmartFieldPropertiesBoxModel from './SmartFieldPropertiesBoxModel';

export default class SmartFieldPropertiesBox extends GroupBox {

constructor() {
  super();
  this.field = null;
}


_jsonModel() {
  return models.get(SmartFieldPropertiesBoxModel);
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
  if (!this.field) {
    return;
  }

  var lookupCallField = this.widget('LookupCallField');
  lookupCallField.setValue(this.field.lookupCall);
  lookupCallField.on('propertyChange', this._onLookupCallPropertyChange.bind(this));
  this.field.on('propertyChange', this._onSmartFieldChange.bind(this));

  var displayStyleField = this.widget('DisplayStyleField');
  displayStyleField.setValue(this.field.displayStyle);
  displayStyleField.on('propertyChange', this._onDisplayStylePropertyChange.bind(this));

  var browseMaxRowCountField = this.widget('BrowseMaxRowCountField');
  browseMaxRowCountField.setValue(this.field.browseMaxRowCount);
  browseMaxRowCountField.on('propertyChange', this._onBrowseMaxRowCountPropertyChange.bind(this));

  var activeFilterEnabledField = this.widget('ActiveFilterEnabledField');
  activeFilterEnabledField.setValue(this.field.activeFilterEnabled);
  activeFilterEnabledField.on('propertyChange', this._onActiveFilterEnabledPropertyChange.bind(this));

  var searchRequiredField = this.widget('SearchRequiredField');
  searchRequiredField.setValue(this.field.searchRequired);
  searchRequiredField.on('propertyChange', this._onSearchRequiredPropertyChange.bind(this));
}

_onSmartFieldChange(event) {
  if (event.propertyName === 'lookupCall') {
    this.widget('LookupCallField').setValue(event.newValue);
  }
}

_onLookupCallPropertyChange(event) {
  if (event.propertyName === 'value') {
    this.field.setLookupCall(event.newValue);
  }
}

_onDisplayStylePropertyChange(event) {
  if (event.propertyName === 'value') {
    this.field.displayStyle = event.newValue;
    this.field.parent.rerenderControls();
    // Validate layout immediately to prevent flickering
    this.field.parent.htmlBody.validateLayoutTree();
  }
}

_onBrowseMaxRowCountPropertyChange(event) {
  if (event.propertyName === 'value') {
    this.field.setBrowseMaxRowCount(event.newValue);
  }
}

_onActiveFilterEnabledPropertyChange(event) {
  if (event.propertyName === 'value') {
    this.field.setActiveFilterEnabled(event.newValue);
  }
}

_onSearchRequiredPropertyChange(event) {
  if (event.propertyName === 'value') {
    this.field.setSearchRequired(event.newValue);
  }
}
}
