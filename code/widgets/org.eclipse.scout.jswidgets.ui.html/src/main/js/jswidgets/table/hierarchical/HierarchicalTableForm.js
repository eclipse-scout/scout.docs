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

  var rowIconColumnWidthField = this.widget('RowIconColumnWidthField');
  rowIconColumnWidthField.setValue(this.table.rowIconColumnWidth);
  rowIconColumnWidthField.on('propertyChange', this._onRowIconColumnWidthPropertyChange.bind(this));

  var checkableStyleField = this.widget('CheckableStyleField');
  checkableStyleField.setValue(this.table.checkableStyle);
  checkableStyleField.on('propertyChange', this._onCheckableStylePropertyChange.bind(this));

  var groupingStyleField = this.widget('GroupingStyleField');
  groupingStyleField.setValue(this.table.groupingStyle);
  groupingStyleField.on('propertyChange', this._onGroupingStylePropertyChange.bind(this));

  this.widget('FormFieldPropertiesBox').setField(this.widget('TableField'));
  this.widget('GridDataBox').setField(this.widget('TableField'));
  this.widget('EventsTab').setField(this.table);

  var extendedHierarchyPaddingField = this.widget('ExtendedHierarchyPadding');
  extendedHierarchyPaddingField.setValue(this.table.cssClassAsArray().indexOf('extended-row-level-padding') > -1);
  extendedHierarchyPaddingField.on('propertyChange', this._onExtendedHierarchyPaddingPropertyChange.bind(this));

  this.widget('RemoveAll').on('action', this._onRemoveAllRows.bind(this));
  this.widget('InsertFew').on('action', this._onInsertFew.bind(this));
  this.widget('InsertMany').on('action', this._onInsertMany.bind(this));
  this.widget('DeleteRowMenu').on('action', this._onDeleteRowMenuAction.bind(this));
  this.widget('AddRowMenu').on('action', this._onAddRowMenuAction.bind(this));

  var targetField = this.widget('Column.TargetField');
  targetField.setLookupCall(new jswidgets.ColumnLookupCall(this.table));
  targetField.setValue(this.table.columns[0]);
  targetField.on('propertyChange', this._onTargetPropertyChange.bind(this));

  this._onTargetPropertyChange({
    propertyName: 'value',
    newValue: targetField.value
  });

  this._insertFewRows();
  this.table.expandAll();
};

jswidgets.HierarchicalTableForm.prototype._onTargetPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var oldColumn = event.oldValue;
    var newColumn = event.newValue;

    var columnPropertiesBox = this.widget('Column.PropertiesBox');
    columnPropertiesBox.setColumn(newColumn);
    columnPropertiesBox.setEnabled(!!newColumn);
  }
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
  var daltonId = this.rowNo++,
    simpsonsId = this.rowNo++;
  this.table.insertRows(this._scrumbleOrder([{
      id: daltonId,
      iconId: scout.icons.WORLD,
      cells: [
        'Daltons brothers', null, null, true
      ]
    }, {
      id: this.rowNo++,
      parentRow: daltonId,
      iconId: scout.icons.PERSON_SOLID,
      cells: [
        'Joe Dalton', 'the smartest', '20.10.1940', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: daltonId,
      iconId: scout.icons.PERSON_SOLID,
      cells: [
        'Wiliam Dalton', 'smarter', '03.05.1942', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: daltonId,
      iconId: scout.icons.PERSON_SOLID,
      cells: [
        'Jack Dalton', 'smart', '15.09.1944', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: daltonId,
      iconId: scout.icons.PERSON_SOLID,
      enabled: false,
      cells: [
        'Averell Dalton', 'not so smart', '23.11.1945', true
      ]
    }, {
      id: simpsonsId,
      iconId: scout.icons.GROUP,
      cells: [
        'Simpsons', 'a simple family', null, false
      ]
    }, {
      id: this.rowNo++,
      parentRow: simpsonsId,
      iconId: scout.icons.PERSON_SOLID,
      cells: [
        'Homer Simpson', 'Daddy', '23.12.1960', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: simpsonsId,
      iconId: scout.icons.PERSON_SOLID,
      cells: [
        'Marge Simpson', 'Mom', '02.05.1964', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: simpsonsId,
      iconId: scout.icons.PERSON_SOLID,
      cells: [
        'Bart Simpson', 'Boy', '08.10.1985', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: simpsonsId,
      iconId: scout.icons.PERSON_SOLID,
      cells: [
        'Lisa Simpson', 'Girl', '17.03.1987', true
      ]
    }, {
      id: this.rowNo++,
      parentRow: simpsonsId,
      iconId: scout.icons.PERSON_SOLID,
      cells: [
        'Maggie Simpson', 'Baby', '14.08.1988', true
      ]
    }

  ]));
};

jswidgets.HierarchicalTableForm.prototype._insertManyRows = function() {
  var i = 0,
    allRows = [],
    createParentWithManyChildren = function (id, name, childCount) {
      var rows = [],
        i,
        rowId;
      rows.push(createRow(id, null, null, [name + '_parent' + ' (' + childCount + ')', null, null]));

      for (i = 0; i < childCount; i++) {
        rowId = this.rowNo++;
        rows.push(createRow(rowId, id, null, [
          name + rowId,
          'Any title',
          '20.10.2015'
        ]));
      }
      return rows;
    }.bind(this);

  for (i = 0; i < 100; i++) {
    allRows = allRows.concat(createParentWithManyChildren(this.rowNo++, 'Abc', Math.floor(Math.random() * 100)));
  }

  this.table.insertRows(allRows);

  function createRow(id, parentId, iconId, cells) {
    return {
      id: id,
      parentRow: parentId,
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
  var id = this.rowNo++,
    parentId = null,
    selectedRow = this.table.selectedRow();
  if (selectedRow) {
    parentId = selectedRow.id;
  }

  this.table.insertRow({
    id: id,
    parentRow: parentId,
    iconId: null,
    cells: [
      'newRow' + id,
      'Any title',
      '20.10.2015'
    ]
  });
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

jswidgets.HierarchicalTableForm.prototype._onRowIconColumnWidthPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setRowIconColumnWidth(event.newValue);
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

jswidgets.HierarchicalTableForm.prototype._onExtendedHierarchyPaddingPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.toggleCssClass('extended-row-level-padding', event.newValue);
  }
};
