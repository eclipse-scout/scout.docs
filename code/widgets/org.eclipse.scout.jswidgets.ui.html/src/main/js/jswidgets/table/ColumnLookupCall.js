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
jswidgets.ColumnLookupCall = function(table) {
  jswidgets.ColumnLookupCall.parent.call(this);

  this.data = [];
  this.setTable(table);
};
scout.inherits(jswidgets.ColumnLookupCall, scout.StaticLookupCall);

jswidgets.ColumnLookupCall.prototype._data = function() {
  return this.data;
};

jswidgets.ColumnLookupCall.prototype.setTable = function(table) {
  this.table = table;
  this._rebuildData();
};

jswidgets.ColumnLookupCall.prototype._rebuildData = function() {
  this.data = this.table.columns.map(function(column) {
    return [column, column.text];
  });
};
