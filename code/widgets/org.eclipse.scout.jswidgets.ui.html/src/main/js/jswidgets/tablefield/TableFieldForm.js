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
jswidgets.TableFieldForm = function() {
  jswidgets.TableFieldForm.parent.call(this);

  this.rowNo = 1;
  this.groupNo = 1;
};
scout.inherits(jswidgets.TableFieldForm, scout.Form);

jswidgets.TableFieldForm.GROUP_SIZE = 2;

jswidgets.TableFieldForm.prototype._init = function(model) {
  jswidgets.TableFieldForm.parent.prototype._init.call(this, model);

  this.table = this.widget('Table');
  this.widget('AddRowsMenu').on('action', this._onAddRowsMenuAction.bind(this));
  this.widget('ColumnVisibilityMenu').on('action', this._onColumnVisibilityMenuAction.bind(this));
  this.widget('GroupingStyleTopMenu').on('action', this._onGroupingStyleAction.bind(this, scout.Table.GroupingStyle.TOP));
  this.widget('GroupingStyleBottomMenu').on('action', this._onGroupingStyleAction.bind(this, scout.Table.GroupingStyle.BOTTOM));
  this.widget('FormFieldPropertiesBox').setField(this.widget('TableField'));
};

jswidgets.TableFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TableFieldForm');
};

jswidgets.TableFieldForm.prototype._onAddRowsMenuAction = function() {
  this.table.insertRow({
    cells: ['Row #' + this.rowNo, this.groupNo, this.rowNo]
  });
  if (this.rowNo % jswidgets.TableFieldForm.GROUP_SIZE === 0) {
    this.groupNo++;
  }
  this.rowNo++;
};

jswidgets.TableFieldForm.prototype._onGroupingStyleAction = function(groupingStyle) {
  this.table.setProperty('groupingStyle', groupingStyle);
};

jswidgets.TableFieldForm.prototype._onColumnVisibilityMenuAction = function() {
  var table = this.widget('Table');
  var column = table._columnById('GroupNo');
  column.setVisible(!column.visible);
};
