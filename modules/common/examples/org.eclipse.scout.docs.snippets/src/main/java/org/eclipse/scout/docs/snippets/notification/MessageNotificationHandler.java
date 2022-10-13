/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
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
