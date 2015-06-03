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
package org.eclipsescout.demo.bahbah.client.services;

import java.util.Date;

import org.eclipse.scout.commons.IRunnable;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.Client;
import org.eclipse.scout.rt.client.job.ClientJobs;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.services.common.clientnotification.ClientNotificationConsumerEvent;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.platform.service.AbstractService;
import org.eclipse.scout.rt.shared.services.common.clientnotification.IClientNotification;
import org.eclipsescout.demo.bahbah.client.ui.desktop.Desktop;
import org.eclipsescout.demo.bahbah.client.ui.desktop.outlines.pages.UserNodePage;
import org.eclipsescout.demo.bahbah.client.ui.forms.ChatForm;
import org.eclipsescout.demo.bahbah.shared.notification.MessageNotification;
import org.eclipsescout.demo.bahbah.shared.notification.RefreshBuddiesNotification;

@Client
public class BahBahNotificationConsumerService extends AbstractService implements IBahBahNotificationConsumerService {
  private static IScoutLogger LOG = ScoutLogManager.getLogger(BahBahNotificationConsumerService.class);

  private void handleMessage(MessageNotification notification) {
    UserNodePage userPage = getUserNodePage();
    String buddy = notification.getSenderName();

    if (userPage != null) {
      try {
        ChatForm form = userPage.getChatForm(buddy);
        if (form != null) {
          form.getHistoryField().addMessage(false, buddy, form.getUserName(), new Date(), notification.getMessage(), notification.getOriginalServerNode(), notification.getProvidingServerNode());
        }
      }
      catch (Throwable t) {
        LOG.error("handling of remote message failed.", t);
      }
    }
  }

  @Override
  public void handleEvent(ClientNotificationConsumerEvent e, boolean sync) {
    LOG.info("received client notification event for user '" + ClientSessionProvider.currentSession().getUserId() + "'");

    final IClientNotification notification = e.getClientNotification();

    // deal with notification in async jobs to prevent blocking of the model thread
    if (notification instanceof RefreshBuddiesNotification) {
      ClientJobs.schedule(new IRunnable() {
        @Override
        public void run() throws Exception {
          ModelJobs.schedule(new IRunnable() {
            @Override
            public void run() throws Exception {
              handleRefreshBuddies();
            }
          });
        }
      });
    }
    else if (notification instanceof MessageNotification) {
      ClientJobs.schedule(new IRunnable() {
        @Override
        public void run() throws Exception {
          ModelJobs.schedule(new IRunnable() {
            @Override
            public void run() throws Exception {
              handleMessage((MessageNotification) notification);
            }
          });
        }
      });
    }
  }

  private UserNodePage getUserNodePage() {
    if (Desktop.get() == null) {
      return null;
    }
    else {
      return Desktop.get().getUserNodePage();
    }
  }

  private void handleRefreshBuddies() {
    UserNodePage userPage = getUserNodePage();

    if (userPage != null) {
      try {
        LOG.info("refreshing buddies on client");
        userPage.updateBuddyPages();
      }
      catch (Throwable t) {
        LOG.error("handling of remote message failed.", t);
      }
    }
  }

}
