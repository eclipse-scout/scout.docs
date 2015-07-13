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
package org.eclipsescout.demo.widgets.ui.swing;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.ScoutTexts;
import org.eclipse.scout.rt.ui.swing.AbstractSwingApplication;
import org.eclipse.scout.rt.ui.swing.ISwingEnvironment;
import org.eclipse.scout.rt.ui.swing.SwingUtility;

public class SwingApplication extends AbstractSwingApplication {

  private static final IScoutLogger LOG = ScoutLogManager.getLogger(SwingApplication.class);

  private volatile IClientSession m_clientSession;

  @Override
  protected IClientSession startInSubject() throws Exception {
    LOG.info("Starting widget application...");
    try {
      return super.startInSubject();
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  protected IClientSession getClientSession() throws ProcessingException {
    if (m_clientSession == null) {
      synchronized (this) {
        if (m_clientSession == null) {
          m_clientSession = createClientSession();
        }
      }
    }
    return m_clientSession;
  }

  @Override
  protected ISwingEnvironment createSwingEnvironment() {
    SwingUtility.setNlsTexts(ScoutTexts.getInstance());
    return new SwingEnvironment();
  }

  private IClientSession createClientSession() throws ProcessingException {
    return BEANS.get(ClientSessionProvider.class).provide(ClientRunContexts.copyCurrent().withUserAgent(initUserAgent()));
  }
}
