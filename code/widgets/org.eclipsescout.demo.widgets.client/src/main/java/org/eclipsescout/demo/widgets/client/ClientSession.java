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
package org.eclipsescout.demo.widgets.client;

import java.util.Locale;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.services.common.bookmark.IBookmarkService;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.shared.SharedConfigProperties.CreateTunnelToServerBeansProperty;
import org.eclipse.scout.rt.shared.services.common.code.CODES;
import org.eclipse.scout.rt.shared.services.common.ping.IPingService;
import org.eclipsescout.demo.widgets.client.ui.desktop.Desktop;

public class ClientSession extends AbstractClientSession {
  private static IScoutLogger logger = ScoutLogManager.getLogger(ClientSession.class);

  private String m_product;

  public ClientSession() {
    super(true);
  }

  /**
   * @return session in current ThreadContext
   */
  public static ClientSession get() {
    return ClientSessionProvider.currentSession(ClientSession.class);
  }

  @Override
  protected void execLoadSession() throws ProcessingException {
    Boolean createTunnelToServerBeans = CONFIG.getPropertyValue(CreateTunnelToServerBeansProperty.class);
    if (!createTunnelToServerBeans) {
      logger.info("starting client without a server!");
    }

    CODES.getAllCodeTypes("org.eclipsescout.demo.widgets.shared");

    setLocale(Locale.ENGLISH);

    setDesktop(new Desktop());
    if (createTunnelToServerBeans) {
      BEANS.get(IBookmarkService.class).loadBookmarks();
      BEANS.get(IPingService.class).ping("ping");
    }
  }

  @Override
  protected void execStoreSession() throws ProcessingException {
//    if (!isFootless()) {
//      getServiceTunnel().setClientNotificationPollInterval(-1L); // stop ClientNotificationPollingJob
//    }
  }
}
