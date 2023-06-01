/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {ajax, AjaxError, App, Button, Event, Form, FormModel, InitModelOf, models, numbers} from '@eclipse-scout/core';
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
    let websocketButton = this.widget('WebsocketButton');
    websocketButton.on('click', this._onWebsocketButtonClick.bind(this));
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
    ajax.getJson('api/example')
      .then(this._onSuccess.bind(this))
      .catch(this._onFail.bind(this));
  }

  protected _onPostButtonClick(event: Event<Button>) {
    ajax.postJson('api/example', {
      hello: 'server'
    }).then(this._onSuccess.bind(this))
      .catch(this._onFail.bind(this));
  }

  protected _onPutButtonClick(event: Event<Button>) {
    ajax.putJson('api/example', {
      hello: 'server'
    }).then(this._onSuccess.bind(this))
      .catch(this._onFail.bind(this));
  }

  protected _onDeleteButtonClick(event: Event<Button>) {
    ajax.removeJson('api/example')
      .then(this._onSuccess.bind(this))
      .catch(this._onFail.bind(this));
  }

  protected _onFailButtonClick(event: Event<Button>) {
    let exceptionId = numbers.ensure(this.widget('ExceptionTypeField').value) || 0;
    ajax.get('api/example/error/' + exceptionId)
      .then(this._onSuccess.bind(this))
      .catch(this._onFail.bind(this));
  }

  protected _onWebsocketButtonClick(event: Event<Button>) {
    const socket = new WebSocket('ws://localhost:8084/ws/cgu');

    socket.addEventListener('open', event => {
      socket.send('Hello Server!');
    });

    socket.addEventListener('message', event => {
      this._addLogEntry(event.data);
    });
  }

  protected _onSuccess(result: Response, textStatus: string, jqXHR: JQuery.jqXHR) {
    this._addLogEntry('Request successful. HTTP-Status: ' + jqXHR.status + '. Response: ' + JSON.stringify(result));
  }

  protected _onFail(ajaxError: AjaxError) {
    App.get().errorHandler.handle(ajaxError);
    this._addLogEntry('Request failed! HTTP-Status: ' + ajaxError.jqXHR.status + '. TextStatus: ' + ajaxError.textStatus + '. ErrorThrown: ' + ajaxError.errorThrown + '. RequestOptions: ' + JSON.stringify(ajaxError.requestOptions));
  }
}

type Response = {
  message: string;
};
