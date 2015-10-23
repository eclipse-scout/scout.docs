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

import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.services.common.bookmark.IBookmarkService;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop.DesktopStyle;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.context.RunContexts;
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
  protected void execLoadSession() {
    Boolean createTunnelToServerBeans = CONFIG.getPropertyValue(CreateTunnelToServerBeansProperty.class);
    createTunnelToServerBeans = false;
    if (!createTunnelToServerBeans) {
      logger.info("starting client without a server!");
    }

    execInitLocale();
    CODES.getAllCodeTypes("org.eclipsescout.demo.widgets.shared");
    setDesktop(createDesktop());

    if (createTunnelToServerBeans) {
      BEANS.get(IBookmarkService.class).loadBookmarks();
      BEANS.get(IPingService.class).ping("ping");
    }
  }

  /**
   * Start web-application with URL http://[host:port]/?desktopStyle=BENCH to activate bench-only mode.
   */
  protected IDesktop createDesktop() {
    DesktopStyle desktopStyle = DesktopStyle.DEFAULT;
    Object desktopStyleProperty = RunContexts.copyCurrent().getProperty("desktopStyle");
    if (desktopStyleProperty instanceof String) {
      desktopStyle = DesktopStyle.valueOf((String) desktopStyleProperty);
    }
    return new Desktop(desktopStyle);
  }

  /**
   * Sets the session locale <i>before</i> the desktop is created. The default implementation sets the locale to
   * {@link Locale#ENGLISH} to get a consistent state across the entire widget application (for most languages, not all
   * tests are localized, except for English). Subclasses may override this method.
   */
  protected void execInitLocale() {
    setLocale(Locale.ENGLISH);
  }

  @Override
  protected void execStoreSession() {
//    if (!isFootless()) {
//      getServiceTunnel().setClientNotificationPollInterval(-1L); // stop ClientNotificationPollingJob
//    }
  }
}
