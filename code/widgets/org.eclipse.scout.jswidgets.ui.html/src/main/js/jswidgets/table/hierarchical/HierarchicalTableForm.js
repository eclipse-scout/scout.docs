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

  this.widget('PropertiesBox').setTable(this.table);
  this.widget('FormFieldPropertiesBox').setField(this.widget('TableField'));
  this.widget('GridDataBox').setField(this.widget('TableField'));
  this.widget('WidgetActionsBox').setField(this.table);
  this.widget('EventsTab').setField(this.table);

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
