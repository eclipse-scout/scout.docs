/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client;

import java.util.Locale;

import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.services.common.bookmark.IBookmarkService;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.ClientUIPreferences;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.shared.SharedConfigProperties.CreateTunnelToServerBeansProperty;
import org.eclipse.scout.rt.shared.services.common.ping.IPingService;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.SeleniumProperty;
import org.eclipse.scout.widgets.client.ui.desktop.Desktop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientSession extends AbstractClientSession {
  private static final Logger LOG = LoggerFactory.getLogger(ClientSession.class);

  public ClientSession() {
    super(true);
  }

  public static ClientSession get() {
    return ClientSessionProvider.currentSession(ClientSession.class);
  }

  @Override
  protected void execLoadSession() {
    Boolean createTunnelToServerBeans = CONFIG.getPropertyValue(CreateTunnelToServerBeansProperty.class);
    createTunnelToServerBeans = false;
    if (!createTunnelToServerBeans) {
      LOG.info("starting client without a server");
    }

    execInitLocale();
    setDesktop(new Desktop());

    if (createTunnelToServerBeans) {
      BEANS.get(IBookmarkService.class).loadBookmarks();
      BEANS.get(IPingService.class).ping("ping");
    }

    if (CONFIG.getPropertyValue(SeleniumProperty.class)) {
      // remove all UIPreferences when in selenium mode to avoid side effects between tests
      ClientUIPreferences.getClientPreferences(this).clear();
    }
  }

  /**
   * Sets the session locale <i>before</i> the desktop is created. The default implementation sets the locale to
   * {@link Locale#ENGLISH} to get a consistent state across the entire widget application (for most languages, not all
   * tests are localized, except for English). Subclasses may override this method.
   */
  protected void execInitLocale() {
    setLocale(Locale.ENGLISH);
  }
}
