/*******************************************************************************
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
jswidgets.CheckableStyleLookupCall = function() {
  jswidgets.CheckableStyleLookupCall.parent.call(this);
};
scout.inherits(jswidgets.CheckableStyleLookupCall, scout.StaticLookupCall);

jswidgets.CheckableStyleLookupCall.prototype._data = function() {
  return jswidgets.CheckableStyleLookupCall.DATA;
};

jswidgets.CheckableStyleLookupCall.DATA = [
  [scout.Table.CheckableStyle.CHECKBOX, 'checkbox'],
  [scout.Table.CheckableStyle.TABLE_ROW, 'table_row'],
  [scout.Table.CheckableStyle.CHECKBOX_TABLE_ROW, 'checkbox_table_row']
];
