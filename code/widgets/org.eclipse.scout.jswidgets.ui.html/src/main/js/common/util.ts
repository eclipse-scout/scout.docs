/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {DesktopNotification, Menu, scout, Status} from '@eclipse-scout/core';

export const util = {
  showMenuActionMessage(menu: Menu) {
    if (menu.isToggleAction()) {
      // Don't show message box if it is a toggle action
      return;
    }
    scout.create(DesktopNotification, {
      parent: menu,
      status: {
        severity: Status.Severity.OK,
        message: menu.session.text('MenuClickMessage', menu.text)
      }
    }).show();
  }
};
