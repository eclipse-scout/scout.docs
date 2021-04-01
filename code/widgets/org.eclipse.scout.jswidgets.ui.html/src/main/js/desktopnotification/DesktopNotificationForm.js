/*
 * Copyright (c) 2021 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */

import {Button, Form, models, scout, Status} from '@eclipse-scout/core';
import DesktopNotificationFormModel from './DesktopNotificationFormModel';
import DesktopNotification from '@eclipse-scout/core/src/desktop/notification/DesktopNotification';

export default class DesktopNotificationForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(DesktopNotificationFormModel);
  }

  _init(model) {
    super._init(model);

    let desktop = this.session.desktop;
    let button = this.widget('Button');
    this.widget('ClosableField').setValue(true);
    this.widget('MessageField').setValue('This is a notification');
    this.widget('DurationField').setValue(5000);
    this.widget('StatusSeverityField').setValue(Status.Severity.OK);
    this.widget('NativeNotificationVisibilityField').setValue(DesktopNotification.NativeNotificationVisibility.NONE);

    button.on('click', this._onButtonClick.bind(this));

    this.widget('WidgetActionsBox').setField(desktop);
    this.widget('EventsTab').setField(desktop);
  }

  _onButtonClick(event) {
    scout.create('DesktopNotification', {
      parent: this,
      closable: this.widget('ClosableField').value,
      duration: this.widget('DurationField').value,
      nativeOnly: this.widget('NativeOnlyField').value,
      nativeNotificationVisibility: this.widget('NativeNotificationVisibilityField').value,
      status: {
        severity: this.widget('StatusSeverityField').value,
        message: this.widget('MessageField').value
      }
    }).show();
  }
}
