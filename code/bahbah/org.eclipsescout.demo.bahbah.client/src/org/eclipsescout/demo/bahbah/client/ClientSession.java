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

import org.eclipse.scout.commons.UriUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.rt.client.services.common.clientnotification.IClientNotificationConsumerService;
import org.eclipse.scout.rt.client.servicetunnel.http.ClientHttpServiceTunnel;
import org.eclipse.scout.rt.shared.services.common.code.CODES;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.bahbah.client.services.IBahBahNotificationConsumerService;
import org.eclipsescout.demo.bahbah.client.ui.desktop.Desktop;
import org.eclipsescout.demo.bahbah.shared.services.process.IUserProcessService;

public class ClientSession extends AbstractClientSession {

  private static IScoutLogger logger = ScoutLogManager.getLogger(ClientSession.class);

  public ClientSession() {
    super(true);
  }

  @Override
  public void execLoadSession() throws ProcessingException {
    setServiceTunnel(new ClientHttpServiceTunnel(this, UriUtility.toUrl(getBundle().getBundleContext().getProperty("server.url"))));

    //pre-load all known code types
    CODES.getAllCodeTypes(org.eclipsescout.demo.bahbah.shared.Activator.PLUGIN_ID);

    // turn client notification polling on
    getServiceTunnel().setClientNotificationPollInterval(1000L);

    // set the notification listener service (this service will be called when the client receives a notification)
    IBahBahNotificationConsumerService notificationHandlerService = SERVICES.getService(IBahBahNotificationConsumerService.class);
    SERVICES.getService(IClientNotificationConsumerService.class).addClientNotificationConsumerListener(this, notificationHandlerService);

    setDesktop(new Desktop());
  }

  @Override
  public void execStoreSession() throws ProcessingException {
    // disable notification polling with -1
    ClientSession.get().getServiceTunnel().setClientNotificationPollInterval(-1);
    SERVICES.getService(IUserProcessService.class).unregisterUser();
  }

  @SuppressWarnings("unchecked")
  @FormData
  public ICode<Integer> getPermission() {
    return getSharedContextVariable(IUserProcessService.PERMISSION_KEY, ICode.class);
  }

  /**
   * @return session in current ThreadContext
   */
  public static ClientSession get() {
    return ClientJob.getCurrentSession(ClientSession.class);
  }
}
