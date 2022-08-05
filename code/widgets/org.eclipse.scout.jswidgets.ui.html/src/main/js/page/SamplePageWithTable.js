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
