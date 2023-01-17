/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.notification.IDesktopNotification;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("7fde3010-625c-46d6-b00e-b1bdc9c1db12")
public class NativeNotificationVisibilityLookupCall extends LocalLookupCall<String> {

  private static final long serialVersionUID = 137589495217391035L;

  @Override
  protected List<LookupRow<String>> execCreateLookupRows() {
    ArrayList<LookupRow<String>> rows = new ArrayList<>();
    rows.add(new LookupRow<>(IDesktopNotification.NATIVE_NOTIFICATION_VISIBILITY_NONE, IDesktopNotification.NATIVE_NOTIFICATION_VISIBILITY_NONE));
    rows.add(new LookupRow<>(IDesktopNotification.NATIVE_NOTIFICATION_VISIBILITY_ALWAYS, IDesktopNotification.NATIVE_NOTIFICATION_VISIBILITY_ALWAYS));
    rows.add(new LookupRow<>(IDesktopNotification.NATIVE_NOTIFICATION_VISIBILITY_BACKGROUND, IDesktopNotification.NATIVE_NOTIFICATION_VISIBILITY_BACKGROUND));
    return rows;
  }
}
