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
jswidgets.LogicalGridLayoutConfigBox = function() {
  jswidgets.LogicalGridLayoutConfigBox.parent.call(this);
  this.field = null;
};
scout.inherits(jswidgets.LogicalGridLayoutConfigBox, scout.GroupBox);

jswidgets.LogicalGridLayoutConfigBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.LogicalGridLayoutConfigBox');
};

jswidgets.LogicalGridLayoutConfigBox.prototype._init = function(model) {
  jswidgets.LogicalGridLayoutConfigBox.parent.prototype._init.call(this, model);

  this._setField(this.field);
  this.widget('HGapField').on('propertyChange', this._onPropertyChange.bind(this));
  this.widget('VGapField').on('propertyChange', this._onPropertyChange.bind(this));
  this.widget('RowHeightField').on('propertyChange', this._onPropertyChange.bind(this));
  this.widget('ColumnWidthField').on('propertyChange', this._onPropertyChange.bind(this));
  this.widget('MinWidthField').on('propertyChange', this._onPropertyChange.bind(this));
};

jswidgets.LogicalGridLayoutConfigBox.prototype.setField = function(field) {
  this.setProperty('field', field);
};

jswidgets.LogicalGridLayoutConfigBox.prototype._setField = function(field) {
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }
  this.reloadLogicalGridLayoutConfig();
};

jswidgets.LogicalGridLayoutConfigBox.prototype.reloadLogicalGridLayoutConfig = function() {
  var layoutConfig = this.getLayoutConfig();
  this.widget('HGapField').setValue(layoutConfig.hgap);
  this.widget('VGapField').setValue(layoutConfig.vgap);
  this.widget('RowHeightField').setValue(layoutConfig.rowHeight);
  this.widget('ColumnWidthField').setValue(layoutConfig.columnWidth);
  this.widget('MinWidthField').setValue(layoutConfig.minWidth);
};

jswidgets.LogicalGridLayoutConfigBox.prototype._onPropertyChange = function(event) {
  if (event.propertyName !== 'value') {
    return;
  }
  var layoutConfig = this.getLayoutConfig().clone();
  this._fillLayoutConfigByEvent(layoutConfig, event);
  this.setLayoutConfig(layoutConfig);
};

jswidgets.LogicalGridLayoutConfigBox.prototype._fillLayoutConfigByEvent = function(layoutConfig, event) {
  if (event.source.id === 'HGapField') {
    layoutConfig.hgap = event.newValue;
  } else if (event.source.id === 'VGapField') {
    layoutConfig.vgap = event.newValue;
  } else if (event.source.id === 'RowHeightField') {
    layoutConfig.rowHeight = event.newValue;
  } else if (event.source.id === 'ColumnWidthField') {
    layoutConfig.columnWidth = event.newValue;
  } else if (event.source.id === 'MinWidthField') {
    layoutConfig.minWidth = event.newValue;
  }
};

jswidgets.LogicalGridLayoutConfigBox.prototype.getLayoutConfig = function() {
  return this.field.layoutConfig;
};

jswidgets.LogicalGridLayoutConfigBox.prototype.setLayoutConfig = function(layoutConfig) {
  this.field.setLayoutConfig(layoutConfig);
};
