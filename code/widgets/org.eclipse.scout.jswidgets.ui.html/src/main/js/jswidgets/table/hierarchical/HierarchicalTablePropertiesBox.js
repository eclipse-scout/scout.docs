/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
jswidgets.HierarchicalTablePropertiesBox = function() {
  jswidgets.HierarchicalTablePropertiesBox.parent.call(this);
  this.table = null;
};
scout.inherits(jswidgets.HierarchicalTablePropertiesBox, jswidgets.TablePropertiesBox);

jswidgets.HierarchicalTablePropertiesBox.prototype._jsonModel = function() {
  return scout.models.extend('jswidgets.HierarchicalTablePropertiesBox', jswidgets.HierarchicalTablePropertiesBox.parent.prototype._jsonModel.call(this));
};

jswidgets.HierarchicalTablePropertiesBox.prototype._init = function(model) {
  jswidgets.HierarchicalTablePropertiesBox.parent.prototype._init.call(this, model);

  this._setTable(this.table);
};

jswidgets.HierarchicalTablePropertiesBox.prototype.setTable = function(table) {
  this.setProperty('table', table);
};

jswidgets.HierarchicalTablePropertiesBox.prototype._setTable = function(table) {
  jswidgets.HierarchicalTablePropertiesBox.parent.prototype._setTable.call(this, table);
  this._setProperty('table', table);
  if (!this.table) {
    return;
  }

  var hierarchicalStyleField = this.widget('HierarchicalStyleField');
  hierarchicalStyleField.setValue(this.table.hierarchicalStyle);
  hierarchicalStyleField.on('propertyChange', this._onHierarchicalStylePropertyChange.bind(this));

  var extendedHierarchyPaddingField = this.widget('ExtendedHierarchyPaddingField');
  extendedHierarchyPaddingField.setValue(this.table.cssClassAsArray().indexOf('extended-row-level-padding') > -1);
  extendedHierarchyPaddingField.on('propertyChange', this._onExtendedHierarchyPaddingPropertyChange.bind(this));
};

jswidgets.HierarchicalTablePropertiesBox.prototype._onHierarchicalStylePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.setHierarchicalStyle(event.newValue);
  }
};

jswidgets.HierarchicalTablePropertiesBox.prototype._onExtendedHierarchyPaddingPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.table.toggleCssClass('extended-row-level-padding', event.newValue);
  }
};
