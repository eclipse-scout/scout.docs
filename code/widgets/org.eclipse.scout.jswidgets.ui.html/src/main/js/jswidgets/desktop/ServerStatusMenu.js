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
jswidgets.ServerStatusMenu = function() {
  jswidgets.ServerStatusMenu.parent.call(this);
  this.serverStatus = scout.Status.Severity.OK;
};
scout.inherits(jswidgets.ServerStatusMenu, scout.FormMenu);

jswidgets.ServerStatusMenu.prototype._init = function(model) {
  jswidgets.ServerStatusMenu.parent.prototype._init.call(this, model);
  this._updateServerStatus();
};

jswidgets.ServerStatusMenu.prototype._render = function() {
  jswidgets.ServerStatusMenu.parent.prototype._render.call(this);
  this._renderServerStatus();
};

jswidgets.ServerStatusMenu.prototype._updateServerStatus = function() {
  var ajaxOptions = {
    type: 'GET',
    dataType: 'json',
    contentType: 'application/json; charset=UTF-8',
    cache: false,
    url: 'api/serverstatus',
    timeout: 0
  };
  this.ajaxCall = scout.create('AjaxCall', {
    ajaxOptions: ajaxOptions,
    name: 'server status request'
  }, {
    ensureUniqueId: false
  });
  this.ajaxCall.retryIntervals = [];
  this.ajaxCall.call()
    .done(function(result) {
      this.setServerStatus(result.severity);
      setTimeout(this._updateServerStatus.bind(this), 1000);
    }.bind(this))
    .fail(
      function() {
        this.setServerStatus(scout.Status.Severity.ERROR);
        setTimeout(this._updateServerStatus.bind(this), 3000);
      }.bind(this)
    );
};

jswidgets.ServerStatusMenu.prototype.setServerStatus = function(serverStatus) {
  this.setProperty('serverStatus', serverStatus);
};


jswidgets.ServerStatusMenu.prototype._renderServerStatus = function() {
  this.$container.removeClass(scout.Status.SEVERITY_CSS_CLASSES);
  this.$container.addClass(scout.Status.cssClassForSeverity(this.serverStatus));
  switch(this.serverStatus){
    case scout.Status.Severity.OK:
      this.setText('Server running');
      this.setIconId('font:icomoon ');
      return;
    default:
      this.setText('Server stopped');
    this.setIconId('font:icomoon ');
      return;
  }
};
