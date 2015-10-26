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

import org.eclipse.scout.commons.IRunnable;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.shared.notification.INotificationHandler;
import org.eclipsescout.demo.bahbah.client.ui.desktop.Desktop;
import org.eclipsescout.demo.bahbah.client.ui.desktop.outlines.pages.UserNodePage;
import org.eclipsescout.demo.bahbah.shared.notification.RefreshBuddiesNotification;

public class RefreshBuddiesNotificationHandler implements INotificationHandler<RefreshBuddiesNotification> {
  private static IScoutLogger LOG = ScoutLogManager.getLogger(RefreshBuddiesNotificationHandler.class);

  @Override
  public void handleNotification(RefreshBuddiesNotification notification) {
    ModelJobs.schedule(new IRunnable() {
      @Override
      public void run() throws Exception {
        UserNodePage userPage = getUserNodePage();

        if (userPage != null) {
          LOG.info("refreshing buddies on client");
          userPage.updateBuddyPages();
        }
      }
    });
  }

  private UserNodePage getUserNodePage() {
    if (Desktop.get() == null) {
      return null;
    }
    else {
      return Desktop.get().getUserNodePage();
    }
  }
}
