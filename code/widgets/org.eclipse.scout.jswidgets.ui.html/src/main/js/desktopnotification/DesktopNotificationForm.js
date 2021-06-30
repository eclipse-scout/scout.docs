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

import {Button, Form, models, scout} from '@eclipse-scout/core';
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

    let notification = scout.create('DesktopNotification', {parent: this});
    this.widget('ClosableField').setValue(notification.closable);
    this.widget('MessageField').setValue('Hi there!');
    this.widget('DurationField').setValue(notification.duration);
    this.widget('DelayField').setValue(0);
    this.widget('StatusSeverityField').setValue(notification.status.severity);
    this.widget('NativeNotificationTitleField').setValue(notification.nativeNotificationTitle);
    this.widget('NativeNotificationIconIdField').setValue(notification.nativeNotificationIconId);
    this.widget('NativeNotificationVisibilityField').setValue(notification.nativeNotificationVisibility);
    notification.destroy();

    let button = this.widget('Button');
    button.on('click', this._onButtonClick.bind(this));
  }

  _onButtonClick(event) {
    let notification = scout.create('DesktopNotification', {
      parent: this,
      closable: this.widget('ClosableField').value,
      duration: this.widget('DurationField').value,
      iconId: this.widget('IconField').value,
      nativeOnly: this.widget('NativeOnlyField').value,
      nativeNotificationTitle: this.widget('NativeNotificationTitleField').value,
      nativeNotificationIconId: this.widget('NativeNotificationIconIdField').value,
      nativeNotificationVisibility: this.widget('NativeNotificationVisibilityField').value,
      status: {
        severity: this.widget('StatusSeverityField').value,
        message: this.widget('MessageField').value
      }
    });
    this.widget('EventsTab').setField(notification);

    const delay = this.widget('DelayField').value;
    if (delay > 0) {
      setTimeout(() => notification.show(), delay);
    } else {
      notification.show();
    }
  }
}
