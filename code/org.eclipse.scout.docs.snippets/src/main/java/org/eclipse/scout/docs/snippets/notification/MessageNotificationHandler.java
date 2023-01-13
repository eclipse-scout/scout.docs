/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets.notification;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.shared.notification.INotificationHandler;

//tag::notificationHandler[]
public class MessageNotificationHandler implements INotificationHandler<MessageNotification> {

  // tag::handleNotification[]
  @Override
  public void handleNotification(final MessageNotification notification) {
    // end::notificationHandler[]
    ModelJobs.schedule(() -> {
      IDesktop desktop = ClientSessionProvider.currentSession().getDesktop();
      // e.g. send dataChanged event to UI listeners
      desktop.dataChanged(notification.getMessage());
    }, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
  }
  // end::handleNotification[]
}
