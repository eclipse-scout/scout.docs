/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {BeanColumn, TableRow} from '@eclipse-scout/core';
import {ExampleBean} from './index';

export class ExampleBeanColumn extends BeanColumn<ExampleBean> {

  protected override _renderValue($cell: JQuery, value: ExampleBean) {
    $cell.appendElement('<img>')
      .attr('src', value.image)
      .addClass('example-bean-column-image');
  }

  override compare(row1: TableRow, row2: TableRow) {
    let cellValue1 = this.table.cell(this, row1).value || {};
    let cellValue2 = this.table.cell(this, row2).value || {};
    return this.comparator.compareIgnoreCase(cellValue1.header, cellValue2.header);
  }
}
