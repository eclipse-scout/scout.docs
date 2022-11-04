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
import {Accordion, comparators, Event, Form, FormModel, Group, Menu, models, scout, TileGrid} from '@eclipse-scout/core';
import AccordionFormModel from './AccordionFormModel';
import $ from 'jquery';
import {InitModelOf} from '@eclipse-scout/core/src/scout';
import {AccordionFormWidgetMap, CustomTile} from '../index';

export class AccordionForm extends Form {
  declare widgetMap: AccordionFormWidgetMap;

  accordion: Accordion;
  insertedGroupCount: number;

  constructor() {
    super();
    this.insertedGroupCount = 0;
  }

  protected override _jsonModel(): FormModel {
    return models.get(AccordionFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.accordion = this.widget('Accordion');

    this._insertGroupWithTiles();

    // -- Properties

    let collapseStyleField = this.widget('CollapseStyleField');
    collapseStyleField.setValue(this.accordion.collapseStyle);
    collapseStyleField.on('propertyChange:value', event => this.accordion.setCollapseStyle(event.newValue));

    let exclusiveExpandField = this.widget('ExclusiveExpandField');
    exclusiveExpandField.setValue(this.accordion.exclusiveExpand);
    exclusiveExpandField.on('propertyChange:value', event => this.accordion.setExclusiveExpand(event.newValue));

    let scrollableField = this.widget('ScrollableField');
    scrollableField.setValue(this.accordion.scrollable);
    scrollableField.on('propertyChange:value', event => this.accordion.setScrollable(event.newValue));

    // -- Actions

    let insertMenu = this.widget('InsertMenu');
    insertMenu.on('action', this._onInsertMenuAction.bind(this));

    let deleteFirstMenu = this.widget('DeleteFirstMenu');
    deleteFirstMenu.on('action', this._onDeleteFirstMenuAction.bind(this));

    let collapseExpandFirstMenu = this.widget('CollapseExpandFirstMenu');
    collapseExpandFirstMenu.on('action', this._onCollapseExpandFirstMenuAction.bind(this));

    let collapseAllMenu = this.widget('CollapseAllMenu');
    collapseAllMenu.on('action', this._onCollapseAllMenuAction.bind(this));

    let sortAscMenu = this.widget('SortAscMenu');
    sortAscMenu.on('action', this._onSortAscMenuAction.bind(this));

    let sortDescMenu = this.widget('SortDescMenu');
    sortDescMenu.on('action', this._onSortDescMenuAction.bind(this));

    let accordionField = this.widget('AccordionField');
    this.widget('FormFieldPropertiesBox').setField(accordionField);
    this.widget('GridDataBox').setField(accordionField);
    this.widget('WidgetActionsBox').setField(accordionField);
    this.widget('FormFieldActionsBox').setField(accordionField);
    this.widget('EventsTab').setField(this.accordion);
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

  protected _onCollapseExpandFirstMenuAction(event: Event<Menu>) {
    if (this.accordion.groups.length === 0) {
      return;
    }
    this.accordion.groups[0].toggleCollapse();
  }

  protected _onCollapseAllMenuAction(event: Event<Menu>) {
    this.accordion.groups.forEach(group => {
      group.setCollapsed(true);
    });
  }

  protected _onSortAscMenuAction(event: Event<Menu>) {
    this._sortGroups(true);
  }

  protected _onSortDescMenuAction(event: Event<Menu>) {
    this._sortGroups(false);
  }

  protected _insertGroupWithTiles() {
    let tiles = [];
    let maxTiles = Math.floor(Math.random() * 30);
    for (let i = 0; i < maxTiles; i++) {
      tiles.push(this._createTile({
        label: 'Tile ' + i
      }));
    }
    let title = 'Group with Tiles';
    if (this.insertedGroupCount > 0) {
      title += ' ' + this.insertedGroupCount;
    }
    let group = scout.create(Group, {
      parent: this.accordion,
      title: title,
      body: {
        objectType: TileGrid,
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
    this.insertedGroupCount++;
  }

  protected _createTile(m: Omit<InitModelOf<CustomTile>, 'parent'>): CustomTile {
    let defaults = {
      parent: this,
      gridDataHints: {
        weightX: 0
      },
      colorScheme: this.accordion.groups.length % 2 === 0 ? 'default' : 'alternative'
    };
    let model = $.extend({}, defaults, m);
    return scout.create(CustomTile, model);
  }

  // noinspection DuplicatedCode
  protected _sortGroups(asc: boolean) {
    let comparator = comparators.ALPHANUMERIC;
    comparator.install(this.session);
    this.accordion.setComparator((group1, group2) => {
      let result = comparator.compare(group1.title, group2.title);
      if (!asc) {
        result = -result;
      }
      return result;
    });
    this.accordion.sort();
  }
}
