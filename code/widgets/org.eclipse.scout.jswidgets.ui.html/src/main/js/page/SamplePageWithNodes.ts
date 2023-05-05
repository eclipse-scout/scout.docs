/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {arrays, models, objects, Page, PageModel, PageWithNodes, scout, TableRow} from '@eclipse-scout/core';
import SamplePageWithNodesModel from './SamplePageWithNodesModel';
import $ from 'jquery';
import {SamplePageWithTable} from '../index';

export class SamplePageWithNodes extends PageWithNodes {

  constructor() {
    super();
  }

  protected override _jsonModel(): PageModel {
    return models.get(SamplePageWithNodesModel);
  }

  protected override _createChildPages(): JQuery.Promise<Page[]> {
    return $.resolvedPromise([
      scout.create(SamplePageWithTable, this._pageParam()),
      scout.create(SamplePageWithNodes, this._pageParam())
    ]);
  }

  override computeTextForRow(row: TableRow): string {
    let table = row.getTable();
    let columnsByIndex = objects.createMap();
    table.columns.forEach(column => {
      columnsByIndex[column.index] = column;
    });
    // Search the first visible cell considering the originally defined column order (ignoring the column order changes the user did)
    let firstDefinedVisibleCell = arrays.find(row.cells, (cell, index) => {
      let column = columnsByIndex[index];
      return column.visible;
    });
    if (firstDefinedVisibleCell) {
      return firstDefinedVisibleCell.text;
    }
    return super.computeTextForRow(row);
  }
}
