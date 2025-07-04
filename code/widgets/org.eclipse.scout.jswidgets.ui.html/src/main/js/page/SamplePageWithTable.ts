/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {ajax, BaseDoEntity, dataObjects, HtmlTile, models, ObjectOrModel, Page, PageModel, PageWithTable, scout, strings, systems, TableRow, TableRowModel, Tile, typeName, ValueDo} from '@eclipse-scout/core';
import SamplePageWithTableModel from './SamplePageWithTableModel';
import {MiniForm, SamplePageWithNodes, SamplePageWithTableRestrictionDo, SamplePageWithTableTable, SampleTableCustomizer, SampleTableCustomizerDo} from '../index';

export class SamplePageWithTable extends PageWithTable {
  declare detailTable: SamplePageWithTableTable;

  protected override _jsonModel(): PageModel {
    return models.get(SamplePageWithTableModel);
  }

  protected override _initDetailTable(table: SamplePageWithTableTable) {
    super._initDetailTable(table);

    table.setCustomizer(scout.create(SampleTableCustomizer, {
      parent: table
    }));

    table.widget('AddRowMenu').on('action', this._onAddRowMenuAction.bind(this));
    table.widget('AddManyMenu').on('action', this._onAddManyMenuAction.bind(this));
    table.widget('DeleteRowMenu').on('action', this._onDeleteRowMenuAction.bind(this));

    table.widget('CompactToggleMenu').setSelected(this.detailTable.compact);
    table.widget('CompactToggleMenu').on('action', e => this.detailTable.setCompact(e.source.selected));

    table.widget('TileToggleMenu').setSelected(this.detailTable.tileMode);
    table.widget('TileToggleMenu').on('action', e => this.detailTable.setTileMode(e.source.selected));

    let formMenu = table.widget('FormMenu');
    formMenu.on('propertyChange:selected', event => {
      if (event.newValue && !formMenu.form) {
        formMenu.setForm(scout.create(MiniForm, {
          parent: formMenu
        }));
      }
    });
    table.setTileProducer(row => this._createTileForRow(row));
  }

  protected _createTileForRow(row: TableRow): Tile {
    let data = (row as TableRow & { data: Record<string, string> }).data; // see _transformTableDataToTableRows
    let content = '<br><b>ID:</b> ' + strings.encode(data.id) +
      '<br><b>String Column:</b> ' + strings.encode(data.string) +
      '<br><b>Number Column:</b> ' + strings.encode(data.number) +
      '<br><b>Boolean Column:</b> ' + strings.encode(data.bool);
    return scout.create(HtmlTile, {
      parent: this.detailTable,
      content: content
    });
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

  protected override _loadTableData(searchFilter: SamplePageWithTableRestrictionDo): JQuery.Promise<SamplePageWithTableResponse> {
    const resourceUrl = systems.getOrCreate().getEndpointUrl('samplePageWithTable', 'samplePageWithTable');
    const restriction = this._withMaxRowCountContribution(searchFilter);
    if (this.detailTable.isCustomizable()) {
      let customizerData = this.detailTable.customizer.getCustomizerData() as SampleTableCustomizerDo;
      if (customizerData) {
        let contribution = scout.create(SampleCustomColumnRestrictionContributionDo, {
          columnTypes: new Map()
        });
        customizerData.columns.forEach(customColumn => {
          contribution.columnTypes.set(customColumn.columnId, customColumn.columnType);
        });
        dataObjects.addContribution(contribution, restriction);
      }
    }
    return ajax.postDataObject(resourceUrl + '/list', restriction);
  }

  protected override _transformTableDataToTableRows(tableData: SamplePageWithTableResponse): ObjectOrModel<TableRow>[] {
    return tableData?.items?.map(item => {
      let cells = [
        item.id,
        item.string,
        item.smartValue,
        item.number,
        item.bool
      ];
      let rowContribution = dataObjects.getContribution(SampleCustomColumnTableRowContributionDo, item);
      if (rowContribution) {
        rowContribution.cells.forEach((value, columnId) => {
          let column = this.detailTable.columnByUuid(columnId);
          if (column) {
            cells[column.index] = value.value;
          }
        });
      }
      return {
        data: item,
        cells: cells
      };
    });
  }

  protected override _createChildPage(row: TableRow): Page {
    return scout.create(SamplePageWithNodes, {
      parent: this.outline
    });
  }
}

@typeName('jswidgets.SamplePageWithTableResponse')
export class SamplePageWithTableResponse extends BaseDoEntity {
  items: SamplePageWithTableRowDo[];
}

@typeName('jswidgets.SamplePageWithTableRow')
export class SamplePageWithTableRowDo extends BaseDoEntity {
  id: number;
  string: string;
  smartValue: string;
  number: number;
  bool: boolean;
}

@typeName('jswidgets.SampleCustomColumnRestrictionContribution')
export class SampleCustomColumnRestrictionContributionDo extends BaseDoEntity {
  columnTypes: Map<string, string>;
}

@typeName('jswidgets.SampleCustomColumnTableRowContribution')
export class SampleCustomColumnTableRowContributionDo extends BaseDoEntity {
  cells: Map<string, ValueDo<any>>;
}

