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
import {ajax, Form, models} from '@eclipse-scout/core';
import RestFormModel from './RestFormModel';

export default class RestForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(RestFormModel);
  }

  _init(model) {
    super._init(model);

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
  }

  _addLogEntry(message) {
    var logField = this.widget('LogField');
    var log = logField.value || '';
    if (log) {
      log += '\n';
    }
    log += message;
    logField.setValue(log);
  }

  _onGetButtonClick(event) {
    ajax.getJson('api/example')
      .then(this._onSuccess.bind(this))
      .catch(this._onFail.bind(this));
  }

  _onPostButtonClick(event) {
    ajax.postJson('api/example', {
      hello: 'server'
    }).then(this._onSuccess.bind(this))
      .catch(this._onFail.bind(this));
  }

  _onPutButtonClick(event) {
    ajax.putJson('api/example', {
      hello: 'server'
    }).then(this._onSuccess.bind(this))
      .catch(this._onFail.bind(this));
  }

  _onDeleteButtonClick(event) {
    ajax.removeJson('api/example')
      .then(this._onSuccess.bind(this))
      .catch(this._onFail.bind(this));
  }

  _onFailButtonClick(event) {
    ajax.get('api/notexistingurl')
      .then(this._onSuccess.bind(this))
      .catch(this._onFail.bind(this));
  }

  _onSuccess(result, textStatus, jqXHR) {
    this._addLogEntry('Request successful. HTTP-Status: ' + jqXHR.status + '. Response: ' + JSON.stringify(result));
  }

  _onFail(ajaxError) {
    this._addLogEntry('Request failed! HTTP-Status: ' + ajaxError.jqXHR.status + '. TextStatus: ' + ajaxError.textStatus + '. ErrorThrown: ' + ajaxError.errorThrown + '. RequestOptions: ' + JSON.stringify(ajaxError.requestOptions));
  }
}
