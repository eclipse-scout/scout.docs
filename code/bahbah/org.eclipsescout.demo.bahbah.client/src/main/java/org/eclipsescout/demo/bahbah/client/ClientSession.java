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

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.common.code.CODES;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipsescout.demo.bahbah.client.ui.desktop.Desktop;
import org.eclipsescout.demo.bahbah.shared.services.process.IUserProcessService;

public class ClientSession extends AbstractClientSession {
  private static IScoutLogger logger = ScoutLogManager.getLogger(ClientSession.class);

  public ClientSession() {
    super(true);
  }

  /**
   * @return session in current ThreadContext
   */
  public static ClientSession get() {
    return ClientSessionProvider.currentSession(ClientSession.class);
  }

  @FormData
  @SuppressWarnings("unchecked")
  public ICode<Integer> getPermission() {
    return getSharedContextVariable(IUserProcessService.PERMISSION_KEY, ICode.class);
  }

  @Override
  protected void execLoadSession() throws ProcessingException {

    //pre-load all known code types
    CODES.getAllCodeTypes("org.eclipsescout.demo.bahbah.shared");

    // turn client notification polling on
//    getServiceTunnel().setClientNotificationPollInterval(1000L);

    setDesktop(new Desktop());
  }

  @Override
  protected void execStoreSession() throws ProcessingException {
    // disable notification polling with -1
//    ClientSession.get().getServiceTunnel().setClientNotificationPollInterval(-1);
    BEANS.get(IUserProcessService.class).unregisterUser();
  }

}
