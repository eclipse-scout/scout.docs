/*******************************************************************************
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
jswidgets.DateColumnPropertiesBox = function() {
  jswidgets.DateColumnPropertiesBox.parent.call(this);
  this.column = null;
};
scout.inherits(jswidgets.DateColumnPropertiesBox, scout.GroupBox);

jswidgets.DateColumnPropertiesBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.DateColumnPropertiesBox');
};

jswidgets.DateColumnPropertiesBox.prototype._init = function(model) {
  jswidgets.DateColumnPropertiesBox.parent.prototype._init.call(this, model);

  this._setColumn(this.column);
};

jswidgets.DateColumnPropertiesBox.prototype.setColumn = function(column) {
  this.setProperty('column', column);
};

jswidgets.DateColumnPropertiesBox.prototype._setColumn = function(column) {
  this._setProperty('column', column);
  if (!this.column) {
    return;
  }

  var formatField = this.widget('FormatField');
  formatField.setValue(this.column.format.pattern);
  formatField.on('propertyChange', this._onPropertyChange.bind(this));

  var groupFormatField = this.widget('GroupFormatField');
  groupFormatField.setValue(this.column.groupFormat.pattern);
  groupFormatField.on('propertyChange', this._onPropertyChange.bind(this));

  var hasDateField = this.widget('HasDateField');
  hasDateField.setValue(this.column.hasDate);
  hasDateField.on('propertyChange', this._onPropertyChange.bind(this));

  var hasTimeField = this.widget('HasTimeField');
  hasTimeField.setValue(this.column.hasTime);
  hasTimeField.on('propertyChange', this._onPropertyChange.bind(this));
};

jswidgets.DateColumnPropertiesBox.prototype._onPropertyChange = function(event) {
  if (event.propertyName === 'value' && event.source.id === 'FormatField') {
    this.column.setFormat(event.newValue);
  }
  if (event.propertyName === 'value' && event.source.id === 'GroupFormatField') {
    this.column.setGroupFormat(event.newValue);
  }
  if (event.propertyName === 'value' && event.source.id === 'HasDateField') {
    this.column.hasDate = event.newValue;
    this.column.setFormat();
  }
  if (event.propertyName === 'value' && event.source.id === 'HasTimeField') {
    this.column.hasTime = event.newValue;
    this.column.setFormat();
  }
};
