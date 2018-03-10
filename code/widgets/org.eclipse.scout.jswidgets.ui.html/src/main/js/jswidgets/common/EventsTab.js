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
jswidgets.EventsTab = function() {
  jswidgets.EventsTab.parent.call(this);
  this.field = null;
  this._listener = {
    func: this._onEvent.bind(this)
  };
};
scout.inherits(jswidgets.EventsTab, scout.TabItem);

jswidgets.EventsTab.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.EventsTab');
};

jswidgets.EventsTab.prototype._init = function(model) {
  jswidgets.EventsTab.parent.prototype._init.call(this, model);

  this._setField(this.field);
  this.widget('EventsOverviewField').setValue(this.session.text('EventsOverview', scout.app.scoutVersion));
  this.widget('ClearEventLogButton').on('click', this._onClearEventLogClick.bind(this));
};

jswidgets.EventsTab.prototype._postRender = function() {
  jswidgets.EventsTab.parent.prototype._postRender.call(this);
  var overviewField = this.widget('EventsOverviewField');
  // useUiHeight does not work correctly yet -> force relayout
  setTimeout(overviewField.revalidateLayoutTree.bind(overviewField));
};

jswidgets.EventsTab.prototype.setField = function(field) {
  this.setProperty('field', field);
};

jswidgets.EventsTab.prototype._setField = function(field) {
  if (this.field) {
    this.field.removeListener(this._listener);
  }
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }
  this.field.addListener(this._listener);
};

jswidgets.EventsTab.prototype._onEvent = function(event) {
  var logField = this.widget('EventLogField');
  var log = logField.value || '';
  if (log) {
    log += '\n';
  }
  var entry = '';
  var keys = Object.keys(event);
  keys.sort(this._createPropertySortFunc(['source', 'type', 'propertyName', 'oldValue', 'newValue']));
  keys.forEach(function(key) {
    if (key === 'preventDefault') {
      return;
    }
    if (entry) {
      entry += ', ';
    }
    var value = event[key];
    if (value instanceof scout.Widget) {
      value = value.objectType;
    }
    entry += key + ': ' + value;
  });
  log += entry;
  logField.setValue(log);
};

jswidgets.EventsTab.prototype._createPropertySortFunc = function(order) {
  return function(a, b) {
    var ia = order.indexOf(a);
    var ib = order.indexOf(b);
    if (ia > -1 && ib > -1) { // both are in the list
      return ia > ib;
    }
    if (ia > -1) { // B is not in list
      return -1;
    }
    if (ib > -1) { // A is not in list
      return 1;
    }
    return scout.comparators.TEXT.compare(a, b); // both are not in list
  };
};

jswidgets.EventsTab.prototype._onClearEventLogClick = function(event) {
  this.widget('EventLogField').setValue('');
};