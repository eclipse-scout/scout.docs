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
jswidgets.GroupBoxForm = function() {
  jswidgets.GroupBoxForm.parent.call(this);
};
scout.inherits(jswidgets.GroupBoxForm, scout.Form);

jswidgets.GroupBoxForm.prototype._init = function(model) {
  jswidgets.GroupBoxForm.parent.prototype._init.call(this, model);

  var groupBox = this.widget('DetailBox');
  var borderVisibleField = this.widget('BorderVisibleField');
  borderVisibleField.setValue(groupBox.borderVisible);
  borderVisibleField.on('propertyChange', this._onBorderVisibleChange.bind(this));

  var borderDecorationField = this.widget('BorderDecorationField');
  borderDecorationField.setValue(groupBox.borderDecoration);
  borderDecorationField.on('propertyChange', this._onBorderDecorationChange.bind(this));

  var expandableField = this.widget('ExpandableField');
  expandableField.setValue(groupBox.expandable);
  expandableField.on('propertyChange', this._onExpandableChange.bind(this));

  var expandedField = this.widget('ExpandedField');
  expandedField.setValue(groupBox.expanded);
  expandedField.on('propertyChange', this._onExpandedChange.bind(this));

  var gridColumnCountField = this.widget('GridColumnCountField');
  gridColumnCountField.setValue(groupBox.gridColumnCount);
  gridColumnCountField.on('propertyChange', this._onGridColumnCountChange.bind(this));

  var logicalGridField = this.widget('LogicalGridField');
  logicalGridField.setValue(groupBox.logicalGrid ? groupBox.logicalGrid.objectType : null);
  logicalGridField.on('propertyChange', this._onLogicalGridChange.bind(this));

  var notificationField = this.widget('NotificationField');
  notificationField.setValue(groupBox.notification ? groupBox.notification.status.severity: null);
  notificationField.on('propertyChange', this._onNotificationChange.bind(this));

  var toggleVisibilityField = this.widget('ToggleVisibilityField');
  toggleVisibilityField.on('propertyChange', this._onToggleVisibilityChange.bind(this));

  var toggleVisibilityButton = this.widget('ToggleVisibilityButton');
  toggleVisibilityButton.setEnabled(!!toggleVisibilityField.value);
  toggleVisibilityButton.on('click', this._onToggleVisibilityButtonClick.bind(this));

  this.widget('FormFieldPropertiesBox').setField(groupBox);
};

jswidgets.GroupBoxForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.GroupBoxForm');
};

jswidgets.GroupBoxForm.prototype._onBorderVisibleChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DetailBox').setBorderVisible(event.newValue);
  }
};

jswidgets.GroupBoxForm.prototype._onBorderDecorationChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DetailBox').setBorderDecoration(event.newValue);
  }
};

jswidgets.GroupBoxForm.prototype._onExpandableChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DetailBox').setExpandable(event.newValue);
  }
};

jswidgets.GroupBoxForm.prototype._onExpandedChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DetailBox').setExpanded(event.newValue);
  }
};

jswidgets.GroupBoxForm.prototype._onGridColumnCountChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DetailBox').setGridColumnCount(event.newValue);
  }
};

jswidgets.GroupBoxForm.prototype._onLogicalGridChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DetailBox').setLogicalGrid(event.newValue);
  }
};

jswidgets.GroupBoxForm.prototype._onNotificationChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('DetailBox').setNotification(this._createNotification(event.newValue));
  }
};

jswidgets.GroupBoxForm.prototype._onToggleVisibilityChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('ToggleVisibilityButton').setEnabled(!!event.newValue);
  }
};

jswidgets.GroupBoxForm.prototype._onToggleVisibilityButtonClick = function(event) {
  var field = this.widget(this.widget('ToggleVisibilityField').value);
  field.setVisible(!field.visible);
};

jswidgets.GroupBoxForm.prototype._createNotification = function(severity) {
  if (!severity ) {
    return null;
  }
  return scout.create('Notification', {
    parent: this,
    severity: severity,
    message: this.session.text('NotificationMessage', scout.objects.keyByValue(scout.Status.Severity, severity))
  });
};
