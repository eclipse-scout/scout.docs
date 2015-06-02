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

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.services.common.clientnotification.AllUserFilter;
import org.eclipse.scout.rt.server.services.common.clientnotification.IClientNotificationService;
import org.eclipse.scout.rt.server.services.common.clientnotification.SingleUserFilter;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.bahbah.server.ServerSession;
import org.eclipsescout.demo.bahbah.shared.notification.MessageNotification;
import org.eclipsescout.demo.bahbah.shared.notification.RefreshBuddiesNotification;
import org.eclipsescout.demo.bahbah.shared.security.CreateNotificationPermission;
import org.eclipsescout.demo.bahbah.shared.services.process.INotificationProcessService;
import org.eclipsescout.demo.bahbah.shared.util.SharedUserUtility;

public class NotificationProcessService extends AbstractService implements INotificationProcessService {
  private static IScoutLogger logger = ScoutLogManager.getLogger(NotificationProcessService.class);

  private final static long TIMEOUT = 1000 * 60 * 10; // 10min

  @Override
  public void sendRefreshBuddies() throws ProcessingException {
    if (!ACCESS.check(new CreateNotificationPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    logger.info("queue 'update buddies' notification on server");
    IClientNotificationService service = SERVICES.getService(IClientNotificationService.class);
    service.putNotification(new RefreshBuddiesNotification(), new AllUserFilter(TIMEOUT));
  }

  @Override
  public void sendMessage(String buddyName, String message) throws ProcessingException {
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
    IClientNotificationService service = SERVICES.getService(IClientNotificationService.class);
    service.putNotification(new MessageNotification(ServerSession.get().getUserId(), message), new SingleUserFilter(buddyName, TIMEOUT));
  }
}
