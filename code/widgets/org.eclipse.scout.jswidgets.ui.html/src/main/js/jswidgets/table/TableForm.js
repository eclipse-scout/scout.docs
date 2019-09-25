/*
 * Copyright (c) 2017 BSI Business Systems Integration AG. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0 which accompanies this
 * distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors: BSI Business Systems Integration AG - initial API and
 * implementation
 */
jswidgets.TableForm = function() {
  jswidgets.TableForm.parent.call(this);

  this.rowNo = 0;
  this.groupNo = 0;
};
scout.inherits(jswidgets.TableForm, scout.Form);

jswidgets.TableForm.GROUP_SIZE = 2;

jswidgets.TableForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TableForm');
};

jswidgets.TableForm.prototype._init = function(model) {
  jswidgets.TableForm.parent.prototype._init.call(this, model);

  this.table = this.widget('Table');
  this.widget('AddRowMenu').on('action', this._onAddRowMenuAction.bind(this));
  this.widget('MoveToTopMenu').on('action', this._onMoveToTopMenuAction.bind(this));
  this.widget('MoveUpMenu').on('action', this._onMoveUpMenuAction.bind(this));
  this.widget('MoveDownMenu').on('action', this._onMoveDownMenuAction.bind(this));
  this.widget('MoveToBottomMenu').on('action', this._onMoveToBottomMenuAction.bind(this));
  this.widget('DeleteRowMenu').on('action', this._onDeleteRowMenuAction.bind(this));
  this.table.on('appLinkAction', this._onAppLinkAction.bind(this));

  this.widget('PropertiesBox').setTable(this.table);
  this.widget('FormFieldPropertiesBox').setField(this.widget('TableField'));
  this.widget('GridDataBox').setField(this.widget('TableField'));
  this.widget('WidgetActionsBox').setField(this.widget('TableField'));
  this.widget('EventsTab').setField(this.table);

  var targetField = this.widget('Column.TargetField');
  targetField.setLookupCall(new jswidgets.ColumnLookupCall(this.table));
  targetField.setValue(this.table.columns[0]);
  targetField.on('propertyChange', this._onTargetPropertyChange.bind(this));

  this._onTargetPropertyChange({
    propertyName: 'value',
    newValue: targetField.value
  });

  this.table.insertRows([this._createRow(), this._createRow(), this._createRow()]);

  this.table.createTileForRow = function(row) {
    var icon = scout.icons.parseIconId(row.cells[5].iconId);
    var model = {
      parent: this,
      content: '<div style="text-align: center;"><p>' + row.cells[0].text + '</p></div>' +
        '<div class="font-icon" style="font-size: 50px; text-align: center;">' + icon.iconCharacter + '</div>' +
        '<div style="text-align: center;"><p>' + row.cells[3].text + '(' + row.cells[1].text + ')</p></div>'
    };
    return new scout.create('HtmlTile', model);
  };
};

jswidgets.TableForm.prototype._onTargetPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var oldColumn = event.oldValue;
    var newColumn = event.newValue;

    var columnPropertiesBox = this.widget('Column.PropertiesBox');
    columnPropertiesBox.setColumn(newColumn);
    columnPropertiesBox.setEnabled(!!newColumn);

    var columnPropertyTab = this.widget('ColumnProperties');
    var newPropertiesBoxField = this._createPropertiesBox(newColumn, columnPropertyTab);
    if (newPropertiesBoxField !== null) {
      columnPropertyTab.insertFieldBefore(newPropertiesBoxField, columnPropertiesBox);
      newPropertiesBoxField.setColumn(newColumn);
    }
    this._removePropertiesBoxes(newColumn.objectType, columnPropertyTab);

    this.validateLayoutTree();
  }
};

jswidgets.TableForm.prototype._createPropertiesBox = function(newColumn, parent) {
  switch (newColumn.objectType) {
    case 'DateColumn':
      return scout.create('jswidgets.DateColumnPropertiesBox', {
        id: 'DateColumnPropertyField',
        label: 'Date Column Properties',
        parent: parent
      });
    case 'NumberColumn':
      return scout.create('jswidgets.NumberColumnPropertiesBox', {
        id: 'NumberColumnPropertyField',
        label: 'Number Column Properties',
        parent: parent
      });
    default:
      return null;
  }
};

jswidgets.TableForm.prototype._removePropertiesBoxes = function(newColumnTypeName, tabBox) {
  if (newColumnTypeName !== 'DateColumn') {
    this._removePropertyBox('DateColumnPropertyField', tabBox);
  }
  if (newColumnTypeName !== 'NumberColumn') {
    this._removePropertyBox('NumberColumnPropertyField', tabBox);
  }
};

jswidgets.TableForm.prototype._removePropertyBox = function(propertyBoxId, tabBox) {
  var boxToRemove = this.widget(propertyBoxId);
  if (boxToRemove) {
    boxToRemove.setColumn(null);
    tabBox.deleteField(boxToRemove);
  }
};

jswidgets.TableForm.prototype._createRow = function() {
  var date = new Date();
  var iconList = [scout.icons.STAR_BOLD, scout.icons.PERSON_SOLID, scout.icons.FOLDER_BOLD];
  var locales = jswidgets.LocaleLookupCall.DATA.map(function(lookupRow) {
    return lookupRow[0];
  });

  var rowIcon = iconList[this.rowNo % iconList.length];
  var iconValue = iconList[this.rowNo % iconList.length];
  var stringValue = 'Row #' + this.rowNo;
  var dateValue = scout.dates.shift(date, 0, 0, -this.groupNo);
  var numberValue = this.rowNo;
  var smartValue = locales[this.rowNo % locales.length];
  var booleanValue = this.rowNo % 2 === 0;
  var htmlValue = '<span class="app-link" data-ref="' + this.rowNo + '">App Link</span>';

  if (this.rowNo % jswidgets.TableForm.GROUP_SIZE === 0) {
    this.groupNo++;
  }
  this.rowNo++;

  return {
    iconId: rowIcon,
    cells: [stringValue, dateValue, numberValue, smartValue, booleanValue, iconValue, htmlValue]
  };
};

jswidgets.TableForm.prototype._onAddRowMenuAction = function() {
  this.table.insertRow(this._createRow());
};

jswidgets.TableForm.prototype._onMoveToTopMenuAction = function() {
  this.table.moveRowToTop(this.table.selectedRows[0]);
};

jswidgets.TableForm.prototype._onMoveUpMenuAction = function() {
  this.table.moveVisibleRowUp(this.table.selectedRows[0]);
};

jswidgets.TableForm.prototype._onMoveDownMenuAction = function() {
  this.table.moveVisibleRowDown(this.table.selectedRows[0]);
};

jswidgets.TableForm.prototype._onMoveToBottomMenuAction = function() {
  this.table.moveRowToBottom(this.table.selectedRows[0]);
};

jswidgets.TableForm.prototype._onDeleteRowMenuAction = function() {
  this.table.deleteRows(this.table.selectedRows);
};

jswidgets.TableForm.prototype._onAppLinkAction = function(event) {
  scout.MessageBoxes.createOk(this)
    .withBody("Link with ref '" + event.ref + "' has been clicked.")
    .buildAndOpen();
};
