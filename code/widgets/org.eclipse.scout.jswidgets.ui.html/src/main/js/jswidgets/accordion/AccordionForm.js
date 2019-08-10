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
jswidgets.AccordionForm = function() {
  jswidgets.AccordionForm.parent.call(this);
  this.insertedGroupCount = 0;
};
scout.inherits(jswidgets.AccordionForm, scout.Form);

jswidgets.AccordionForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.AccordionForm');
};

jswidgets.AccordionForm.prototype._init = function(model) {
  jswidgets.AccordionForm.parent.prototype._init.call(this, model);

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
  this.widget('EventsTab').setField(this.accordion);
};

jswidgets.AccordionForm.prototype._onCollapseStylePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.accordion.setCollapseStyle(event.newValue);
  }
};

jswidgets.AccordionForm.prototype._onExclusiveExpandPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.accordion.setExclusiveExpand(event.newValue);
  }
};

jswidgets.AccordionForm.prototype._onScrollablePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.accordion.setScrollable(event.newValue);
  }
};

jswidgets.AccordionForm.prototype._onInsertMenuAction = function(event) {
  this._insertGroupWithTiles();
};

jswidgets.AccordionForm.prototype._onDeleteFirstMenuAction = function(event) {
  this.accordion.deleteGroup(this.accordion.groups[0]);
  if (this.accordion.groups.length === 0) {
    this.insertedGroupCount = 0;
  }
};

jswidgets.AccordionForm.prototype._onCollapseExpandFirstMenuAction = function(event) {
  if (this.accordion.groups.length === 0) {
    return;
  }
  this.accordion.groups[0].toggleCollapse();
};

jswidgets.AccordionForm.prototype._onCollapseAllMenuAction = function(event) {
  this.accordion.groups.forEach(function(group) {
    group.setCollapsed(true);
  });
};

jswidgets.AccordionForm.prototype._onSortAscMenuAction = function(event) {
  this._sortGroups(true);
};

jswidgets.AccordionForm.prototype._onSortDescMenuAction = function(event) {
  this._sortGroups();
};

jswidgets.AccordionForm.prototype._insertGroupWithTiles = function() {
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
  var group = new scout.create('Group', {
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
};

jswidgets.AccordionForm.prototype._createTile = function(model) {
  var defaults = {
    parent: this,
    gridDataHints: {
      weightX: 0
    },
    colorScheme: this.accordion.groups.length % 2 === 0 ? 'default' : 'alternative'
  };
  model = $.extend({}, defaults, model);
  return scout.create('jswidgets.CustomTile', model);
};

jswidgets.AccordionForm.prototype._sortGroups = function(asc) {
  var comparator = scout.comparators.ALPHANUMERIC;
  comparator.install(this.session);
  this.accordion.setComparator(function(group1, group2) {
    var result = comparator.compare(group1.title, group2.title);
    if (!asc) {
      result = -result;
    }
    return result;
  });
  this.accordion.sort();
};
