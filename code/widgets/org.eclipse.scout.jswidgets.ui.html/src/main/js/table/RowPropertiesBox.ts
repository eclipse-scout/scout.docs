/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {arrays, CheckBoxField, GroupBox, GroupBoxModel, InitModelOf, PropertyChangeEvent, scout, Table, TableRow, TableRowDropTypesDo, TableRowsSelectedEvent, ValueField} from '@eclipse-scout/core';
import {RowPropertiesBoxWidgetMap} from '../index';
import model from './RowPropertiesBoxModel';

export class RowPropertiesBox extends GroupBox {
  declare widgetMap: RowPropertiesBoxWidgetMap;

  table: Table;

  protected _fieldValueChangeHandler = this._onFieldValueChange.bind(this);
  protected _rowsSelectedHandler = this._onRowsSelected.bind(this);
  protected _updating = false;

  protected override _jsonModel(): GroupBoxModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.widget('IconIdField').on('propertyChange:value', this._fieldValueChangeHandler);
    this.widget('EnabledField').on('propertyChange:value', this._fieldValueChangeHandler);
    this.widget('DraggableField').on('propertyChange:value', this._fieldValueChangeHandler);
    this.widget('DropTypeBeforeField').on('propertyChange:value', this._fieldValueChangeHandler);
    this.widget('DropTypeAfterField').on('propertyChange:value', this._fieldValueChangeHandler);
    this.widget('DropTypeInsideField').on('propertyChange:value', this._fieldValueChangeHandler);
  }

  setTable(table: Table) {
    this.setProperty('table', table);
  }

  protected _setTable(table: Table) {
    if (this.table) {
      this.table.off('rowsSelected', this._rowsSelectedHandler);
    }
    this._setProperty('table', table);
    if (this.table) {
      this.table.on('rowsSelected', this._rowsSelectedHandler);
    }
    this._updateForSelectedRows();
  }

  protected _onRowsSelected(event: TableRowsSelectedEvent) {
    this._updateForSelectedRows();
  }

  protected _onFieldValueChange(event: PropertyChangeEvent) {
    if (this._updating || !(event.source instanceof ValueField)) {
      return;
    }
    let field = event.source;
    field.touch(); // allow changing back to the initial value
    if (field instanceof CheckBoxField) {
      field.setTriStateEnabled(false);
    }
    this._exportRowProperties();
  }

  protected _updateForSelectedRows() {
    this._updating = true;
    try {

      let selectedRows = arrays.ensure(this.table?.selectedRows);
      if (arrays.hasElements(selectedRows)) {
        this.setEnabled(true);
        this.widget('SelectedRowsLabelField').setValue(selectedRows.length === 1 ? '1 row selected' : `${selectedRows.length} rows selected`);
        this._importRowProperties(selectedRows);
      } else {
        this.setEnabled(false);
        this.widget('SelectedRowsLabelField').setValue('0 rows selected');
        this.widget('IconIdField').setValue(null);
        this.widget('EnabledField').setValue(null);
        this.widget('DraggableField').setValue(null);
        this.widget('DropTypeBeforeField').setValue(null);
        this.widget('DropTypeAfterField').setValue(null);
        this.widget('DropTypeInsideField').setValue(null);
      }
    } finally {
      this._updating = false;
    }
  }

  protected _importRowProperties(rows: TableRow[]) {
    let iconIdValues = new Set(rows.map(row => row.iconId));
    let enabledValues = new Set(rows.map(row => row.enabled));
    let draggableValues = new Set(rows.map(row => scout.nvl(row.draggable, true)));
    let dropTypeBeforeValues = new Set(rows.map(row => row.dropTypes?.before));
    let dropTypeAfterValues = new Set(rows.map(row => row.dropTypes?.after));
    let dropTypeInsideValues = new Set(rows.map(row => row.dropTypes?.inside));

    this.widget('IconIdField').setValue(iconIdValues.size === 1 ? iconIdValues.values().next().value : null);
    this.widget('EnabledField').setTriStateEnabled(enabledValues.size > 1);
    this.widget('EnabledField').setValue(enabledValues.size === 1 ? enabledValues.values().next().value : null);
    this.widget('DraggableField').setTriStateEnabled(draggableValues.size > 1);
    this.widget('DraggableField').setValue(draggableValues.size === 1 ? draggableValues.values().next().value : null);
    this.widget('DropTypeBeforeField').setValue(dropTypeBeforeValues.size === 1 ? dropTypeBeforeValues.values().next().value : null);
    this.widget('DropTypeAfterField').setValue(dropTypeAfterValues.size === 1 ? dropTypeAfterValues.values().next().value : null);
    this.widget('DropTypeInsideField').setValue(dropTypeInsideValues.size === 1 ? dropTypeInsideValues.values().next().value : null);

    this.markAsSaved(); // so we can check for saveNeed in _exportRowProperties()
  }

  protected _exportRowProperties() {
    let selectedRows = arrays.ensure(this.table?.selectedRows);
    for (let row of selectedRows) {
      if (this.widget('IconIdField').saveNeeded) {
        row.iconId = this.widget('IconIdField').value;
      }
      if (this.widget('EnabledField').saveNeeded) {
        row.enabled = this.widget('EnabledField').value;
      }
      if (this.widget('DraggableField').saveNeeded) {
        row.draggable = this.widget('DraggableField').value;
      }
      if (this.widget('DropTypeBeforeField').saveNeeded) {
        row.dropTypes = row.dropTypes || scout.create(TableRowDropTypesDo);
        row.dropTypes.before = this.widget('DropTypeBeforeField').value;
      }
      if (this.widget('DropTypeAfterField').saveNeeded) {
        row.dropTypes = row.dropTypes || scout.create(TableRowDropTypesDo);
        row.dropTypes.after = this.widget('DropTypeAfterField').value;
      }
      if (this.widget('DropTypeInsideField').saveNeeded) {
        row.dropTypes = row.dropTypes || scout.create(TableRowDropTypesDo);
        row.dropTypes.inside = this.widget('DropTypeInsideField').value;
      }
    }
    this.table.updateRows(selectedRows);
  }
}
