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
jswidgets.TilesForm = function() {
  jswidgets.TilesForm.parent.call(this);
};
scout.inherits(jswidgets.TilesForm, scout.Form);

jswidgets.TilesForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TilesForm');
};

jswidgets.TilesForm.prototype._init = function(model) {
  jswidgets.TilesForm.parent.prototype._init.call(this, model);

  this.tiles = this.widget('Tiles');

  var gridColumnCountField = this.widget('GridColumnCountField');
  gridColumnCountField.setValue(this.tiles.gridColumnCount);
  gridColumnCountField.on('propertyChange', this._onGridColumnCountPropertyChange.bind(this));

  var logicalGridField = this.widget('LogicalGridField');
  logicalGridField.setValue(this.tiles.logicalGrid ? this.tiles.logicalGrid.objectType : null);
  logicalGridField.on('propertyChange', this._onLogicalGridChange.bind(this));

  var logicalGridColumnWidthField = this.widget('LogicalGridColumnWidthField');
  logicalGridColumnWidthField.setValue(this.tiles.logicalGridColumnWidth);
  logicalGridColumnWidthField.on('propertyChange', this._onLogicalGridColumnWidthPropertyChange.bind(this));

  var logicalGridRowHeightField = this.widget('LogicalGridRowHeightField');
  logicalGridRowHeightField.setValue(this.tiles.logicalGridRowHeight);
  logicalGridRowHeightField.on('propertyChange', this._onLogicalGridRowHeightPropertyChange.bind(this));

  var withPlaceholdersField = this.widget('WithPlaceholdersField');
  withPlaceholdersField.setValue(this.tiles.withPlaceholders);
  withPlaceholdersField.on('propertyChange', this._onWithPlacehodersPropertyChange.bind(this));

  var colorSchemeField = this.widget('ColorSchemeField');
  colorSchemeField.setValue(this.tiles.tiles[0].colorScheme.scheme);
  colorSchemeField.on('propertyChange', this._onColorSchemePropertyChange.bind(this));

  var invertColorsField = this.widget('InvertColorsField');
  invertColorsField.setValue(this.tiles.tiles[0].colorScheme.inverted);
  invertColorsField.on('propertyChange', this._onInvertColorsPropertyChange.bind(this));
};

jswidgets.TilesForm.prototype._onGridColumnCountPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tiles.setGridColumnCount(event.newValue);
  }
};

jswidgets.TilesForm.prototype._onLogicalGridChange = function(event) {
  if (event.propertyName === 'value') {
    this.tiles.setLogicalGrid(event.newValue);
  }
};

jswidgets.TilesForm.prototype._onLogicalGridColumnWidthPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tiles.setLogicalGridColumnWidth(event.newValue);
  }
};

jswidgets.TilesForm.prototype._onLogicalGridRowHeightPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tiles.setLogicalGridRowHeight(event.newValue);
  }
};

jswidgets.TilesForm.prototype._onWithPlacehodersPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tiles.setWithPlaceholders(event.newValue);
  }
};

jswidgets.TilesForm.prototype._onColorSchemePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tiles.tiles.forEach(function(tile) {
      var scheme = $.extend({}, tile.colorScheme, {
        scheme: event.newValue
      });
      tile.setColorScheme(scheme);
    });
  }
};

jswidgets.TilesForm.prototype._onInvertColorsPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tiles.tiles.forEach(function(tile) {
      var scheme = $.extend({}, tile.colorScheme, {
        inverted: event.newValue
      });
      tile.setColorScheme(scheme);
    });
  }
};
