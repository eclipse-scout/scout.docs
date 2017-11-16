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
  this.tileFilter = null;
};
scout.inherits(jswidgets.TilesForm, scout.Form);

jswidgets.TilesForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TilesForm');
};

jswidgets.TilesForm.prototype._init = function(model) {
  jswidgets.TilesForm.parent.prototype._init.call(this, model);

  this.tiles = this.widget('Tiles');
  this.tiles.on('propertyChange', this._onTilesPropertyChange.bind(this));

  var filterField = this.widget('FilterField');
  filterField.on('propertyChange', this._onFilterPropertyChange.bind(this));

  // -- Properties

  var gridColumnCountField = this.widget('GridColumnCountField');
  gridColumnCountField.setValue(this.tiles.gridColumnCount);
  gridColumnCountField.on('propertyChange', this._onGridColumnCountPropertyChange.bind(this));

  var logicalGridField = this.widget('LogicalGridField');
  logicalGridField.setValue(this.tiles.logicalGrid ? this.tiles.logicalGrid.objectType : null);
  logicalGridField.on('propertyChange', this._onLogicalGridChange.bind(this));

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

  var multiSelectField = this.widget('MultiSelectField');
  multiSelectField.setValue(this.tiles.multiSelect);
  multiSelectField.on('propertyChange', this._onMultiSelectPropertyChange.bind(this));

  var scrollableField = this.widget('ScrollableField');
  scrollableField.setValue(this.tiles.scrollable);
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

  var tilesField = this.widget('TilesField');
  this.widget('LayoutConfigBox').setField(this.tiles);
  this.widget('FormFieldPropertiesBox').setField(tilesField);
  this.widget('GridDataBox').setField(tilesField);
  this.updateStatus();
};

jswidgets.TilesForm.prototype._onTilesPropertyChange = function(event) {
  if (event.propertyName === 'tiles' || event.propertyName === 'selectedTiles' || event.propertyName === 'filteredTiles') {
    this.updateStatus();
  }
};

jswidgets.TilesForm.prototype._onFilterPropertyChange = function(event) {
  if (event.propertyName === 'displayText') {
    this.filterTilesByText(event.newValue);
  }
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

jswidgets.TilesForm.prototype._onMultiSelectPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tiles.setMultiSelect(event.newValue);
  }
};

jswidgets.TilesForm.prototype._onScrollablePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.tiles.setScrollable(event.newValue);
  }
};

jswidgets.TilesForm.prototype._onInsertMenuAction = function(event) {
  var tile = new scout.create('jswidgets.SimpleTile', {
    parent: this.tiles,
    label: 'New Tile ' + this.insertedTileCount++
  });
  this.tiles.insertTile(tile);
};

jswidgets.TilesForm.prototype._onInsertManyMenuAction = function(event) {
  var tiles = [];
  for (var i = 0; i < 50; i++) {
    tiles.push(new scout.create('jswidgets.SimpleTile', {
      parent: this.tiles,
      label: 'New Tile ' + this.insertedTileCount++
    }));
  }
  this.tiles.insertTiles(tiles);
};

jswidgets.TilesForm.prototype._onDeleteMenuAction = function(event) {
  this.tiles.deleteTiles(this.tiles.selectedTiles);
  if (this.tiles.tiles.length === 0) {
    this.insertedTileCount = 0;
  }
};

jswidgets.TilesForm.prototype._onSelectNextMenuAction = function(event) {
  if (this.tiles.tiles.length === 0) {
    return;
  }
  var selectedTileIndex = scout.arrays.findIndex(this.tiles.tiles, function(tile) {
    return tile.selected;
  });
  this.tiles.selectTile(this.tiles.tiles[selectedTileIndex + 1] || this.tiles.tiles[0]);
};

jswidgets.TilesForm.prototype._onSelectAllMenuAction = function(event) {
  this.tiles.selectAllTiles();
};

jswidgets.TilesForm.prototype._onSortAscMenuAction = function(event) {
  this._sortTiles(true);
};

jswidgets.TilesForm.prototype._onSortDescMenuAction = function(event) {
  this._sortTiles();
};

jswidgets.TilesForm.prototype.filterTilesByText = function(text) {
  if (text) {
    if (!this.tileFilter) {
      this.tileFilter = scout.create('jswidgets.SimpleTileFilter');
      this.tiles.addFilter(this.tileFilter);
    }
    this.tileFilter.setText(text);
  } else {
    this.tiles.removeFilter(this.tileFilter);
    this.tileFilter = null;
  }
  this.tiles.filter();
};

jswidgets.TilesForm.prototype.updateStatus = function() {
  this.widget('StatusField').setValue(this.session.text('TilesStatus', this.tiles.tiles.length, this.tiles.filteredTiles.length, this.tiles.selectedTiles.length));
};

jswidgets.TilesForm.prototype._sortTiles = function(asc) {
  var tiles = this.tiles.tiles.slice();
  var comparator = scout.comparators.ALPHANUMERIC;
  comparator.install(this.session);
  tiles.sort(function(tile1, tile2) {
    var result = comparator.compare(tile1.label, tile2.label);
    if (!asc) {
      result = -result;
    }
    return result;
  });
  this.tiles.setTiles(tiles);
};
