/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.bahbah.client;

import java.util.Date;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.shared.notification.INotificationHandler;
import org.eclipsescout.demo.bahbah.client.ui.desktop.Desktop;
import org.eclipsescout.demo.bahbah.client.ui.desktop.outlines.pages.UserNodePage;
import org.eclipsescout.demo.bahbah.client.ui.forms.ChatForm;
import org.eclipsescout.demo.bahbah.shared.notification.MessageNotification;

//tag::notificationHandler[]
public class MessageNotificationHandler implements INotificationHandler<MessageNotification> {

  // tag::handleNotification[]
  @Override
  public void handleNotification(final MessageNotification notification) {
    // end::notificationHandler[]
    ModelJobs.schedule(new IRunnable() {
      @Override
      public void run() throws Exception {
        UserNodePage userPage = getUserNodePage();
        String buddy = notification.getSenderName();

        if (userPage != null) {
          ChatForm form = userPage.getChatForm(buddy);
          if (form != null) {
            form.getHistoryField().addMessage(false, buddy, form.getUserName(), new Date(), notification.getMessage());
          }
        }
      }
    }, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
  }
  // end::handleNotification[]

  private UserNodePage getUserNodePage() {
    if (Desktop.get() == null) {
      return null;
    }
    else {
      return Desktop.get().getUserNodePage();
    }
  }
}
