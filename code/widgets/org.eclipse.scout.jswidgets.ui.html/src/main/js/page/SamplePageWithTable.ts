/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {ajax, DoEntity, HtmlTile, models, ObjectOrModel, Page, PageModel, PageWithTable, scout, systems, TableRow, TableRowModel, Tile} from '@eclipse-scout/core';
import SamplePageWithTableModel from './SamplePageWithTableModel';
import {MiniForm, SamplePageWithNodes, SamplePageWithTableSearchFormData, SamplePageWithTableTable} from '../index';

export class SamplePageWithTable extends PageWithTable {

  constructor() {
    super();
  }

  protected override _jsonModel(): PageModel {
    return models.get(SamplePageWithTableModel);
  }

  protected override _initDetailTable(table: SamplePageWithTableTable) {
    super._initDetailTable(table);

    table.widget('AddRowMenu').on('action', this._onAddRowMenuAction.bind(this));
    table.widget('AddManyMenu').on('action', this._onAddManyMenuAction.bind(this));
    table.widget('DeleteRowMenu').on('action', this._onDeleteRowMenuAction.bind(this));
    table.widget('TileToggleMenu').on('action', this._onTileToggleMenuAction.bind(this));
    let formMenu = table.widget('FormMenu');
    formMenu.on('propertyChange:selected', event => {
      if (event.newValue && !formMenu.form) {
        formMenu.setForm(scout.create(MiniForm, {
          parent: formMenu
        }));
      }
    });
    table.setTileProducer((row: TableRow & { data: Record<string, string> }) => this.createTileForRow(row));
  }

  createTileForRow(row: TableRow & { data: Record<string, string> }): Tile {
    let model = {
      parent: this.detailTable,
      content: '<br><b>ID:</b> ' +
        row.data.id + '<br><b>String Column:</b> ' +
        row.data.string + '<br><b>Number Column:</b> ' +
        row.data.number + '<br><b>Boolean Column:</b> ' +
        row.data.bool
    };
    return scout.create(HtmlTile, model);
  }

  protected _onAddRowMenuAction() {
    this.detailTable.insertRow(this._createRow());
  }

  protected _onAddManyMenuAction() {
    let rows = [];
    for (let i = 0; i < 10; i++) {
      rows.push(this._createRow(this.detailTable.rows.length + i));
    }
    this.detailTable.insertRows(rows);
  }

  protected _onDeleteRowMenuAction() {
    this.detailTable.deleteRows(this.detailTable.selectedRows);
  }

  protected _onTileToggleMenuAction() {
    this.detailTable.setTileMode(!this.detailTable.tileMode);
  }

  protected _createRow(rowNo?: number): TableRowModel {
    rowNo = scout.nvl(rowNo, this.detailTable.rows.length + 1);
    let smartValues = [null, 'es_CR', null, 'pt_BR', 'ro_RO'];

    let row = {
      id: this.detailTable.rows.length + 1,
      string: 'string ' + rowNo,
      smartValue: smartValues[rowNo % smartValues.length],
      number: Math.floor(Math.random() * Math.floor(999999)),
      bool: Math.random() >= 0.5
    };
    return {
      data: row,
      cells: [
        row.id,
        row.string,
        row.smartValue,
        row.number,
        row.bool
      ]
    };
  }

  protected override _loadTableData(searchFilter: SamplePageWithTableSearchFormData): JQuery.Promise<SamplePageWithTableResponse> {
    const resourceUrl = systems.getOrCreate().getEndpointUrl('samplePageWithTable', 'samplePageWithTable');
    const restriction = this._withMaxRowCountContribution(searchFilter);
    return ajax.postJson(resourceUrl + '/list', restriction);
  }

  protected override _transformTableDataToTableRows(tableData: SamplePageWithTableResponse): ObjectOrModel<TableRow>[] {
    return tableData?.items?.map(row => {
      return {
        data: row,
        cells: [
          row.id,
          row.string,
          row.smartValue,
          row.number,
          row.bool
        ]
      };
    });
  }

  override createChildPage(row: TableRow): Page {
    return scout.create(SamplePageWithNodes, {
      parent: this.getOutline()
    });
  }
}

export interface SamplePageWithTableResponse extends DoEntity {
  items: SamplePageWithTableRowDo[];
}

export interface SamplePageWithTableRowDo extends DoEntity {
  id: number;
  string: string;
  smartValue: string;
  number: number;
  bool: boolean;
}
