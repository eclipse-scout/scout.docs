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
jswidgets.TableForm = function() {
  jswidgets.TableForm.parent.call(this);

  this.rowNo = 1;
  this.groupNo = 1;
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
  this.widget('DeleteRowMenu').on('action', this._onDeleteRowMenuAction.bind(this));
  this.widget('ToggleGroupNoColumnMenu').on('action', this._onToggleGroupNoColumnMenuAction.bind(this));

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
};

jswidgets.TableForm.prototype._onAddRowMenuAction = function() {
  this.table.insertRow({
    iconId: scout.icons.STAR_BOLD,
    cells: ['Row #' + this.rowNo, this.groupNo, this.rowNo]
  });
  if (this.rowNo % jswidgets.TableForm.GROUP_SIZE === 0) {
    this.groupNo++;
  }
  this.rowNo++;
};

jswidgets.TableForm.prototype._onDeleteRowMenuAction = function() {
  this.table.deleteRows(this.table.selectedRows);
};

jswidgets.TableForm.prototype._onToggleGroupNoColumnMenuAction = function() {
  var column = this.table.columnById('GroupNo');
  column.setVisible(!column.visible);
};

jswidgets.TableForm.prototype._onAutoResizeColumnsPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setAutoResizeColumns(event.newValue);
  }
};

jswidgets.TableForm.prototype._onAutoOptimizeColumnWidthsPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.columns.forEach(function(column) {
      column.setAutoOptimizeWidth(event.newValue);
    });
  }
};

jswidgets.TableForm.prototype._onCheckablePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setCheckable(event.newValue);
  }
};

jswidgets.TableForm.prototype._onHeaderEnabledPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setHeaderEnabled(event.newValue);
  }
};

jswidgets.TableForm.prototype._onHeaderVisiblePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setHeaderVisible(event.newValue);
  }
};

jswidgets.TableForm.prototype._onHeaderMenusEnabledPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setHeaderMenusEnabled(event.newValue);
  }
};

jswidgets.TableForm.prototype._onMultiCheckPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setMultiCheck(event.newValue);
  }
};

jswidgets.TableForm.prototype._onMultiSelectPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setMultiSelect(event.newValue);
  }
};

jswidgets.TableForm.prototype._onScrollToSelectionPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setScrollToSelection(event.newValue);
  }
};

jswidgets.TableForm.prototype._onSortEnabledPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setSortEnabled(event.newValue);
  }
};

jswidgets.TableForm.prototype._onFooterVisiblePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setFooterVisible(event.newValue);
  }
};

jswidgets.TableForm.prototype._onRowIconVisiblePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setRowIconVisible(event.newValue);
  }
};

jswidgets.TableForm.prototype._onCheckableStylePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setCheckableStyle(event.newValue);
  }
};

jswidgets.TableForm.prototype._onGroupingStylePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setGroupingStyle(event.newValue);
  }
};
