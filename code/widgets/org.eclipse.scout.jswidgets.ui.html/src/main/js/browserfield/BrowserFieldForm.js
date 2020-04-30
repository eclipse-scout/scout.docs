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
import {Form, models} from '@eclipse-scout/core';
import BrowserFieldFormModel from './BrowserFieldFormModel';

export default class BrowserFieldForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(BrowserFieldFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    this.browserField = this.widget('BrowserField');

    let scrollBarEnabledField = this.widget('ScrollBarEnabledField');
    scrollBarEnabledField.setValue(this.browserField.scrollBarEnabled);
    scrollBarEnabledField.on('propertyChange', this._onScrollBarEnabledPropertyChange.bind(this));

    let sandboxEnabledField = this.widget('SandboxEnabledField');
    sandboxEnabledField.setValue(this.browserField.sandboxEnabled);
    sandboxEnabledField.on('propertyChange', this._onSandboxEnabledPropertyChange.bind(this));

    let sandboxPermissionsField = this.widget('SandboxPermissionsField');
    sandboxPermissionsField.setValue(this.browserField.sandboxPermissions);
    sandboxPermissionsField.on('propertyChange', this._onSandboxPermissionsPropertyChange.bind(this));

    let locationField = this.widget('LocationField');
    locationField.setValue(this.browserField.location);
    locationField.on('propertyChange', this._onLocationPropertyChange.bind(this));

    let trackLocationField = this.widget('TrackLocationField');
    trackLocationField.setValue(this.browserField.trackLocation);
    trackLocationField.on('propertyChange', this._onTrackLocationPropertyChange.bind(this));

    let showInExternalWindowField = this.widget('ShowInExternalWindowField');
    showInExternalWindowField.setValue(this.browserField.showInExternalWindow);
    showInExternalWindowField.on('propertyChange', this._onShowInExternalWindowPropertyChange.bind(this));

    let autoCloseExternalWindowField = this.widget('AutoCloseExternalWindowField');
    autoCloseExternalWindowField.setValue(this.browserField.autoCloseExternalWindow);
    autoCloseExternalWindowField.on('propertyChange', this._onAutoCloseExternalWindowPropertyChange.bind(this));

    let externalWindowButtonTextField = this.widget('ExternalWindowButtonTextField');
    externalWindowButtonTextField.setValue(this.browserField.externalWindowButtonText);
    externalWindowButtonTextField.on('propertyChange', this._onExternalWindowButtonTextPropertyChange.bind(this));

    let externalWindowFieldTextField = this.widget('ExternalWindowFieldTextField');
    externalWindowFieldTextField.setValue(this.browserField.externalWindowFieldText);
    externalWindowFieldTextField.on('propertyChange', this._onExternalWindowFieldTextPropertyChange.bind(this));

    this.browserField.on('propertyChange', this._onBrowserFieldPropertyChange.bind(this));

    this.widget('FormFieldPropertiesBox').setField(this.browserField);
    this.widget('GridDataBox').setField(this.browserField);
    this.widget('WidgetActionsBox').setField(this.browserField);
    this.widget('FormFieldActionsBox').setField(this.browserField);
    this.widget('EventsTab').setField(this.browserField);
  }

  _onBrowserFieldPropertyChange(event) {
    if (event.propertyName === 'location') {
      this.widget('LocationField').setValue(event.newValue);
    }
  }

  _onScrollBarEnabledPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.browserField.setScrollBarEnabled(event.newValue);
    }
  }

  _onSandboxEnabledPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.browserField.setSandboxEnabled(event.newValue);
    }
  }

  _onSandboxPermissionsPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.browserField.setSandboxPermissions(event.newValue);
    }
  }

  _onLocationPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.browserField.setLocation(event.newValue);
    }
  }

  _onTrackLocationPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.browserField.setTrackLocation(event.newValue);
    }
  }

  _onShowInExternalWindowPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.browserField.showInExternalWindow = event.newValue;
      this.browserField.parent.rerenderControls();
      // Validate layout immediately to prevent flickering
      this.browserField.parent.htmlBody.validateLayoutTree();
    }
  }

  _onAutoCloseExternalWindowPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.browserField.setAutoCloseExternalWindow(event.newValue);
    }
  }

  _onExternalWindowButtonTextPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.browserField.setExternalWindowButtonText(event.newValue);
    }
  }

  _onExternalWindowFieldTextPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.browserField.setExternalWindowFieldText(event.newValue);
    }
  }
}
