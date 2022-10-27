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
import TileAccordionFormModel from './TileAccordionFormModel';
import {GroupLookupCall} from '../index';
import $ from 'jquery';

export default class TileAccordionForm extends Form {

  constructor() {
    super();
    this.insertedGroupCount = 0;
    this.insertedTilesCount = 0;
    this.tileFilter = null;
    this.tileTypeField = null;
  }

  _jsonModel() {
    return models.get(TileAccordionFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    this.tileTypeField = this.widget('InsertTileTypeField');

    this.accordion = this.widget('Accordion');
    this.accordion.on('propertyChange:tiles propertyChange:selectedTiles', event => this._updateStatus());
    this.accordion.on('propertyChange:filteredTiles', event => {
      this._updateStatus();
      this._updateGroupVisibility();
    });

    this._insertGroupWithTiles();
    this._insertGroupWithTiles();
    this._insertGroupWithTiles();

    // -- Properties

    let exclusiveExpandField = this.widget('ExclusiveExpandField');
    exclusiveExpandField.setValue(this.accordion.exclusiveExpand);
    exclusiveExpandField.on('propertyChange:value', event => this.accordion.setExclusiveExpand(event.newValue));

    let scrollableField = this.widget('ScrollableField');
    scrollableField.setValue(this.accordion.scrollable);
    scrollableField.on('propertyChange:value', event => this.accordion.setScrollable(event.newValue));

    let selectableField = this.widget('SelectableField');
    selectableField.setValue(this.accordion.selectable);
    selectableField.on('propertyChange:value', event => this.accordion.setSelectable(event.newValue));

    let multiSelectField = this.widget('MultiSelectField');
    multiSelectField.setValue(this.accordion.multiSelect);
    multiSelectField.on('propertyChange:value', event => this.accordion.setMultiSelect(event.newValue));

    let withPlaceholdersField = this.widget('WithPlaceholdersField');
    withPlaceholdersField.setValue(this.accordion.withPlaceholders);
    withPlaceholdersField.on('propertyChange:value', event => this.accordion.setWithPlaceholders(event.newValue));

    let virtualField = this.widget('VirtualField');
    virtualField.setValue(this.accordion.virtual);
    virtualField.on('propertyChange:value', event => this.accordion.setVirtual(event.newValue));

    let textFilterEnabledField = this.widget('TextFilterEnabledField');
    textFilterEnabledField.setValue(this.accordion.textFilterEnabled);
    textFilterEnabledField.on('propertyChange:value', event => this.accordion.setTextFilterEnabled(event.newValue));

    let gridColumnCountField = this.widget('GridColumnCountField');
    gridColumnCountField.setValue(this.accordion.gridColumnCount);
    gridColumnCountField.on('propertyChange:value', event => this.accordion.setGridColumnCount(event.newValue));

    // -- Actions

    let insertMenu = this.widget('InsertMenu');
    insertMenu.on('action', this._onInsertMenuAction.bind(this));

    let deleteFirstMenu = this.widget('DeleteFirstMenu');
    deleteFirstMenu.on('action', this._onDeleteFirstMenuAction.bind(this));

    let insertTileIntoGroup0Menu = this.widget('InsertTileIntoGroup0Menu');
    insertTileIntoGroup0Menu.on('action', this._onInsertTileIntoGroup0MenuAction.bind(this));

    let insertTileIntoGroup1Menu = this.widget('InsertTileIntoGroup1Menu');
    insertTileIntoGroup1Menu.on('action', this._onInsertTileIntoGroup1MenuAction.bind(this));

    let deleteAllSelectedTilesMenu = this.widget('DeleteSelectedTilesMenu');
    deleteAllSelectedTilesMenu.on('action', this._onDeleteAllSelectedTilesMenuAction.bind(this));

    let selectNextMenu = this.widget('SelectNextMenu');
    selectNextMenu.on('action', this._onSelectNextMenuAction.bind(this));

    let selectAllMenu = this.widget('SelectAllMenu');
    selectAllMenu.on('action', this._onSelectAllMenuAction.bind(this));

    let sortAscMenu = this.widget('SortAscMenu');
    sortAscMenu.on('action', this._onSortAscMenuAction.bind(this));

    let sortDescMenu = this.widget('SortDescMenu');
    sortDescMenu.on('action', this._onSortDescMenuAction.bind(this));

    let insertTileTargetField = this.widget('InsertTileTargetField');
    insertTileTargetField.setLookupCall(new GroupLookupCall(this.accordion));
    insertTileTargetField.setValue(this.accordion.groups[0]);

    let insertTileButton = this.widget('InsertTileButton');
    insertTileButton.on('click', this._onInsertTileButtonClick.bind(this));

    let accordionField = this.widget('AccordionField');
    this.widget('FormFieldPropertiesBox').setField(accordionField);
    this.widget('GridDataBox').setField(accordionField);
    this.widget('WidgetActionsBox').setField(accordionField);
    this.widget('FormFieldActionsBox').setField(accordionField);
    this.widget('EventsTab').setField(this.accordion);

    let layoutConfigBox = this.widget('LayoutConfigBox');
    layoutConfigBox.getBodyLayout = function() {
      // Use the layout of the first group for the initialization of the grid config box.
      return this.field.groups[0].body.htmlComp.layout;
    };
    layoutConfigBox.getLayoutConfig = function() {
      return this.field.tileGridLayoutConfig;
    };
    layoutConfigBox.setLayoutConfig = function(layoutConfig) {
      this.field.setTileGridLayoutConfig(layoutConfig);
    };
    layoutConfigBox.setField(this.accordion);
    this._updateStatus();
    this._updateGroupVisibility();
  }

  _onInsertMenuAction(event) {
    this._insertGroupWithTiles();
  }

  _onDeleteFirstMenuAction(event) {
    this.accordion.deleteGroup(this.accordion.groups[0]);
    if (this.accordion.groups.length === 0) {
      this.insertedGroupCount = 0;
    }
  }

  _onSortAscMenuAction(event) {
    this._sortTiles(true);
  }

  _onSortDescMenuAction(event) {
    this._sortTiles();
  }

  _onDeleteAllSelectedTilesMenuAction(event) {
    this.accordion.deleteTiles(this.accordion.getSelectedTiles());
  }

  _onInsertTileIntoGroup0MenuAction(event) {
    if (this.accordion.groups.length > 0) {
      this.accordion.groups[0].body.insertTile(this._createTile());
    }
  }

  _onInsertTileIntoGroup1MenuAction(event) {
    if (this.accordion.groups.length > 1) {
      this.accordion.groups[1].body.insertTile(this._createTile());
    }
  }

  _onInsertTileButtonClick(event) {
    let count = this.widget('InsertTileCountField').value;
    let group = this.widget('InsertTileTargetField').value;
    let tiles = [];
    for (let i = 0; i < count; i++) {
      tiles.push(this._createTile());
    }
    group.body.insertTiles(tiles);
  }

  _onSelectNextMenuAction(event) {
    let filteredTiles = this.accordion.getFilteredTiles();
    if (filteredTiles.length === 0) {
      return;
    }
    let selectedTileIndex = arrays.findIndex(filteredTiles, tile => {
      return tile.selected;
    });
    this.accordion.selectTile(filteredTiles[selectedTileIndex + 1] || filteredTiles[0]);
  }

  _onSelectAllMenuAction(event) {
    this.accordion.selectAllTiles();
  }

  _insertGroupWithTiles() {
    let tiles = [];
    let maxTiles = Math.floor(Math.random() * 30);
    for (let i = 0; i < maxTiles; i++) {
      tiles.push(this._createTile({
        label: 'Tile ' + i
      }));
    }
    let group = scout.create('Group', {
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
        tiles: tiles,
        placeholderProducer: () => ({
          gridDataHints: {
            weightX: 0
          }
        })
      }
    });
    this.accordion.insertGroup(group);
  }

  _createTile(model) {
    let defaults;
    let tileType = this.tileTypeField.value;
    if (tileType === 'default') {
      defaults = {
        parent: this.accordion,
        content: 'New <i>Html Tile</i> ' + this.insertedTilesCount++,
        gridDataHints: {
          weightX: 0
        }
      };
      model = $.extend({}, defaults, model);
      return scout.create('HtmlTile', model);
    }
    defaults = {
      parent: this.accordion,
      label: 'New Tile ' + this.insertedTilesCount++,
      gridDataHints: {
        weightX: 0
      },
      colorScheme: this.accordion.groups.length % 2 === 0 ? 'default' : 'alternative'
    };
    model = $.extend({}, defaults, model);
    return scout.create('jswidgets.CustomTile', model);
  }

  _updateStatus() {
    this.widget('StatusField').setValue(this.session.text('TileGridStatus', this.accordion.getTileCount(), this.accordion.getFilteredTileCount(), this.accordion.getSelectedTileCount()));
  }

  _updateGroupVisibility() {
    this.accordion.groups.forEach(group => {
      // Make groups invisible if a tile filter is active and no tiles match (= no tiles are visible)
      let groupEmpty = group.body.filters.length > 0 && group.body.filteredTiles.length === 0;
      group.setVisible(!groupEmpty);
    });
  }

  _sortTiles(asc) {
    let comparator = comparators.ALPHANUMERIC;
    comparator.install(this.session);
    this.accordion.setTileComparator((tile1, tile2) => {
      let result = comparator.compare(tile1.label, tile2.label);
      if (!asc) {
        result = -result;
      }
      return result;
    });
    this.accordion.sortTiles();
  }
}
