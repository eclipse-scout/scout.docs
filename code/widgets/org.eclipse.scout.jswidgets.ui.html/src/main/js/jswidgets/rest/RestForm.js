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

  $.ajaxSetup({ cache: false });

  var callButton = this.widget('CallButton');
  callButton.on('click', this._onCallButtonClick.bind(this));
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

jswidgets.RestForm.prototype._onCallButtonClick = function(event) {
  $.getJSON('api/serverstatus')
    .done(function(result, textStatus, jqXHR) {
      this._addLogEntry('Request successful. HTTP-Status: ' + jqXHR.status + '. Response: ' + JSON.stringify(result));
    }.bind(this))
    .fail(function(jqXHR, textStatus, errorThrown) {
      this._addLogEntry('Request failed! HTTP-Status: ' + jqXHR.status + '. TextStatus: ' + textStatus + '. ErrorThrown: ' + errorThrown);
    }.bind(this));
};
