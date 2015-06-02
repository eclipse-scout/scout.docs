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

import org.eclipse.scout.commons.UriUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.rt.client.services.common.bookmark.IBookmarkService;
import org.eclipse.scout.rt.client.servicetunnel.http.ClientHttpServiceTunnel;
import org.eclipse.scout.rt.shared.services.common.code.CODES;
import org.eclipse.scout.rt.shared.services.common.ping.IPingService;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.widgets.client.ui.desktop.Desktop;

public class ClientSession extends AbstractClientSession {

  private static IScoutLogger logger = ScoutLogManager.getLogger(ClientSession.class);
  private String m_product;
  private boolean m_footless;

  public ClientSession() {
    super(true);
  }

  @Override
  public void execLoadSession() throws ProcessingException {
    m_footless = !Boolean.valueOf(getBundle().getBundleContext().getProperty("server.available"));
    if (isFootless()) {
      logger.warn("starting client without a server!");
    }
    else {
      setServiceTunnel(new ClientHttpServiceTunnel(this, UriUtility.toUrl(getBundle().getBundleContext().getProperty("server.url")), (String) getBundle().getHeaders().get("Bundle-Version")));
    }

    CODES.getAllCodeTypes(org.eclipsescout.demo.widgets.shared.Activator.PLUGIN_ID);

    setLocale(Locale.ENGLISH);

    setDesktop(new Desktop());
    if (!isFootless()) {
      SERVICES.getService(IBookmarkService.class).loadBookmarks();
      getServiceTunnel().setClientNotificationPollInterval(2000L);
      SERVICES.getService(IPingService.class).ping("ping");
    }
  }

  /**
   * @return session in current ThreadContext
   */
  public static ClientSession get() {
    return ClientJob.getCurrentSession(ClientSession.class);
  }

  /**
   * @return true if there is no server attached or available
   */
  public boolean isFootless() {
    return m_footless;
  }
}
