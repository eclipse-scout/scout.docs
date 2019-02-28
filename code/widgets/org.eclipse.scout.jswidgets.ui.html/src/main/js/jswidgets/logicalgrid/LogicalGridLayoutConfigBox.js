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
  // layout defaults are only known after the widget got rendered.
  field.one('render', this.initLayoutDefaults.bind(this));
};

jswidgets.LogicalGridLayoutConfigBox.prototype.initLayoutDefaults = function() {
  var bodyLayout = this.getBodyLayout();
  this.widget('HGapField').setValue(bodyLayout.hgap);
  this.widget('VGapField').setValue(bodyLayout.vgap);
  this.widget('RowHeightField').setValue(bodyLayout.rowHeight);
  this.widget('ColumnWidthField').setValue(bodyLayout.columnWidth);
  this.widget('MinWidthField').setValue(bodyLayout.minWidth);
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

/**
 * Return the body layout of the widget. Used to initialized the config box with the default values.
 */
jswidgets.LogicalGridLayoutConfigBox.prototype.getBodyLayout = function() {
  return this.field.htmlBody.layout;
};

jswidgets.LogicalGridLayoutConfigBox.prototype.getLayoutConfig = function() {
  return this.field.layoutConfig;
};

jswidgets.LogicalGridLayoutConfigBox.prototype.setLayoutConfig = function(layoutConfig) {
  this.field.setLayoutConfig(layoutConfig);
};
