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
jswidgets.RestForm = function() {
  jswidgets.RestForm.parent.call(this);
};
scout.inherits(jswidgets.RestForm, scout.Form);

jswidgets.RestForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.RestForm');
};

jswidgets.RestForm.prototype._init = function(model) {
  jswidgets.RestForm.parent.prototype._init.call(this, model);

  var getButton = this.widget('GetButton');
  getButton.on('click', this._onGetButtonClick.bind(this));
  var postButton = this.widget('PostButton');
  postButton.on('click', this._onPostButtonClick.bind(this));
  var putButton = this.widget('PutButton');
  putButton.on('click', this._onPutButtonClick.bind(this));
  var deleteButton = this.widget('DeleteButton');
  deleteButton.on('click', this._onDeleteButtonClick.bind(this));
  var failButton = this.widget('FailButton');
  failButton.on('click', this._onFailButtonClick.bind(this));
};

jswidgets.RestForm.prototype._addLogEntry = function(message) {
  var logField = this.widget('LogField');
  var log = logField.value || '';
  if (log) {
    log += '\n';
  }
  log += message;
  logField.setValue(log);
};

jswidgets.RestForm.prototype._onGetButtonClick = function(event) {
  scout.ajax.getJson('api/example')
    .then(this._onSuccess.bind(this))
    .catch(this._onFail.bind(this));
};

jswidgets.RestForm.prototype._onPostButtonClick = function(event) {
  scout.ajax.postJson('api/example', {
      hello: 'server'
    }).then(this._onSuccess.bind(this))
    .catch(this._onFail.bind(this));
};

jswidgets.RestForm.prototype._onPutButtonClick = function(event) {
  scout.ajax.putJson('api/example', {
      hello: 'server'
    }).then(this._onSuccess.bind(this))
    .catch(this._onFail.bind(this));
};

jswidgets.RestForm.prototype._onDeleteButtonClick = function(event) {
  scout.ajax.deleteJson('api/example')
    .then(this._onSuccess.bind(this))
    .catch(this._onFail.bind(this));
};

jswidgets.RestForm.prototype._onFailButtonClick = function(event) {
  scout.ajax.deleteJson('api/notexistingurl')
    .then(this._onSuccess.bind(this))
    .catch(this._onFail.bind(this));
};

jswidgets.RestForm.prototype._onSuccess = function(result, textStatus, jqXHR) {
  this._addLogEntry('Request successful. HTTP-Status: ' + jqXHR.status + '. Response: ' + JSON.stringify(result));
};

jswidgets.RestForm.prototype._onFail = function(ajaxError) {
  this._addLogEntry('Request failed! HTTP-Status: ' + ajaxError.jqXHR.status + '. TextStatus: ' + ajaxError.textStatus + '. ErrorThrown: ' + ajaxError.errorThrown + '. RequestOptions: ' + JSON.stringify(ajaxError.requestOptions));
};
