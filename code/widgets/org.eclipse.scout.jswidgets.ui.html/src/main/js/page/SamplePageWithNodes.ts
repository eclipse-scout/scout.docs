import {arrays, models, objects, PageWithNodes, scout} from '@eclipse-scout/core';
import SamplePageWithNodesModel from './SamplePageWithNodesModel';
import $ from 'jquery';

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
    let table = row.getTable();
    let columnsByIndex = objects.createMap();
    table.columns.forEach(column => {
      columnsByIndex[column.index] = column;
    });
    // Search the first visible cell considering the originally defined column order (ignoring the column order changes the user did)
    let firstDefinedVisibleCell = arrays.find(row.cells, (cell, index) => {
      let column = columnsByIndex[index];
      return column.visible && column.displayable;
    });
    if (firstDefinedVisibleCell) {
      return firstDefinedVisibleCell.text;
    }
    return super.computeTextForRow(row);
  }
}
