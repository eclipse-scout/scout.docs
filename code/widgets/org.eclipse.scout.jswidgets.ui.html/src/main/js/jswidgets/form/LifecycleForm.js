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
jswidgets.LifecycleForm = function() {
  jswidgets.LifecycleForm.parent.call(this);
};
scout.inherits(jswidgets.LifecycleForm, scout.Form);

jswidgets.LifecycleForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.LifecycleForm');
};

jswidgets.LifecycleForm.prototype._init = function(model) {
  jswidgets.LifecycleForm.parent.prototype._init.call(this, model);

  this.widget('HasCloseButtonField').on('propertyChange', this._onHasCloseButtonPropertyChange.bind(this));

  var askIfNeedSaveField = this.widget('AskIfNeedSaveField');
  askIfNeedSaveField.setValue(this.askIfNeedSave);
  askIfNeedSaveField.on('propertyChange', this._onAskIfNeedSavePropertyChange.bind(this));
};

jswidgets.LifecycleForm.prototype.importData = function() {
  this.widget('NameField').setValue(this.data.name);
  this.widget('BirthdayField').setValue(this.data.birthday);
};

jswidgets.LifecycleForm.prototype.exportData = function() {
  return {
    name: this.widget('NameField').value,
    birthday: this.widget('BirthdayField').value,
  };
};

jswidgets.LifecycleForm.prototype._save = function(data) {
  if (!this.widget('ExceptionField').value) {
    return jswidgets.LifecycleForm.parent.prototype._save.call(this, data);
  }
  // Simulate a failing asynchronous save operation (e.g. an ajax call)
  return $.resolvedPromise().then(function() {
    throw new Error('Saving failed');
  });
};

jswidgets.LifecycleForm.prototype._onHasCloseButtonPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('CloseMenu').setVisible(event.newValue);
  }
};

jswidgets.LifecycleForm.prototype._onAskIfNeedSavePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.setAskIfNeedSave(event.newValue);
  }
};
