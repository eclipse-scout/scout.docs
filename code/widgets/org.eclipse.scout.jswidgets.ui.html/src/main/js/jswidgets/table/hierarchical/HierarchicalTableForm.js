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
jswidgets.HierarchicalTableForm = function() {
  jswidgets.HierarchicalTableForm.parent.call(this);

  this.rowNo = 1;
  this.groupNo = 1;
};
scout.inherits(jswidgets.HierarchicalTableForm, scout.Form);

jswidgets.HierarchicalTableForm.GROUP_SIZE = 2;

jswidgets.HierarchicalTableForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.HierarchicalTableForm');
};

jswidgets.HierarchicalTableForm.prototype._init = function(model) {
  jswidgets.HierarchicalTableForm.parent.prototype._init.call(this, model);

  this.table = this.widget('Table');

  var structuredDisplayStyle = this.widget('StructuredDisplayStyle');
  structuredDisplayStyle.setValue(scout.Table.HierarchicalStyle.STRUCTURED === this.table.hierarchicalStyle);
  structuredDisplayStyle.on('propertyChange', this._onHierarchicalStylePropertyChange.bind(this));

  var autoResizeColumnsField = this.widget('AutoResizeColumnsField');
  autoResizeColumnsField.setValue(this.table.autoResizeColumns);
  autoResizeColumnsField.on('propertyChange', this._onAutoResizeColumnsPropertyChange.bind(this));

  var autoOptimizeColumnWidthsField = this.widget('AutoOptimizeColumnWidthsField');
  autoOptimizeColumnWidthsField.setValue(false);
  autoOptimizeColumnWidthsField.on('propertyChange', this._onAutoOptimizeColumnWidthsPropertyChange.bind(this));

  var checkableField = this.widget('CheckableField');
  checkableField.setValue(this.table.checkable);
  checkableField.on('propertyChange', this._onCheckablePropertyChange.bind(this));

  var headerEnabledField = this.widget('HeaderEnabledField');
  headerEnabledField.setValue(this.table.headerEnabled);
  headerEnabledField.on('propertyChange', this._onHeaderEnabledPropertyChange.bind(this));

  var headerVisibleField = this.widget('HeaderVisibleField');
  headerVisibleField.setValue(this.table.headerVisible);
  headerVisibleField.on('propertyChange', this._onHeaderVisiblePropertyChange.bind(this));

  var headerMenusEnabledField = this.widget('HeaderMenusEnabledField');
  headerMenusEnabledField.setValue(this.table.headerMenusEnabled);
  headerMenusEnabledField.on('propertyChange', this._onHeaderMenusEnabledPropertyChange.bind(this));

  var multiCheckField = this.widget('MultiCheckField');
  multiCheckField.setValue(this.table.multiCheck);
  multiCheckField.on('propertyChange', this._onMultiCheckPropertyChange.bind(this));

  var multiSelectField = this.widget('MultiSelectField');
  multiSelectField.setValue(this.table.multiSelect);
  multiSelectField.on('propertyChange', this._onMultiSelectPropertyChange.bind(this));

  var scrollToSelectionField = this.widget('ScrollToSelectionField');
  scrollToSelectionField.setValue(this.table.scrollToSelection);
  scrollToSelectionField.on('propertyChange', this._onScrollToSelectionPropertyChange.bind(this));

  var sortEnabledField = this.widget('SortEnabledField');
  sortEnabledField.setValue(this.table.sortEnabled);
  sortEnabledField.on('propertyChange', this._onSortEnabledPropertyChange.bind(this));

  var footerVisibleField = this.widget('FooterVisibleField');
  footerVisibleField.setValue(this.table.footerVisible);
  footerVisibleField.on('propertyChange', this._onFooterVisiblePropertyChange.bind(this));

  var rowIconVisibleField = this.widget('RowIconVisibleField');
  rowIconVisibleField.setValue(this.table.rowIconVisible);
  rowIconVisibleField.on('propertyChange', this._onRowIconVisiblePropertyChange.bind(this));

  var checkableStyleField = this.widget('CheckableStyleField');
  checkableStyleField.setValue(this.table.checkableStyle);
  checkableStyleField.on('propertyChange', this._onCheckableStylePropertyChange.bind(this));

  var groupingStyleField = this.widget('GroupingStyleField');
  groupingStyleField.setValue(this.table.groupingStyle);
  groupingStyleField.on('propertyChange', this._onGroupingStylePropertyChange.bind(this));

  this.widget('FormFieldPropertiesBox').setField(this.widget('TableField'));
  this.widget('GridDataBox').setField(this.widget('TableField'));

  this.widget('RemoveAll').on('action', this._onRemoveAllRows.bind(this));
  this.widget('InsertFew').on('action', this._onInsertFew.bind(this));
  this.widget('InsertMany').on('action', this._onInsertMany.bind(this));

  this._insertFewRows();
};

jswidgets.HierarchicalTableForm.prototype._onRemoveAllRows = function() {
  this.table.deleteAllRows();
};

jswidgets.HierarchicalTableForm.prototype._onInsertFew = function() {
  this._insertFewRows();
};

jswidgets.HierarchicalTableForm.prototype._onInsertMany = function() {
  this._insertManyRows();
};

jswidgets.HierarchicalTableForm.prototype._insertFewRows = function() {
  this.table.insertRows(this._scrumbleOrder([{
    id: 1,
    iconId: scout.icons.WORLD,
    cells: [
      'BSI AG', null, null, true
    ]
  }, {
    id: 2,
    parentId: 1,
    iconId: scout.icons.STAR_BOLD,
    cells: [
      'Christian Rusche', 'Stuff', '20.10.2015', true
    ]
  }, {
    id: 3,
    parentId: 1,
    iconId: scout.icons.STAR_BOLD,
    cells: [
      'Claudio Guglielmo', 'Frontend Engineer', '20.10.2015', true
    ]
  }, {
    id: 4,
    parentId: 1,
    iconId: scout.icons.STAR_BOLD,
    cells: [
      'Andreas Hoegger', 'Frontend Engineer', '20.10.2015', true
    ]
  }, {
    id: 5,
    parentId: 1,
    iconId: scout.icons.GROUP,
    cells: [
      'BSI Deutschland GmbH', null, null, true
    ]
  }, {
    id: 6,
    parentId: 5,
    iconId: scout.icons.PERSON_SOLID,
    enabled: false,
    cells: [
      'Oliver Hechler', 'Project Manager', '20.10.2015', false
    ]
  }, {
    id: 6,
    parentId: 5,
    iconId: scout.icons.PERSON_SOLID,
    cells: [
      'Matthias Otterbach', 'Senior Software Engineer', '20.10.2015', true
    ]
  }]));
};

jswidgets.HierarchicalTableForm.prototype._insertManyRows = function() {
  var i = 0,
    allRows = [];

  for (i = 0; i < 100; i++) {
    allRows = allRows.concat(createParentWithManyChildren(i, 'Abc', Math.floor(Math.random() * 100)));
  }

  this.table.insertRows(allRows);

  function createParentWithManyChildren(id, name, childCount) {
    var rows = [],
      rowId;
    rows.push(createRow(id, null, null, [name + '_parent' + ' (' + childCount + ')', null, null]));
    for (rowId = id + 1; rowId < id + childCount; rowId++) {
      rows.push(createRow(rowId, id, null, [
        name + rowId,
        'Any title',
        '20.10.2015'
      ]));
    }
    return rows;
  }

  function createRow(id, parentId, iconId, cells) {
    return {
      id: id,
      parentId: parentId,
      iconId: iconId,
      cells: cells
    };
  }

};

jswidgets.HierarchicalTableForm.prototype._scrumbleOrder = function(rows) {
  return rows.sort(function(a, b) {
    return 0.5 - Math.random();
  });
};

jswidgets.HierarchicalTableForm.prototype._onAddRowMenuAction = function() {
  this.table.insertRow({
    iconId: scout.icons.STAR,
    cells: ['Row #' + this.rowNo, this.groupNo, this.rowNo]
  });
  if (this.rowNo % jswidgets.HierarchicalTableForm.GROUP_SIZE === 0) {
    this.groupNo++;
  }
  this.rowNo++;
};

jswidgets.HierarchicalTableForm.prototype._onDeleteRowMenuAction = function() {
  this.table.deleteRows(this.table.selectedRows);
};

jswidgets.HierarchicalTableForm.prototype._onToggleGroupNoColumnMenuAction = function() {
  var column = this.table.columnById('GroupNo');
  column.setVisible(!column.visible);
};

jswidgets.HierarchicalTableForm.prototype._onHierarchicalStylePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setHierarchicalStyle((event.newValue) ? (scout.Table.HierarchicalStyle.STRUCTURED) : (scout.Table.HierarchicalStyle.DEFAULT));
  }
};

jswidgets.HierarchicalTableForm.prototype._onAutoResizeColumnsPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setAutoResizeColumns(event.newValue);
  }
};

jswidgets.HierarchicalTableForm.prototype._onAutoOptimizeColumnWidthsPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.columns.forEach(function(column) {
      column.setAutoOptimizeWidth(event.newValue);
    });
  }
};

jswidgets.HierarchicalTableForm.prototype._onCheckablePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setCheckable(event.newValue);
  }
};

jswidgets.HierarchicalTableForm.prototype._onHeaderEnabledPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setHeaderEnabled(event.newValue);
  }
};

jswidgets.HierarchicalTableForm.prototype._onHeaderVisiblePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setHeaderVisible(event.newValue);
  }
};

jswidgets.HierarchicalTableForm.prototype._onHeaderMenusEnabledPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setHeaderMenusEnabled(event.newValue);
  }
};

jswidgets.HierarchicalTableForm.prototype._onMultiCheckPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setMultiCheck(event.newValue);
  }
};

jswidgets.HierarchicalTableForm.prototype._onMultiSelectPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setMultiSelect(event.newValue);
  }
};

jswidgets.HierarchicalTableForm.prototype._onScrollToSelectionPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setScrollToSelection(event.newValue);
  }
};

jswidgets.HierarchicalTableForm.prototype._onSortEnabledPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setSortEnabled(event.newValue);
  }
};

jswidgets.HierarchicalTableForm.prototype._onFooterVisiblePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setFooterVisible(event.newValue);
  }
};

jswidgets.HierarchicalTableForm.prototype._onRowIconVisiblePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setRowIconVisible(event.newValue);
  }
};

jswidgets.HierarchicalTableForm.prototype._onCheckableStylePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setCheckableStyle(event.newValue);
  }
};

jswidgets.HierarchicalTableForm.prototype._onGroupingStylePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setGroupingStyle(event.newValue);
  }
};
