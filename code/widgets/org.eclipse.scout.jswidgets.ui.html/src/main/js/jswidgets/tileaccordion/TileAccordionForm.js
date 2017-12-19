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
jswidgets.TileAccordionForm = function() {
  jswidgets.TileAccordionForm.parent.call(this);
  this.insertedGroupCount = 0;
  this.insertedTilesCount = 0;
};
scout.inherits(jswidgets.TileAccordionForm, scout.Form);

jswidgets.TileAccordionForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TileAccordionForm');
};

jswidgets.TileAccordionForm.prototype._init = function(model) {
  jswidgets.TileAccordionForm.parent.prototype._init.call(this, model);

  this.accordion = this.widget('Accordion');

  this._insertGroupWithTiles();
  this._insertGroupWithTiles();
  this._insertGroupWithTiles();

  // -- Properties

  var exclusiveExpandField = this.widget('ExclusiveExpandField');
  exclusiveExpandField.setValue(this.accordion.exclusiveExpand);
  exclusiveExpandField.on('propertyChange', this._onExclusiveExpandPropertyChange.bind(this));

  var scrollableField = this.widget('ScrollableField');
  scrollableField.setValue(this.accordion.scrollable);
  scrollableField.on('propertyChange', this._onScrollablePropertyChange.bind(this));

  var selectableField = this.widget('SelectableField');
  selectableField.setValue(this.accordion.selectable);
  selectableField.on('propertyChange', this._onSelectablePropertyChange.bind(this));

  var multiSelectField = this.widget('MultiSelectField');
  multiSelectField.setValue(this.accordion.multiSelect);
  multiSelectField.on('propertyChange', this._onMultiSelectPropertyChange.bind(this));

  var withPlaceholdersField = this.widget('WithPlaceholdersField');
  withPlaceholdersField.setValue(this.accordion.withPlaceholders);
  withPlaceholdersField.on('propertyChange', this._onWithPlacehodersPropertyChange.bind(this));

  var gridColumnCountField = this.widget('GridColumnCountField');
  gridColumnCountField.setValue(this.accordion.gridColumnCount);
  gridColumnCountField.on('propertyChange', this._onGridColumnCountPropertyChange.bind(this));


  // -- Actions

  var insertMenu = this.widget('InsertMenu');
  insertMenu.on('action', this._onInsertMenuAction.bind(this));

  var deleteFirstMenu = this.widget('DeleteFirstMenu');
  deleteFirstMenu.on('action', this._onDeleteFirstMenuAction.bind(this));

  var insertTileIntoGroup0Menu = this.widget('InsertTileIntoGroup0Menu');
  insertTileIntoGroup0Menu.on('action', this._onInsertTileIntoGroup0MenuAction.bind(this));

  var insertTileIntoGroup1Menu = this.widget('InsertTileIntoGroup1Menu');
  insertTileIntoGroup1Menu.on('action', this._onInsertTileIntoGroup1MenuAction.bind(this));

  var deleteAllSelectedTilesMenu = this.widget('DeleteSelectedTilesMenu');
  deleteAllSelectedTilesMenu.on('action', this._onDeleteAllSelectedTilesMenuAction.bind(this));

  var selectNextMenu = this.widget('SelectNextMenu');
  selectNextMenu.on('action', this._onSelectNextMenuAction.bind(this));

  var selectAllMenu = this.widget('SelectAllMenu');
  selectAllMenu.on('action', this._onSelectAllMenuAction.bind(this));

  var sortAscMenu = this.widget('SortAscMenu');
  sortAscMenu.on('action', this._onSortAscMenuAction.bind(this));

  var sortDescMenu = this.widget('SortDescMenu');
  sortDescMenu.on('action', this._onSortDescMenuAction.bind(this));

  var accordionField = this.widget('AccordionField');
  this.widget('FormFieldPropertiesBox').setField(accordionField);
  this.widget('GridDataBox').setField(accordionField);
};

jswidgets.TileAccordionForm.prototype._onExclusiveExpandPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.accordion.setExclusiveExpand(event.newValue);
  }
};

jswidgets.TileAccordionForm.prototype._onScrollablePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.accordion.setScrollable(event.newValue);
  }
};

jswidgets.TileAccordionForm.prototype._onGridColumnCountPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.accordion.setGridColumnCount(event.newValue);
  }
};

jswidgets.TileAccordionForm.prototype._onWithPlacehodersPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.accordion.setWithPlaceholders(event.newValue);
  }
};

jswidgets.TileAccordionForm.prototype._onSelectablePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.accordion.setSelectable(event.newValue);
  }
};

jswidgets.TileAccordionForm.prototype._onMultiSelectPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.accordion.setMultiSelect(event.newValue);
  }
};

jswidgets.TileAccordionForm.prototype._onInsertMenuAction = function(event) {
  this._insertGroupWithTiles();
};

jswidgets.TileAccordionForm.prototype._onDeleteFirstMenuAction = function(event) {
  this.accordion.deleteGroup(this.accordion.groups[0]);
  if (this.accordion.groups.length === 0) {
    this.insertedGroupCount = 0;
  }
};

jswidgets.TileAccordionForm.prototype._onSortAscMenuAction = function(event) {
  this._sortTiles(true);
};

jswidgets.TileAccordionForm.prototype._onSortDescMenuAction = function(event) {
  this._sortTiles();
};

jswidgets.TileAccordionForm.prototype._onDeleteAllSelectedTilesMenuAction = function(event) {
  this.accordion.deleteTiles(this.accordion.getSelectedTiles());
};

jswidgets.TileAccordionForm.prototype._onInsertTileIntoGroup0MenuAction = function(event) {
  if (this.accordion.groups.length > 0) {
    this.accordion.groups[0].body.insertTile(this._createTile({
      label: 'New tile ' + this.insertedTilesCount++
    }));
  }
};

jswidgets.TileAccordionForm.prototype._onInsertTileIntoGroup1MenuAction = function(event) {
  if (this.accordion.groups.length > 1) {
    this.accordion.groups[1].body.insertTile(this._createTile({
      label: 'New tile ' + this.insertedTilesCount++
    }));
  }
};

jswidgets.TileAccordionForm.prototype._onSelectNextMenuAction = function(event) {
  var filteredTiles = this.accordion.getFilteredTiles();
  if (filteredTiles.length === 0) {
    return;
  }
  var selectedTileIndex = scout.arrays.findIndex(filteredTiles, function(tile) {
    return tile.selected;
  });
  this.accordion.selectTile(filteredTiles[selectedTileIndex + 1] || filteredTiles[0]);
};

jswidgets.TileAccordionForm.prototype._onSelectAllMenuAction = function(event) {
  this.accordion.selectAllTiles();
};

jswidgets.TileAccordionForm.prototype._insertGroupWithTiles = function() {
  var tiles = [];
  var maxTiles = Math.floor(Math.random() * 30);
  for (var i = 0; i < maxTiles; i++) {
    tiles.push(this._createTile({
      label: 'Tile ' + i
    }));
  }
  var group = new scout.create('Group', {
    parent: this.accordion,
    title: 'Group ' + this.insertedGroupCount++,
    body: {
      objectType: 'TileGrid',
      gridColumnCount: 6,
      layoutConfig: {
        columnWidth: 100,
        rowHeight: 100
      },
      scrollable: false,
      tiles: tiles
    }
  });
  this.accordion.insertGroup(group);
};

jswidgets.TileAccordionForm.prototype._createTile = function(model) {
  var defaults = {
    parent: this,
    gridDataHints: {
      weightX: 0
    },
    colorScheme: this.accordion.groups.length % 2 === 0 ? 'default' : 'alternative'
  };
  model = $.extend({}, defaults, model);
  return scout.create('jswidgets.SimpleTile', model);
};

jswidgets.TileAccordionForm.prototype._sortTiles = function(asc) {
  var comparator = scout.comparators.ALPHANUMERIC;
  comparator.install(this.session);
  this.accordion.setTileComparator(function(tile1, tile2) {
    var result = comparator.compare(tile1.label, tile2.label);
    if (!asc) {
      result = -result;
    }
    return result;
  });
  this.accordion.sortTiles();
};
