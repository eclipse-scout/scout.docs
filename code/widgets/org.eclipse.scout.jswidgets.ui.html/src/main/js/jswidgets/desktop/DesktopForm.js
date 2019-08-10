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
jswidgets.DesktopForm = function() {
  jswidgets.DesktopForm.parent.call(this);
};
scout.inherits(jswidgets.DesktopForm, scout.Form);

jswidgets.DesktopForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.DesktopForm');
};

jswidgets.DesktopForm.prototype._init = function(model) {
  jswidgets.DesktopForm.parent.prototype._init.call(this, model);

  var desktop = this.session.desktop;
  var navigationVisibleField = this.widget('NavigationVisibleField');
  navigationVisibleField.setValue(desktop.navigationVisible);
  navigationVisibleField.on('propertyChange', this._onNavigationVisiblePropertyChange.bind(this));

  var headerVisibleField = this.widget('HeaderVisibleField');
  headerVisibleField.setValue(desktop.headerVisible);
  headerVisibleField.on('propertyChange', this._onHeaderVisiblePropertyChange.bind(this));

  var denseField = this.widget('DenseField');
  denseField.setValue(desktop.dense);
  denseField.on('propertyChange', this._onDensePropertyChange.bind(this));

  this.widget('WidgetActionsBox').setField(desktop);
  this.widget('EventsTab').setField(desktop);
};

jswidgets.DesktopForm.prototype._onNavigationVisiblePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.session.desktop.setNavigationVisible(event.newValue);
  }
};

jswidgets.DesktopForm.prototype._onHeaderVisiblePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.session.desktop.setHeaderVisible(event.newValue);
    this.session.desktop.validateLayoutTree(); // Prevent flickering
  }
};

jswidgets.DesktopForm.prototype._onDensePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.session.desktop.setDense(event.newValue);
  }
};
