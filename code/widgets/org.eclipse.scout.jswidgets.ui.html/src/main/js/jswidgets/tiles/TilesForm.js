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
  this.insertedTileCount = 0;
};
scout.inherits(jswidgets.TilesForm, scout.Form);

jswidgets.TilesForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TilesForm');
};

jswidgets.TilesForm.prototype._init = function(model) {
  jswidgets.TilesForm.parent.prototype._init.call(this, model);

  this.tiles = this.widget('Tiles');

  // -- Properties

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

  var maxContentWidthField = this.widget('MaxContentWidthField');
  maxContentWidthField.setValue(this.tiles.maxContentWidth);
  maxContentWidthField.on('propertyChange', this._onMaxContentWidthPropertyChange.bind(this));

  var withPlaceholdersField = this.widget('WithPlaceholdersField');
  withPlaceholdersField.setValue(this.tiles.withPlaceholders);
  withPlaceholdersField.on('propertyChange', this._onWithPlacehodersPropertyChange.bind(this));

  var colorSchemeField = this.widget('ColorSchemeField');
  colorSchemeField.setValue(this.tiles.tiles[0].colorScheme.scheme);
  colorSchemeField.on('propertyChange', this._onColorSchemePropertyChange.bind(this));

  var invertColorsField = this.widget('InvertColorsField');
  invertColorsField.setValue(this.tiles.tiles[0].colorScheme.inverted);
  invertColorsField.on('propertyChange', this._onInvertColorsPropertyChange.bind(this));

  var selectableField = this.widget('SelectableField');
  selectableField.setValue(this.tiles.selectable);
  selectableField.on('propertyChange', this._onSelectablePropertyChange.bind(this));

  var scrollableField = this.widget('ScrollableField');
  scrollableField.setValue(this.tiles.scrollable);
  scrollableField.on('propertyChange', this._onScrollablePropertyChange.bind(this));

  // -- Actions

  var insertButton = this.widget('InsertButton');
  insertButton.on('click', this._onInsertButtonClick.bind(this));

  var insertManyButton = this.widget('InsertManyButton');
  insertManyButton.on('click', this._onInsertManyButtonClick.bind(this));

  var deleteButton = this.widget('DeleteButton');
  deleteButton.on('click', this._onDeleteButtonClick.bind(this));

  var selectNextButton = this.widget('SelectNextButton');
  selectNextButton.on('click', this._onSelectNextButtonClick.bind(this));

  var selectAllButton = this.widget('SelectAllButton');
  selectAllButton.on('click', this._onSelectAllButtonClick.bind(this));
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

jswidgets.TilesForm.prototype._onMaxContentWidthPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tiles.setMaxContentWidth(event.newValue);
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

jswidgets.TilesForm.prototype._onSelectablePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tiles.setSelectable(event.newValue);
  }
};

jswidgets.TilesForm.prototype._onScrollablePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tiles.setScrollable(event.newValue);
  }
};

jswidgets.TilesForm.prototype._onInsertButtonClick = function(event) {
  var tile = new scout.create('jswidgets.SimpleTile', {
    parent: this.tiles,
    label: 'New Tile ' + this.insertedTileCount++
  });
  this.tiles.insertTile(tile);
};

jswidgets.TilesForm.prototype._onInsertManyButtonClick = function(event) {
  var tiles = [];
  for (var i = 0; i < 50; i++) {
    tiles.push(new scout.create('jswidgets.SimpleTile', {
      parent: this.tiles,
      label: 'New Tile ' + this.insertedTileCount++
    }));
  }
  this.tiles.insertTiles(tiles);
};

jswidgets.TilesForm.prototype._onDeleteButtonClick = function(event) {
  this.tiles.deleteTiles(this.tiles.selectedTiles);
};

jswidgets.TilesForm.prototype._onSelectNextButtonClick = function(event) {
  if (this.tiles.tiles.length === 0) {
    return;
  }
  var selectedTileIndex = scout.arrays.findIndex(this.tiles.tiles, function(tile) {
    return tile.selected;
  });
  this.tiles.selectTile(this.tiles.tiles[selectedTileIndex + 1] || this.tiles.tiles[0]);
};

jswidgets.TilesForm.prototype._onSelectAllButtonClick = function(event) {
  this.tiles.selectAllTiles();
};
