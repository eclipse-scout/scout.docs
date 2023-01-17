/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {TableForm} from '../../index';
import {InitModelOf} from '@eclipse-scout/core';

export class EditableTableForm extends TableForm {

  constructor() {
    super();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.table.columns.forEach(column => {
      if (column.id === 'IconColumn' || column.id === 'HtmlColumn') {
        return;
      }
      column.setEditable(true);
    });

    // Update field in column properties box (there are no property change listeners yet)
    let column = this.widget('Column.PropertiesBox').column;
    if (column) {
      let editableField = this.widget('EditableField');
      editableField.setValue(column.editable);
    }
  }
}
