/*
 * Copyright (c) 2010-2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSI CRM Software License v1.0
 * which accompanies this distribution as bsi-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {BeanColumn} from '@eclipse-scout/core';

export default class ExampleBeanColumn extends BeanColumn {

  constructor() {
    super();
  }

  _renderValue($cell, value) {
    $cell.appendElement('<img>')
      .attr('src', value.image)
      .addClass('example-bean-column-image');
  }

  compare(row1, row2) {
    let cellValue1 = this.table.cell(this, row1).value || {};
    let cellValue2 = this.table.cell(this, row2).value || {};
    return this.comparator.compareIgnoreCase(cellValue1.header, cellValue2.header);
  }
}
