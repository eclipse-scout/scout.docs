/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
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
