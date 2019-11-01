/*
 * Copyright (c) 2017 BSI Business Systems Integration AG. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0 which accompanies this
 * distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors: BSI Business Systems Integration AG - initial API and
 * implementation
 */
import {TableForm} from '../../index';

export default class EditableTableForm extends TableForm {

constructor() {
  super();
}


_init(model) {
  super._init( model);

  this.table.columns.forEach(function(column) {
    if (column.id === 'IconColumn' || column.id === 'HtmlColumn') {
      return;
    }
    column.setEditable(true);
  });

  // Update field in column properties box (there are no property change listeners yet)
  var column = this.widget('Column.PropertiesBox').column;
  if (column) {
    var editableField = this.widget('EditableField');
    editableField.setValue(column.editable);
  }
}
}
