/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {BrowserField, Button, dates, Event, Form, FormModel, GroupBox, InitModelOf, models, numbers} from '@eclipse-scout/core';
import BrowserFieldFormModel from './BrowserFieldFormModel';
import {BrowserFieldFormWidgetMap} from '../index';

export class BrowserFieldForm extends Form {
  declare widgetMap: BrowserFieldFormWidgetMap;

  browserField: BrowserField;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(BrowserFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.browserField = this.widget('BrowserField');

    let scrollBarEnabledField = this.widget('ScrollBarEnabledField');
    scrollBarEnabledField.setValue(this.browserField.scrollBarEnabled);
    scrollBarEnabledField.on('propertyChange:value', event => this.browserField.setScrollBarEnabled(event.newValue));

    let sandboxEnabledField = this.widget('SandboxEnabledField');
    sandboxEnabledField.setValue(this.browserField.sandboxEnabled);
    sandboxEnabledField.on('propertyChange:value', event => this.browserField.setSandboxEnabled(event.newValue));

    let sandboxPermissionsField = this.widget('SandboxPermissionsField');
    sandboxPermissionsField.setValue(this.browserField.sandboxPermissions);
    sandboxPermissionsField.on('propertyChange:value', event => this.browserField.setSandboxPermissions(event.newValue));

    let locationField = this.widget('LocationField');
    locationField.setValue(this.browserField.location);
    locationField.on('propertyChange:value', event => this.browserField.setLocation(event.newValue));

    let trackLocationField = this.widget('TrackLocationField');
    trackLocationField.setValue(this.browserField.trackLocation);
    trackLocationField.on('propertyChange:value', event => this.browserField.setTrackLocation(event.newValue));

    let showInExternalWindowField = this.widget('ShowInExternalWindowField');
    showInExternalWindowField.setValue(this.browserField.showInExternalWindow);
    showInExternalWindowField.on('propertyChange:value', event => {
      this.browserField.showInExternalWindow = event.newValue;
      (this.browserField.parent as GroupBox).rerenderControls();
    });

    let autoCloseExternalWindowField = this.widget('AutoCloseExternalWindowField');
    autoCloseExternalWindowField.setValue(this.browserField.autoCloseExternalWindow);
    autoCloseExternalWindowField.on('propertyChange:value', event => this.browserField.setAutoCloseExternalWindow(event.newValue));

    let externalWindowButtonTextField = this.widget('ExternalWindowButtonTextField');
    externalWindowButtonTextField.setValue(this.browserField.externalWindowButtonText);
    externalWindowButtonTextField.on('propertyChange:value', event => this.browserField.setExternalWindowButtonText(event.newValue));

    let externalWindowFieldTextField = this.widget('ExternalWindowFieldTextField');
    externalWindowFieldTextField.setValue(this.browserField.externalWindowFieldText);
    externalWindowFieldTextField.on('propertyChange:value', event => this.browserField.setExternalWindowFieldText(event.newValue));

    this.browserField.on('propertyChange:location', event => this.widget('LocationField').setValue(event.newValue));

    this.widget('PostTextMessageButton').on('click', this._onPostTextMessageButtonClick.bind(this));
    this.widget('PostJsonMessageButton').on('click', this._onPostJsonMessageButtonClick.bind(this));

    this.widget('FormFieldPropertiesBox').setField(this.browserField);
    this.widget('GridDataBox').setField(this.browserField);
    this.widget('WidgetActionsBox').setField(this.browserField);
    this.widget('FormFieldActionsBox').setField(this.browserField);
    this.widget('EventsTab').setField(this.browserField);
  }

  protected _onPostTextMessageButtonClick(event: Event<Button>) {
    let msg = 'Lucky number: ' + (Math.floor(Math.random() * 100) + 1);
    this.browserField.postMessage(msg, '/');
  }

  protected _onPostJsonMessageButtonClick(event: Event<Button>) {
    let msg = {
      'id': numbers.randomId(),
      'name': 'Test',
      'timestamp': dates.format(new Date(), this.session.locale, 'hh:mm:ss.SSS')
    };
    this.browserField.postMessage(msg, '/');
  }
}
