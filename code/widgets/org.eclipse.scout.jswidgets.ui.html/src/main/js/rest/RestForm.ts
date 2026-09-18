/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {ajax, AjaxCall, AjaxError, App, Button, Event, Form, FormModel, InitModelOf, models, numbers} from '@eclipse-scout/core';
import RestFormModel from './RestFormModel';
import {RestFormWidgetMap} from '../index';

export class RestForm extends Form {
  declare widgetMap: RestFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(RestFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let getButton = this.widget('GetButton');
    getButton.on('click', this._onGetButtonClick.bind(this));
    let postButton = this.widget('PostButton');
    postButton.on('click', this._onPostButtonClick.bind(this));
    let putButton = this.widget('PutButton');
    putButton.on('click', this._onPutButtonClick.bind(this));
    let deleteButton = this.widget('DeleteButton');
    deleteButton.on('click', this._onDeleteButtonClick.bind(this));
    let failButton = this.widget('FailButton');
    failButton.on('click', this._onFailButtonClick.bind(this));
  }

  protected _addLogEntry(message: string) {
    let logField = this.widget('LogField');
    let log = logField.value || '';
    if (log) {
      log += '\n';
    }
    log += message;
    logField.setValue(log);
  }

  protected _onGetButtonClick(event: Event<Button>) {
    // Use createCallJson (instead of the getJson shorthand) to get access to the AjaxCall and thus to the HTTP status of the response.
    let call = ajax.createCallJson({url: 'api/example', method: 'GET'});
    call.call()
      .then(result => this._onSuccess(result, call))
      .catch(this._onFail.bind(this));
  }

  protected _onPostButtonClick(event: Event<Button>) {
    let call = ajax.createCallJson({url: 'api/example', method: 'POST', data: JSON.stringify({hello: 'server'})});
    call.call()
      .then(result => this._onSuccess(result, call))
      .catch(this._onFail.bind(this));
  }

  protected _onPutButtonClick(event: Event<Button>) {
    let call = ajax.createCallJson({url: 'api/example', method: 'PUT', data: JSON.stringify({hello: 'server'})});
    call.call()
      .then(result => this._onSuccess(result, call))
      .catch(this._onFail.bind(this));
  }

  protected _onDeleteButtonClick(event: Event<Button>) {
    let call = ajax.createCallJson({url: 'api/example', method: 'DELETE'});
    call.call()
      .then(result => this._onSuccess(result, call))
      .catch(this._onFail.bind(this));
  }

  protected _onFailButtonClick(event: Event<Button>) {
    let exceptionId = numbers.ensure(this.widget('ExceptionTypeField').value) || 0;
    let call = ajax.createCall({url: 'api/example/error/' + exceptionId, method: 'GET'});
    call.call()
      .then(result => this._onSuccess(result, call))
      .catch(this._onFail.bind(this));
  }

  protected _onSuccess(result: Response, call: AjaxCall) {
    this._addLogEntry('Request successful. HTTP-Status: ' + call.lastXhr?.status + '. Response: ' + JSON.stringify(result));
  }

  protected _onFail(ajaxError: AjaxError) {
    App.get().errorHandler.handle(ajaxError);
    this._addLogEntry('Request failed! HTTP-Status: ' + ajaxError.jqXHR.status + '. TextStatus: ' + ajaxError.textStatus + '. ErrorThrown: ' + ajaxError.errorThrown + '. RequestOptions: ' + JSON.stringify(ajaxError.requestOptions));
  }
}

type Response = {
  message: string;
};
