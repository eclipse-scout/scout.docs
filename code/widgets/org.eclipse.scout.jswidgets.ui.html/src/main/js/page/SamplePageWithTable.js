import {models, PageWithTable, strings} from '@eclipse-scout/core';
import SamplePageWithTableModel from './SamplePageWithTableModel';
import * as $ from 'jquery';

export default class SamplePageWithTable extends PageWithTable {

  constructor() {
    super();
  }


  _jsonModel() {
    return models.get(SamplePageWithTableModel);
  }

  _loadTableData(searchFilter) {
    var searchFormStringFieldValue = searchFilter.stringField;
    var filter = function(element) {
      if (!strings.hasText(searchFormStringFieldValue)) {
        return true;
      }
      return strings.contains(element.string, searchFormStringFieldValue);
    };
    var data = [{
      id: 1,
      string: 'string 01',
      number: 103012,
      bool: true
    }, {
      id: 2,
      string: 'string 02',
      number: 5685,
      bool: true
    }, {
      id: 3,
      string: 'string 03',
      number: 9214575,
      bool: false
    }, {
      id: 4,
      string: 'string 04',
      number: 168461,
      bool: false
    }, {
      id: 5,
      string: 'string 05',
      number: 959161,
      bool: true
    }];
    return $.resolvedPromise(data.filter(filter));
  }

  _transformTableDataToTableRows(tableData) {
    return tableData
      .map(function(row) {
        return {
          data: row,
          cells: [
            row.id,
            row.string,
            row.number,
            row.bool
          ]
        };
      });
  }
}
