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
import {arrays, comparators, Event, Form, FormModel, HtmlTile, HtmlTileModel, InitModelOf, Menu, models, scout, SmartField, TileGrid} from '@eclipse-scout/core';
import TileGridFormModel from './TileGridFormModel';
import $ from 'jquery';
import {CustomTile, CustomTileModel, TileGridFormWidgetMap} from '../index';

export class TileGridForm extends Form {
  declare widgetMap: TileGridFormWidgetMap;

  insertedTileCount: number;
  tileTypeField: SmartField<'default' | 'simple'>;
  tileGrid: TileGrid;

  constructor() {
    super();
    this.insertedTileCount = 0;
    this.tileTypeField = null;
  }

  protected override _jsonModel(): FormModel {
    return models.get(TileGridFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
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

  protected _onInsertMenuAction(event: Event<Menu>) {
    let tile = this._createTile();
    this.tileGrid.insertTile(tile);
  }

  protected _createTile(model?: HtmlTileModel | CustomTileModel): HtmlTile | CustomTile {
    let defaults;
    let tileType = this.tileTypeField.value;
    if (tileType === 'default') {
      defaults = {
        parent: this.tileGrid,
        content: 'New <i>Html Tile</i> ' + this.insertedTileCount++
      };
      model = $.extend({}, defaults, model);
      return scout.create(HtmlTile, model as InitModelOf<HtmlTile>);
    }
    defaults = {
      parent: this.tileGrid,
      label: 'New Tile ' + this.insertedTileCount++
    };
    model = $.extend({}, defaults, model);
    return scout.create(CustomTile, model as InitModelOf<CustomTile>);
  }

  protected _onInsertManyMenuAction(event: Event<Menu>) {
    let tiles = [];
    for (let i = 0; i < 50; i++) {
      tiles.push(this._createTile());
    }
    this.tileGrid.insertTiles(tiles);
  }

  protected _onDeleteMenuAction(event: Event<Menu>) {
    this.tileGrid.deleteTiles(this.tileGrid.selectedTiles);
    if (this.tileGrid.tiles.length === 0) {
      this.insertedTileCount = 0;
    }
  }

  protected _onSelectNextMenuAction(event: Event<Menu>) {
    if (this.tileGrid.filteredTiles.length === 0) {
      return;
    }
    let selectedTileIndex = arrays.findIndex(this.tileGrid.filteredTiles, tile => {
      return tile.selected;
    });
    this.tileGrid.selectTile(this.tileGrid.filteredTiles[selectedTileIndex + 1] || this.tileGrid.filteredTiles[0]);
  }

  protected _onSelectAllMenuAction(event: Event<Menu>) {
    this.tileGrid.selectAllTiles();
  }

  protected _onSortAscMenuAction(event: Event<Menu>) {
    this._sortTiles(true);
  }

  protected _onSortDescMenuAction(event: Event<Menu>) {
    this._sortTiles(false);
  }

  updateStatus() {
    this.widget('StatusField').setValue(this.session.text('TileGridStatus', this.tileGrid.tiles.length, this.tileGrid.filteredTiles.length, this.tileGrid.selectedTiles.length));
  }

  // noinspection DuplicatedCode
  protected _sortTiles(asc: boolean) {
    let comparator = comparators.ALPHANUMERIC;
    comparator.install(this.session);
    this.tileGrid.setComparator((tile1: (HtmlTile & { label?: string }) | CustomTile, tile2: (HtmlTile & { label?: string }) | CustomTile) => {
      let result = comparator.compare(tile1.label, tile2.label);
      if (!asc) {
        result = -result;
      }
      return result;
    });
    this.tileGrid.sort();
  }
}
