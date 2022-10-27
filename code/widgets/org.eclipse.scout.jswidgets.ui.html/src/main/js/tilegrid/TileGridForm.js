/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {arrays, comparators, Form, models, scout} from '@eclipse-scout/core';
import TileGridFormModel from './TileGridFormModel';
import $ from 'jquery';

export default class TileGridForm extends Form {

  constructor() {
    super();
    this.insertedTileCount = 0;
    this.tileFilter = null;
    this.tileTypeField = null;
  }

  _jsonModel() {
    return models.get(TileGridFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    this.tileTypeField = this.widget('TileTypeField');

    this.tileGrid = this.widget('TileGrid');
    this.tileGrid.on('propertyChange:tiles propertyChange:selectedTiles propertyChange:filteredTiles', event => this.updateStatus());

    // -- Properties

    let gridColumnCountField = this.widget('GridColumnCountField');
    gridColumnCountField.setValue(this.tileGrid.gridColumnCount);
    gridColumnCountField.on('propertyChange:value', event => this.tileGrid.setGridColumnCount(event.newValue));

    let logicalGridField = this.widget('LogicalGridField');
    logicalGridField.setValue(this.tileGrid.logicalGrid ? this.tileGrid.logicalGrid.objectType : null);
    logicalGridField.on('propertyChange:value', event => this.tileGrid.setLogicalGrid(event.newValue));

    let withPlaceholdersField = this.widget('WithPlaceholdersField');
    withPlaceholdersField.setValue(this.tileGrid.withPlaceholders);
    withPlaceholdersField.on('propertyChange:value', event => this.tileGrid.setWithPlaceholders(event.newValue));

    let virtualField = this.widget('VirtualField');
    virtualField.setValue(this.tileGrid.virtual);
    virtualField.on('propertyChange:value', event => this.tileGrid.setVirtual(event.newValue));

    let textFilterEnabledField = this.widget('TextFilterEnabledField');
    textFilterEnabledField.setValue(this.tileGrid.textFilterEnabled);
    textFilterEnabledField.on('propertyChange:value', event => this.tileGrid.setTextFilterEnabled(event.newValue));

    let colorSchemeField = this.widget('ColorSchemeField');
    colorSchemeField.setValue(this.tileGrid.tiles[0].colorScheme.scheme);
    colorSchemeField.on('propertyChange:value', event => this.tileGrid.tiles.forEach(tile => {
      let scheme = $.extend({}, tile.colorScheme, {
        scheme: event.newValue
      });
      tile.setColorScheme(scheme);
    }));

    let invertColorsField = this.widget('InvertColorsField');
    invertColorsField.setValue(this.tileGrid.tiles[0].colorScheme.inverted);
    invertColorsField.on('propertyChange:value', event => this.tileGrid.tiles.forEach(tile => {
      let scheme = $.extend({}, tile.colorScheme, {
        inverted: event.newValue
      });
      tile.setColorScheme(scheme);
    }));

    let selectableField = this.widget('SelectableField');
    selectableField.setValue(this.tileGrid.selectable);
    selectableField.on('propertyChange:value', event => this.tileGrid.setSelectable(event.newValue));

    let multiSelectField = this.widget('MultiSelectField');
    multiSelectField.setValue(this.tileGrid.multiSelect);
    multiSelectField.on('propertyChange:value', event => this.tileGrid.setMultiSelect(event.newValue));

    let scrollableField = this.widget('ScrollableField');
    scrollableField.setValue(this.tileGrid.scrollable);
    scrollableField.on('propertyChange:value', event => this.tileGrid.setScrollable(event.newValue));

    // -- Actions

    let insertMenu = this.widget('InsertMenu');
    insertMenu.on('action', this._onInsertMenuAction.bind(this));

    let insertManyMenu = this.widget('InsertManyMenu');
    insertManyMenu.on('action', this._onInsertManyMenuAction.bind(this));

    let deleteMenu = this.widget('DeleteMenu');
    deleteMenu.on('action', this._onDeleteMenuAction.bind(this));

    let selectNextMenu = this.widget('SelectNextMenu');
    selectNextMenu.on('action', this._onSelectNextMenuAction.bind(this));

    let selectAllMenu = this.widget('SelectAllMenu');
    selectAllMenu.on('action', this._onSelectAllMenuAction.bind(this));

    let sortAscMenu = this.widget('SortAscMenu');
    sortAscMenu.on('action', this._onSortAscMenuAction.bind(this));

    let sortDescMenu = this.widget('SortDescMenu');
    sortDescMenu.on('action', this._onSortDescMenuAction.bind(this));

    let tileField = this.widget('TileField');
    let layoutConfigBox = this.widget('LayoutConfigBox');
    layoutConfigBox.getBodyLayout = function() {
      return this.field.htmlComp.layout;
    };
    layoutConfigBox.setField(this.tileGrid);
    this.widget('FormFieldPropertiesBox').setField(tileField);
    this.widget('GridDataBox').setField(tileField);
    this.widget('WidgetActionsBox').setField(tileField);
    this.widget('FormFieldActionsBox').setField(tileField);
    this.widget('EventsTab').setField(this.tileGrid);
    this.updateStatus();
  }

  _onInsertMenuAction(event) {
    let tile = this._createTile();
    this.tileGrid.insertTile(tile);
  }

  _createTile(model) {
    let defaults;
    let tileType = this.tileTypeField.value;
    if (tileType === 'default') {
      defaults = {
        parent: this.tileGrid,
        content: 'New <i>Html Tile</i> ' + this.insertedTileCount++
      };
      model = $.extend({}, defaults, model);
      return scout.create('HtmlTile', model);
    }
    defaults = {
      parent: this.tileGrid,
      label: 'New Tile ' + this.insertedTileCount++
    };
    model = $.extend({}, defaults, model);
    return scout.create('jswidgets.CustomTile', model);
  }

  _onInsertManyMenuAction(event) {
    let tiles = [];
    for (let i = 0; i < 50; i++) {
      tiles.push(this._createTile());
    }
    this.tileGrid.insertTiles(tiles);
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
    let selectedTileIndex = arrays.findIndex(this.tileGrid.filteredTiles, tile => {
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

  updateStatus() {
    this.widget('StatusField').setValue(this.session.text('TileGridStatus', this.tileGrid.tiles.length, this.tileGrid.filteredTiles.length, this.tileGrid.selectedTiles.length));
  }

  // noinspection DuplicatedCode
  _sortTiles(asc) {
    let comparator = comparators.ALPHANUMERIC;
    comparator.install(this.session);
    this.tileGrid.setComparator((tile1, tile2) => {
      let result = comparator.compare(tile1.label, tile2.label);
      if (!asc) {
        result = -result;
      }
      return result;
    });
    this.tileGrid.sort();
  }
}
