import {arrays, models, objects, PageWithNodes, scout} from '@eclipse-scout/core';
import SamplePageWithNodesModel from './SamplePageWithNodesModel';
import * as $ from 'jquery';

export default class SamplePageWithNodes extends PageWithNodes {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(SamplePageWithNodesModel);
  }

  _createChildPages() {
    return $.resolvedPromise([
      scout.create('jswidgets.SamplePageWithTable', this._pageParam()),
      scout.create('jswidgets.SamplePageWithNodes', this._pageParam())
    ]);
  }

  /**
   * @override
   */
  computeTextForRow(row) {
    var table = row.getTable();
    var columnsByIndex = objects.createMap();
    table.columns.forEach(function(column) {
      columnsByIndex[column.index] = column;
    });
    // Search the first visible cell considering the originally defined column order (ignoring the column order changes the user did)
    var firstDefinedVisibleCell = arrays.find(row.cells, function(cell, index) {
      var column = columnsByIndex[index];
      return column.visible && column.displayable;
    });
    if (firstDefinedVisibleCell) {
      return firstDefinedVisibleCell.text;
    }
    return super.computeTextForRow(row);
  }
}
