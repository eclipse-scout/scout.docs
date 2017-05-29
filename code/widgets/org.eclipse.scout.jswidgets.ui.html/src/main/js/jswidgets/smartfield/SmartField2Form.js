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
jswidgets.SmartField2Form = function() {
  jswidgets.SmartField2Form.parent.call(this);
};
scout.inherits(jswidgets.SmartField2Form, scout.Form);

jswidgets.SmartField2Form.GROUP_SIZE = 2;

jswidgets.SmartField2Form.prototype._init = function(model) {
  jswidgets.SmartField2Form.parent.prototype._init.call(this, model);

  var bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.widget('MainBox'));
  bodyGrid.validate(this.widget('TableSmartFieldBox'));
  bodyGrid.validate(this.widget('TreeSmartFieldBox'));

  this.smartField = this.widget('TableSmartField');
  this.valueField = this.widget('ValueField');

  this.widget('SetValueButton').on('click', function(event) {
    var value = this.valueField.value;
    this.smartField.setValue(value);
  }.bind(this));

  var treeSmartField = this.widget('TreeSmartField');
  var expandTreeNodesField = this.widget('ExpandTreeNodesField');

  expandTreeNodesField.setValue(treeSmartField.browseAutoExpandAll);
  expandTreeNodesField.on('propertyChange', function(event) {
    if ('value' === event.propertyName) {
      treeSmartField.setBrowseAutoExpandAll(event.newValue);
    }
  });

  var loadIncrementalField = this.widget('LoadIncrementalField');
  loadIncrementalField.setValue(treeSmartField.browseLoadIncremental);
  loadIncrementalField.on('propertyChange', function(event) {
    if ('value' === event.propertyName) {
      treeSmartField.setBrowseLoadIncremental(event.newValue);
    }
  });

};

jswidgets.SmartField2Form.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.SmartField2Form');
};
