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
import {comparators, Form, models, scout} from '@eclipse-scout/core';
import AccordionFormModel from './AccordionFormModel';
import $ from 'jquery';

export default class AccordionForm extends Form {

  constructor() {
    super();
    this.insertedGroupCount = 0;
  }

  _jsonModel() {
    return models.get(AccordionFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    this.accordion = this.widget('Accordion');

    this._insertGroupWithTiles();

    // -- Properties

    var collapseStyleField = this.widget('CollapseStyleField');
    collapseStyleField.setValue(this.accordion.collapseStyle);
    collapseStyleField.on('propertyChange', this._onCollapseStylePropertyChange.bind(this));

    var exclusiveExpandField = this.widget('ExclusiveExpandField');
    exclusiveExpandField.setValue(this.accordion.exclusiveExpand);
    exclusiveExpandField.on('propertyChange', this._onExclusiveExpandPropertyChange.bind(this));

    var scrollableField = this.widget('ScrollableField');
    scrollableField.setValue(this.accordion.scrollable);
    scrollableField.on('propertyChange', this._onScrollablePropertyChange.bind(this));

    // -- Actions

    var insertMenu = this.widget('InsertMenu');
    insertMenu.on('action', this._onInsertMenuAction.bind(this));

    var deleteFirstMenu = this.widget('DeleteFirstMenu');
    deleteFirstMenu.on('action', this._onDeleteFirstMenuAction.bind(this));

    var collapseExpandFirstMenu = this.widget('CollapseExpandFirstMenu');
    collapseExpandFirstMenu.on('action', this._onCollapseExpandFirstMenuAction.bind(this));

    var collapseAllMenu = this.widget('CollapseAllMenu');
    collapseAllMenu.on('action', this._onCollapseAllMenuAction.bind(this));

    var sortAscMenu = this.widget('SortAscMenu');
    sortAscMenu.on('action', this._onSortAscMenuAction.bind(this));

    var sortDescMenu = this.widget('SortDescMenu');
    sortDescMenu.on('action', this._onSortDescMenuAction.bind(this));

    var accordionField = this.widget('AccordionField');
    this.widget('FormFieldPropertiesBox').setField(accordionField);
    this.widget('GridDataBox').setField(accordionField);
    this.widget('WidgetActionsBox').setField(accordionField);
    this.widget('FormFieldActionsBox').setField(accordionField);
    this.widget('EventsTab').setField(this.accordion);
  }

  _onCollapseStylePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.accordion.setCollapseStyle(event.newValue);
    }
  }

  _onExclusiveExpandPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.accordion.setExclusiveExpand(event.newValue);
    }
  }

  _onScrollablePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.accordion.setScrollable(event.newValue);
    }
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

  _onCollapseExpandFirstMenuAction(event) {
    if (this.accordion.groups.length === 0) {
      return;
    }
    this.accordion.groups[0].toggleCollapse();
  }

  _onCollapseAllMenuAction(event) {
    this.accordion.groups.forEach(function(group) {
      group.setCollapsed(true);
    });
  }

  _onSortAscMenuAction(event) {
    this._sortGroups(true);
  }

  _onSortDescMenuAction(event) {
    this._sortGroups();
  }

  _insertGroupWithTiles() {
    var tiles = [];
    var maxTiles = Math.floor(Math.random() * 30);
    for (var i = 0; i < maxTiles; i++) {
      tiles.push(this._createTile({
        label: 'Tile ' + i
      }));
    }
    var title = 'Group with Tiles';
    if (this.insertedGroupCount > 0) {
      title += ' ' + this.insertedGroupCount;
    }
    var group = scout.create('Group', {
      parent: this.accordion,
      title: title,
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
    this.insertedGroupCount++;
  }

  _createTile(model) {
    var defaults = {
      parent: this,
      gridDataHints: {
        weightX: 0
      },
      colorScheme: this.accordion.groups.length % 2 === 0 ? 'default' : 'alternative'
    };
    model = $.extend({}, defaults, model);
    return scout.create('jswidgets.CustomTile', model);
  }

  // noinspection DuplicatedCode
  _sortGroups(asc) {
    var comparator = comparators.ALPHANUMERIC;
    comparator.install(this.session);
    this.accordion.setComparator(function(group1, group2) {
      var result = comparator.compare(group1.title, group2.title);
      if (!asc) {
        result = -result;
      }
      return result;
    });
    this.accordion.sort();
  }
}
