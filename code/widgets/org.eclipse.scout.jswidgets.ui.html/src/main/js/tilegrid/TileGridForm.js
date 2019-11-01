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
import {Form, models, arrays, scout, comparators} from '@eclipse-scout/core';
import {CustomTile} from '../index';
import TileGridFormModel from './TileGridFormModel';
import * as $ from 'jquery';

export default class TileGridForm extends Form {

  constructor() {
    super();
    this.insertedTileCount = 0;
    this.tileFilter = null;
  }


  _jsonModel() {
    return models.get(TileGridFormModel);
  }

  _init(model) {
    super._init(model);

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

    var virtualField = this.widget('VirtualField');
    virtualField.setValue(this.tileGrid.virtual);
    virtualField.on('propertyChange', this._onVirtualPropertyChange.bind(this));

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
    var layoutConfigBox = this.widget('LayoutConfigBox');
    layoutConfigBox.getBodyLayout = function() {
      return this.field.htmlComp.layout;
    };
    layoutConfigBox.setField(this.tileGrid);
    this.widget('FormFieldPropertiesBox').setField(tileField);
    this.widget('GridDataBox').setField(tileField);
    this.widget('WidgetActionsBox').setField(tileField);
    this.widget('EventsTab').setField(this.tileGrid);
    this.updateStatus();
    this._updateHasCustomTiles();
  }

  _onTileGridPropertyChange(event) {
    if (event.propertyName === 'tiles' || event.propertyName === 'selectedTiles' || event.propertyName === 'filteredTiles') {
      this.updateStatus();
    }
    if (event.propertyName === 'filteredTiles') {
      this._updateHasCustomTiles();
    }
  }

  _onFilterPropertyChange(event) {
    if (event.propertyName === 'displayText') {
      this._filterTilesByText(event.newValue);
    }
  }

  _onGridColumnCountPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.tileGrid.setGridColumnCount(event.newValue);
    }
  }

  _onLogicalGridChange(event) {
    if (event.propertyName === 'value') {
      this.tileGrid.setLogicalGrid(event.newValue);
    }
  }

  _onWithPlacehodersPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.tileGrid.setWithPlaceholders(event.newValue);
    }
  }

  _onVirtualPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.tileGrid.setVirtual(event.newValue);
    }
  }

  _onColorSchemePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.tileGrid.tiles.forEach(function(tile) {
        var scheme = $.extend({}, tile.colorScheme, {
          scheme: event.newValue
        });
        tile.setColorScheme(scheme);
      });
    }
  }

  _onInvertColorsPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.tileGrid.tiles.forEach(function(tile) {
        var scheme = $.extend({}, tile.colorScheme, {
          inverted: event.newValue
        });
        tile.setColorScheme(scheme);
      });
    }
  }

  _onSelectablePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.tileGrid.setSelectable(event.newValue);
    }
  }

  _onMultiSelectPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.tileGrid.setMultiSelect(event.newValue);
    }
  }

  _onScrollablePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.tileGrid.setScrollable(event.newValue);
    }
  }

  _onInsertMenuAction(event) {
    var tile = this._createTile();
    this.tileGrid.insertTile(tile);
  }

  _createTile(model) {
    var defaults;
    var tileType = this.widget('TileTypeField').value;
    if (tileType === 'default') {
      defaults = {
        parent: this.tileGrid,
        content: 'New <i>Html Tile</i> ' + this.insertedTileCount++
      };
      model = $.extend({}, defaults, model);
      return new scout.create('HtmlTile', model);
    }
    defaults = {
      parent: this.tileGrid,
      label: 'New Tile ' + this.insertedTileCount++
    };
    model = $.extend({}, defaults, model);
    return new scout.create('jswidgets.CustomTile', model);
  }

  _onInsertManyMenuAction(event) {
    var tiles = [];
    for (var i = 0; i < 50; i++) {
      tiles.push(this._createTile());
    }
    this.tileGrid.insertTiles(tiles);
  }

  _updateHasCustomTiles(event) {
    this.tileGrid.toggleCssClass('has-custom-tiles', this.tileGrid.filteredTiles.some(function(tile) {
      return tile instanceof CustomTile;
    }));
  }

  _onDeleteMenuAction(event) {
    this.tileGrid.deleteTiles(this.tileGrid.selectedTiles);
    if (this.tileGrid.tiles.length === 0) {
      this.insertedTileCount = 0;
    }
  }

  _onSelectNextMenuAction(event) {
    if (this.tileGrid.filteredTiles.length === 0) {
      return;
    }
    var selectedTileIndex = arrays.findIndex(this.tileGrid.filteredTiles, function(tile) {
      return tile.selected;
    });
    this.tileGrid.selectTile(this.tileGrid.filteredTiles[selectedTileIndex + 1] || this.tileGrid.filteredTiles[0]);
  }

  _onSelectAllMenuAction(event) {
    this.tileGrid.selectAllTiles();
  }

  _onSortAscMenuAction(event) {
    this._sortTiles(true);
  }

  _onSortDescMenuAction(event) {
    this._sortTiles();
  }

  _filterTilesByText(text) {
    if (text) {
      if (!this.tileFilter) {
        this.tileFilter = scout.create('jswidgets.CustomTileFilter');
        this.tileGrid.addFilter(this.tileFilter);
      }
      this.tileFilter.setText(text);
    } else {
      this.tileGrid.removeFilter(this.tileFilter);
      this.tileFilter = null;
    }
    this.tileGrid.filter();
  }

  updateStatus() {
    this.widget('StatusField').setValue(this.session.text('TileGridStatus', this.tileGrid.tiles.length, this.tileGrid.filteredTiles.length, this.tileGrid.selectedTiles.length));
  }

  _sortTiles(asc) {
    var comparator = comparators.ALPHANUMERIC;
    comparator.install(this.session);
    this.tileGrid.setComparator(function(tile1, tile2) {
      var result = comparator.compare(tile1.label, tile2.label);
      if (!asc) {
        result = -result;
      }
      return result;
    });
    this.tileGrid.sort();
  }
}
