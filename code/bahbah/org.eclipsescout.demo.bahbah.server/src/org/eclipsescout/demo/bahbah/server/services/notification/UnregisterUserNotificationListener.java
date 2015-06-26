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
package org.eclipsescout.demo.bahbah.server.services.notification;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.services.common.clustersync.IClusterNotificationListener;
import org.eclipse.scout.rt.server.services.common.clustersync.IClusterNotificationMessage;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.bahbah.shared.services.process.IUserProcessService;

public class UnregisterUserNotificationListener implements IClusterNotificationListener {
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(UnregisterUserNotificationListener.class);

  @Override
  public void onNotification(IClusterNotificationMessage notification) {
    if (isInteresting(notification)) {
      UnregisterUserNotification unregisterUserNotification = (UnregisterUserNotification) notification.getNotification();
      try {
        SERVICES.getService(IUserProcessService.class).unregisterUserInternal(unregisterUserNotification.getUserName());
      }
      catch (ProcessingException e) {
        LOG.error("Unable to unregister user internal", e);
      }
    }
  }

  public boolean isInteresting(IClusterNotificationMessage notification) {
    return (notification.getNotification() instanceof UnregisterUserNotification);
  }

}
