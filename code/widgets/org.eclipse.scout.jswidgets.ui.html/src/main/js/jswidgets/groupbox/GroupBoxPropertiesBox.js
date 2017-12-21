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
jswidgets.GroupBoxPropertiesBox = function() {
  jswidgets.GroupBoxPropertiesBox.parent.call(this);
  this.field = null;
};
scout.inherits(jswidgets.GroupBoxPropertiesBox, scout.GroupBox);

jswidgets.GroupBoxPropertiesBox.prototype._init = function(model) {
  jswidgets.GroupBoxPropertiesBox.parent.prototype._init.call(this, model);
  this._setField(this.field);
};

jswidgets.GroupBoxPropertiesBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.GroupBoxPropertiesBox');
};

jswidgets.GroupBoxPropertiesBox.prototype.setField = function(field) {
  this.setProperty('field', field);
};

jswidgets.GroupBoxPropertiesBox.prototype._setField = function(field) {
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }

  var subLabelField = this.widget('SubLabelField');
  subLabelField.setValue(this.field.subLabel);
  subLabelField.on('propertyChange', this._onPropertyChange.bind(this));

  var borderVisibleField = this.widget('BorderVisibleField');
  borderVisibleField.setValue(this.field.borderVisible);
  borderVisibleField.on('propertyChange', this._onPropertyChange.bind(this));

  var borderDecorationField = this.widget('BorderDecorationField');
  borderDecorationField.setValue(this.field.borderDecoration);
  borderDecorationField.on('propertyChange', this._onPropertyChange.bind(this));

  var expandableField = this.widget('ExpandableField');
  expandableField.setValue(this.field.expandable);
  expandableField.on('propertyChange', this._onPropertyChange.bind(this));

  var expandedField = this.widget('ExpandedField');
  expandedField.setValue(this.field.expanded);
  expandedField.on('propertyChange', this._onPropertyChange.bind(this));

  var scrollableField = this.widget('ScrollableField');
  scrollableField.setValue(this.field.scrollable);
  scrollableField.on('propertyChange', this._onPropertyChange.bind(this));

  var gridColumnCountField = this.widget('GridColumnCountField');
  gridColumnCountField.setValue(this.field.gridColumnCount);
  gridColumnCountField.on('propertyChange', this._onPropertyChange.bind(this));

  var logicalGridField = this.widget('LogicalGridField');
  logicalGridField.setValue(this.field.logicalGrid ? this.field.logicalGrid.objectType : null);
  logicalGridField.on('propertyChange', this._onPropertyChange.bind(this));

  var notificationField = this.widget('NotificationField');
  notificationField.setValue(this.field.notification ? this.field.notification.status.severity : null);
  notificationField.on('propertyChange', this._onPropertyChange.bind(this));

};

jswidgets.GroupBoxPropertiesBox.prototype._onPropertyChange = function(event) {
  if (event.propertyName === 'value' && event.source.id === 'SubLabelField') {
    this.field.setSubLabel(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'BorderVisibleField') {
    this.field.setBorderVisible(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'BorderDecorationField') {
    this.field.setBorderDecoration(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'ExpandableField') {
    this.field.setExpandable(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'ExpandedField') {
    this.field.setExpanded(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'ScrollableField') {
    this.field.setScrollable(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'GridColumnCountField') {
    this.field.setGridColumnCount(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'LogicalGridField') {
    this.field.setLogicalGrid(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'NotificationField') {
    this.field.setNotification(this._createNotification(event.newValue));
  }
};

jswidgets.GroupBoxPropertiesBox.prototype._createNotification = function(severity) {
  if (!severity) {
    return null;
  }
  return scout.create('Notification', {
    parent: this,
    severity: severity,
    message: this.session.text('NotificationMessage', scout.objects.keyByValue(scout.Status.Severity, severity))
  });
};
