import {models, PageWithTable, scout, strings} from '@eclipse-scout/core';
import SamplePageWithTableModel from './SamplePageWithTableModel';
import $ from 'jquery';

export default class SamplePageWithTable extends PageWithTable {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(SamplePageWithTableModel);
  }

  _initDetailTable(table) {
    super._initDetailTable(table);

    table.widget('AddRowMenu').on('action', this._onAddRowMenuAction.bind(this));
    table.widget('AddManyMenu').on('action', this._onAddManyMenuAction.bind(this));
    table.widget('DeleteRowMenu').on('action', this._onDeleteRowMenuAction.bind(this));
    table.widget('TileToggleMenu').on('action', this._onTileToggleMenuAction.bind(this));
    let formMenu = table.widget('FormMenu');
    formMenu.on('propertyChange:selected', event => {
      if (event.newValue && !formMenu.form) {
        formMenu.setForm(scout.create('jswidgets.MiniForm', {
          parent: formMenu
        }));
      }
    });
    table.setTileProducer(row => this.createTileForRow(row));
  }

  createTileForRow(row) {
    let model = {
      parent: this.detailTable,
      content: '<br><b>ID:</b> ' +
        row.data.id + '<br><b>String Column:</b> ' +
        row.data.string + '<br><b>Number Column:</b> ' +
        row.data.number + '<br><b>Boolean Column:</b> ' +
        row.data.bool
    };
    return scout.create('HtmlTile', model);
  }

  _onAddRowMenuAction() {
    this.detailTable.insertRow(this._createRow());
  }

  _onAddManyMenuAction() {
    let rows = [];
    for (let i = 0; i < 10; i++) {
      rows.push(this._createRow(this.detailTable.rows.length + i));
    }
    this.detailTable.insertRows(rows);
  }

  _onDeleteRowMenuAction() {
    this.detailTable.deleteRows(this.detailTable.selectedRows);
  }

  _onTileToggleMenuAction() {
    this.detailTable.setTileMode(!this.detailTable.tileMode);
  }

  _createRow(rowNo) {
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

  _loadTableData(searchFilter) {
    let searchFormStringFieldValue = searchFilter.stringField;
    let filter = element => {
      if (!strings.hasText(searchFormStringFieldValue)) {
        return true;
      }
      return strings.contains(element.string, searchFormStringFieldValue);
    };
    let data = [{
      id: 1,
      string: 'string 1',
      smartValue: null,
      number: 103012,
      bool: true
    }, {
      id: 3,
      string: 'string 2',
      smartValue: null,
      number: 9214575,
      bool: false
    }, {
      id: 2,
      string: 'string 3',
      smartValue: 'es_GT',
      number: 5685,
      bool: true
    }, {
      id: 4,
      string: 'string 4',
      smartValue: 'de_CH',
      number: 168461,
      bool: false
    }, {
      id: 5,
      string: 'string 5',
      smartValue: 'th_TH',
      number: 959161,
      bool: true
    }];
    return $.resolvedPromise(data.filter(filter));
  }

  _transformTableDataToTableRows(tableData) {
    return tableData
      .map(row => {
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

  createChildPage(row) {
    return scout.create('jswidgets.SamplePageWithNodes', {
      parent: this.getOutline()
    });
  }
}
