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
jswidgets.TileFieldForm = function() {
  jswidgets.TileFieldForm.parent.call(this);
  this.insertedTileCount = 0;
  this.tileFilter = null;
};
scout.inherits(jswidgets.TileFieldForm, scout.Form);

jswidgets.TileFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TileFieldForm');
};

jswidgets.TileFieldForm.prototype._init = function(model) {
  jswidgets.TileFieldForm.parent.prototype._init.call(this, model);

  this.tileGrid = this.widget('TileGrid');
  this.tileGrid.on('propertyChange', this._onTileGridPropertyChange.bind(this));

  var filterField = this.widget('FilterField');
  filterField.on('propertyChange', this._onFilterPropertyChange.bind(this));

  // -- Properties

  var gridColumnCountField = this.widget('GridColumnCountField');
  gridColumnCountField.setValue(this.tileGrid.gridColumnCount);
  gridColumnCountField.on('propertyChange', this._onGridColumnCountPropertyChange.bind(this));

  var logicalGridField = this.widget('LogicalGridField');
  logicalGridField.setValue(this.tileGrid.logicalGrid ? this.tileGrid.logicalGrid.objectType : null);
  logicalGridField.on('propertyChange', this._onLogicalGridChange.bind(this));

  var withPlaceholdersField = this.widget('WithPlaceholdersField');
  withPlaceholdersField.setValue(this.tileGrid.withPlaceholders);
  withPlaceholdersField.on('propertyChange', this._onWithPlacehodersPropertyChange.bind(this));

  var colorSchemeField = this.widget('ColorSchemeField');
  colorSchemeField.setValue(this.tileGrid.tiles[0].colorScheme.scheme);
  colorSchemeField.on('propertyChange', this._onColorSchemePropertyChange.bind(this));

  var invertColorsField = this.widget('InvertColorsField');
  invertColorsField.setValue(this.tileGrid.tiles[0].colorScheme.inverted);
  invertColorsField.on('propertyChange', this._onInvertColorsPropertyChange.bind(this));

  var selectableField = this.widget('SelectableField');
  selectableField.setValue(this.tileGrid.selectable);
  selectableField.on('propertyChange', this._onSelectablePropertyChange.bind(this));

  var multiSelectField = this.widget('MultiSelectField');
  multiSelectField.setValue(this.tileGrid.multiSelect);
  multiSelectField.on('propertyChange', this._onMultiSelectPropertyChange.bind(this));

  var scrollableField = this.widget('ScrollableField');
  scrollableField.setValue(this.tileGrid.scrollable);
  scrollableField.on('propertyChange', this._onScrollablePropertyChange.bind(this));

  // -- Actions

  var insertMenu = this.widget('InsertMenu');
  insertMenu.on('action', this._onInsertMenuAction.bind(this));

  var insertManyMenu = this.widget('InsertManyMenu');
  insertManyMenu.on('action', this._onInsertManyMenuAction.bind(this));

  var deleteMenu = this.widget('DeleteMenu');
  deleteMenu.on('action', this._onDeleteMenuAction.bind(this));

  var selectNextMenu = this.widget('SelectNextMenu');
  selectNextMenu.on('action', this._onSelectNextMenuAction.bind(this));

  var selectAllMenu = this.widget('SelectAllMenu');
  selectAllMenu.on('action', this._onSelectAllMenuAction.bind(this));

  var sortAscMenu = this.widget('SortAscMenu');
  sortAscMenu.on('action', this._onSortAscMenuAction.bind(this));

  var sortDescMenu = this.widget('SortDescMenu');
  sortDescMenu.on('action', this._onSortDescMenuAction.bind(this));

  var tileField = this.widget('TileField');
  this.widget('LayoutConfigBox').setField(this.tileGrid);
  this.widget('FormFieldPropertiesBox').setField(tileField);
  this.widget('GridDataBox').setField(tileField);
  this.updateStatus();
};

jswidgets.TileFieldForm.prototype._onTileGridPropertyChange = function(event) {
  if (event.propertyName === 'tiles' || event.propertyName === 'selectedTiles' || event.propertyName === 'filteredTiles') {
    this.updateStatus();
  }
};

jswidgets.TileFieldForm.prototype._onFilterPropertyChange = function(event) {
  if (event.propertyName === 'displayText') {
    this.filterTilesByText(event.newValue);
  }
};

jswidgets.TileFieldForm.prototype._onGridColumnCountPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tileGrid.setGridColumnCount(event.newValue);
  }
};

jswidgets.TileFieldForm.prototype._onLogicalGridChange = function(event) {
  if (event.propertyName === 'value') {
    this.tileGrid.setLogicalGrid(event.newValue);
  }
};

jswidgets.TileFieldForm.prototype._onWithPlacehodersPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tileGrid.setWithPlaceholders(event.newValue);
  }
};

jswidgets.TileFieldForm.prototype._onColorSchemePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tileGrid.tiles.forEach(function(tile) {
      var scheme = $.extend({}, tile.colorScheme, {
        scheme: event.newValue
      });
      tile.setColorScheme(scheme);
    });
  }
};

jswidgets.TileFieldForm.prototype._onInvertColorsPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tileGrid.tiles.forEach(function(tile) {
      var scheme = $.extend({}, tile.colorScheme, {
        inverted: event.newValue
      });
      tile.setColorScheme(scheme);
    });
  }
};

jswidgets.TileFieldForm.prototype._onSelectablePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tileGrid.setSelectable(event.newValue);
  }
};

jswidgets.TileFieldForm.prototype._onMultiSelectPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tileGrid.setMultiSelect(event.newValue);
  }
};

jswidgets.TileFieldForm.prototype._onScrollablePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tileGrid.setScrollable(event.newValue);
  }
};

jswidgets.TileFieldForm.prototype._onInsertMenuAction = function(event) {
  var tile = new scout.create('jswidgets.SimpleTile', {
    parent: this.tileGrid,
    label: 'New Tile ' + this.insertedTileCount++
  });
  this.tileGrid.insertTile(tile);
};

jswidgets.TileFieldForm.prototype._onInsertManyMenuAction = function(event) {
  var tiles = [];
  for (var i = 0; i < 50; i++) {
    tiles.push(new scout.create('jswidgets.SimpleTile', {
      parent: this.tileGrid,
      label: 'New Tile ' + this.insertedTileCount++
    }));
  }
  this.tileGrid.insertTiles(tiles);
};

jswidgets.TileFieldForm.prototype._onDeleteMenuAction = function(event) {
  this.tileGrid.deleteTiles(this.tileGrid.selectedTiles);
  if (this.tileGrid.tiles.length === 0) {
    this.insertedTileCount = 0;
  }
};

jswidgets.TileFieldForm.prototype._onSelectNextMenuAction = function(event) {
  if (this.tileGrid.filteredTiles.length === 0) {
    return;
  }
  var selectedTileIndex = scout.arrays.findIndex(this.tileGrid.filteredTiles, function(tile) {
    return tile.selected;
  });
  this.tileGrid.selectTile(this.tileGrid.filteredTiles[selectedTileIndex + 1] || this.tileGrid.filteredTiles[0]);
};

jswidgets.TileFieldForm.prototype._onSelectAllMenuAction = function(event) {
  this.tileGrid.selectAllTiles();
};

jswidgets.TileFieldForm.prototype._onSortAscMenuAction = function(event) {
  this._sortTiles(true);
};

jswidgets.TileFieldForm.prototype._onSortDescMenuAction = function(event) {
  this._sortTiles();
};

jswidgets.TileFieldForm.prototype.filterTilesByText = function(text) {
  if (text) {
    if (!this.tileFilter) {
      this.tileFilter = scout.create('jswidgets.SimpleTileFilter');
      this.tileGrid.addFilter(this.tileFilter);
    }
    this.tileFilter.setText(text);
  } else {
    this.tileGrid.removeFilter(this.tileFilter);
    this.tileFilter = null;
  }
  this.tileGrid.filter();
};

jswidgets.TileFieldForm.prototype.updateStatus = function() {
  this.widget('StatusField').setValue(this.session.text('TileGridStatus', this.tileGrid.tiles.length, this.tileGrid.filteredTiles.length, this.tileGrid.selectedTiles.length));
};

jswidgets.TileFieldForm.prototype._sortTiles = function(asc) {
  var comparator = scout.comparators.ALPHANUMERIC;
  comparator.install(this.session);
  this.tileGrid.setComparator(function(tile1, tile2) {
    var result = comparator.compare(tile1.label, tile2.label);
    if (!asc) {
      result = -result;
    }
    return result;
  });
  this.tileGrid.sort();
};
