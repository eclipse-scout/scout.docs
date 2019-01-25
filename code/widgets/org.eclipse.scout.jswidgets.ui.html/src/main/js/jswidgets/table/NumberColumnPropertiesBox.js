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
jswidgets.NumberColumnPropertiesBox = function() {
  jswidgets.NumberColumnPropertiesBox.parent.call(this);
  this.column = null;
};
scout.inherits(jswidgets.NumberColumnPropertiesBox, scout.GroupBox);

jswidgets.NumberColumnPropertiesBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.NumberColumnPropertiesBox');
};

jswidgets.NumberColumnPropertiesBox.prototype._init = function(model) {
  jswidgets.NumberColumnPropertiesBox.parent.prototype._init.call(this, model);

  this._setColumn(this.column);
};

jswidgets.NumberColumnPropertiesBox.prototype.setColumn = function(column) {
  this.setProperty('column', column);
};

jswidgets.NumberColumnPropertiesBox.prototype._setColumn = function(column) {
  this._setProperty('column', column);
  if (!this.column) {
    return;
  }

  var multiplierField = this.widget('MultiplierField');
  multiplierField.setValue(this.column.decimalFormat.multiplier);
  multiplierField.on('propertyChange', this._onPropertyChange.bind(this));

  var formatField = this.widget('FormatField');
  formatField.setValue(this.column.decimalFormat.pattern);
  formatField.on('propertyChange', this._onPropertyChange.bind(this));
};

jswidgets.NumberColumnPropertiesBox.prototype._onPropertyChange = function(event) {
  if (event.propertyName === 'value' && event.source.id === 'MultiplierField') {
    this.column.setDecimalFormat({
      pattern: this.column.decimalFormat.pattern,
      multiplier: event.newValue
    });
  }
  if (event.propertyName === 'value' && event.source.id === 'FormatField') {
    this.column.setDecimalFormat({
      pattern: event.newValue,
      multiplier: this.column.decimalFormat.multiplier
    });
  }
};
