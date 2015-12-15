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
package org.eclipsescout.demo.bahbah.server.services.process;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.clientnotification.ClientNotificationRegistry;
import org.eclipse.scout.rt.server.services.common.clustersync.IClusterSynchronizationService;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipsescout.demo.bahbah.server.ServerSession;
import org.eclipsescout.demo.bahbah.shared.notification.MessageNotification;
import org.eclipsescout.demo.bahbah.shared.security.CreateNotificationPermission;
import org.eclipsescout.demo.bahbah.shared.services.process.INotificationProcessService;
import org.eclipsescout.demo.bahbah.shared.util.SharedUserUtility;

public class NotificationProcessService implements INotificationProcessService {

  @Override
  public void sendMessage(String buddyName, String message) {
    // permission validation
    if (!ACCESS.check(new CreateNotificationPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    // input validation
    if (!StringUtility.hasText(message) || !StringUtility.hasText(buddyName)) {
      throw new VetoException();
    }
    if (StringUtility.length(message) > INotificationProcessService.MESSAGE_MAX_LENGTH || StringUtility.length(buddyName) > SharedUserUtility.MAX_USERNAME_LENGTH) {
      throw new VetoException();
    }

    // process message
    String nodeId = null;
    IClusterSynchronizationService s = BEANS.opt(IClusterSynchronizationService.class);
    if (s != null) {
      nodeId = s.getNodeId();
    }

    BEANS.get(ClientNotificationRegistry.class).putForUser(buddyName, new MessageNotification(ServerSession.get().getUserId(), message, nodeId));
  }

}
