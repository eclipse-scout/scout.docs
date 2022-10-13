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

import {StaticLookupCall} from '@eclipse-scout/core';
import DesktopNotification from '@eclipse-scout/core/src/desktop/notification/DesktopNotification';

export default class NativeNotificationVisibilityLookupCall extends StaticLookupCall {

  constructor() {
    super();
  }

  _data() {
    return NativeNotificationVisibilityLookupCall.DATA;
  }

  static DATA = [
    [DesktopNotification.NativeNotificationVisibility.NONE, 'none'],
    [DesktopNotification.NativeNotificationVisibility.BACKGROUND, 'background'],
    [DesktopNotification.NativeNotificationVisibility.ALWAYS, 'always']
  ];
}
