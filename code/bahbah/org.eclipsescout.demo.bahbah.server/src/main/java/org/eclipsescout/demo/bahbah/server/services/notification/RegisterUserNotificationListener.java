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

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.server.services.common.clustersync.IClusterNotificationListener;
import org.eclipse.scout.rt.server.services.common.clustersync.IClusterNotificationMessage;
import org.eclipsescout.demo.bahbah.shared.services.process.IUserProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterUserNotificationListener implements IClusterNotificationListener {
  private static final Logger LOG = LoggerFactory.getLogger(RegisterUserNotificationListener.class);

  @Override
  public void onNotification(IClusterNotificationMessage notification) {
    if (isInteresting(notification)) {
      RegisterUserNotification registerUserNotification = (RegisterUserNotification) notification.getNotification();
      try {
        BEANS.get(IUserProcessService.class).registerUserInternal(registerUserNotification.getUserName());
      }
      catch (RuntimeException e) {
        LOG.error("Unable to register user internal", e);
      }
    }
  }

  public boolean isInteresting(IClusterNotificationMessage notification) {
    return (notification.getNotification() instanceof RegisterUserNotification);
  }

}
