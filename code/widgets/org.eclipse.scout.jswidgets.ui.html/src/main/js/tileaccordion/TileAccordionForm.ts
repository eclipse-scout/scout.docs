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
import {arrays, Button, comparators, Event, Form, FormModel, Group, HtmlTile, HtmlTileModel, InitModelOf, Menu, models, scout, SmartField, TileAccordion, TileGrid} from '@eclipse-scout/core';
import TileAccordionFormModel from './TileAccordionFormModel';
import {CustomTile, CustomTileModel, GroupLookupCall, TileAccordionFormWidgetMap} from '../index';
import $ from 'jquery';

export class TileAccordionForm extends Form {
  declare widgetMap: TileAccordionFormWidgetMap;

  accordion: TileAccordion;
  insertedGroupCount: number;
  insertedTilesCount: number;
  tileFilter: any;
  tileTypeField: SmartField<'default' | 'simple'>;

  constructor() {
    super();
    this.insertedGroupCount = 0;
    this.insertedTilesCount = 0;
    this.tileFilter = null;
    this.tileTypeField = null;
  }

  protected override _jsonModel(): FormModel {
    return models.get(TileAccordionFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
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

  protected _onInsertMenuAction(event: Event<Menu>) {
    this._insertGroupWithTiles();
  }

  protected _onDeleteFirstMenuAction(event: Event<Menu>) {
    this.accordion.deleteGroup(this.accordion.groups[0]);
    if (this.accordion.groups.length === 0) {
      this.insertedGroupCount = 0;
    }
  }

  protected _onSortAscMenuAction(event: Event<Menu>) {
    this._sortTiles(true);
  }

  protected _onSortDescMenuAction(event: Event<Menu>) {
    this._sortTiles(false);
  }

  protected _onDeleteAllSelectedTilesMenuAction(event: Event<Menu>) {
    this.accordion.deleteTiles(this.accordion.getSelectedTiles());
  }

  protected _onInsertTileIntoGroup0MenuAction(event: Event<Menu>) {
    if (this.accordion.groups.length > 0) {
      this.accordion.groups[0].body.insertTile(this._createTile());
    }
  }

  protected _onInsertTileIntoGroup1MenuAction(event: Event<Menu>) {
    if (this.accordion.groups.length > 1) {
      this.accordion.groups[1].body.insertTile(this._createTile());
    }
  }

  protected _onInsertTileButtonClick(event: Event<Button>) {
    let count = this.widget('InsertTileCountField').value;
    let group = this.widget('InsertTileTargetField').value;
    let tiles = [];
    for (let i = 0; i < count; i++) {
      tiles.push(this._createTile());
    }
    group.body.insertTiles(tiles);
  }

  protected _onSelectNextMenuAction(event: Event<Menu>) {
    let filteredTiles = this.accordion.getFilteredTiles();
    if (filteredTiles.length === 0) {
      return;
    }
    let selectedTileIndex = arrays.findIndex(filteredTiles, tile => {
      return tile.selected;
    });
    this.accordion.selectTile(filteredTiles[selectedTileIndex + 1] || filteredTiles[0]);
  }

  protected _onSelectAllMenuAction(event: Event<Menu>) {
    this.accordion.selectAllTiles();
  }

  protected _insertGroupWithTiles() {
    let tiles = [];
    let maxTiles = Math.floor(Math.random() * 30);
    for (let i = 0; i < maxTiles; i++) {
      tiles.push(this._createTile({
        label: 'Tile ' + i
      }));
    }
    let group = scout.create(Group, {
      parent: this.accordion,
      title: 'Group ' + this.insertedGroupCount++,
      body: {
        objectType: TileGrid,
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

  protected _createTile(model?: HtmlTileModel | CustomTileModel): HtmlTile | CustomTile {
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
      return scout.create(HtmlTile, model as InitModelOf<HtmlTile>);
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
    return scout.create(CustomTile, model as InitModelOf<CustomTile>);
  }

  protected _updateStatus() {
    this.widget('StatusField').setValue(this.session.text('TileGridStatus', this.accordion.getTileCount(), this.accordion.getFilteredTileCount(), this.accordion.getSelectedTileCount()));
  }

  protected _updateGroupVisibility() {
    this.accordion.groups.forEach(group => {
      // Make groups invisible if a tile filter is active and no tiles match (= no tiles are visible)
      let groupEmpty = group.body.filters.length > 0 && group.body.filteredTiles.length === 0;
      group.setVisible(!groupEmpty);
    });
  }

  protected _sortTiles(asc: boolean) {
    let comparator = comparators.ALPHANUMERIC;
    comparator.install(this.session);
    this.accordion.setTileComparator((tile1: (HtmlTile & { label?: string }) | CustomTile, tile2: (HtmlTile & { label?: string }) | CustomTile) => {
      let result = comparator.compare(tile1.label, tile2.label);
      if (!asc) {
        result = -result;
      }
      return result;
    });
    this.accordion.sortTiles();
  }
}
