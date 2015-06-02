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
package org.eclipsescout.demo.minicrm.spec.ui.swing;

import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.services.common.session.IClientSessionRegistryService;
import org.eclipse.scout.rt.spec.client.SpecJob;
import org.eclipse.scout.rt.ui.swing.AbstractSwingApplication;
import org.eclipse.scout.rt.ui.swing.ISwingEnvironment;
import org.eclipse.scout.service.SERVICES;
import org.eclipse.scout.testing.client.TestingClientSessionRegistryService;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.eclipsescout.demo.minicrm.client.ClientSession;
import org.eclipsescout.demo.minicrm.ui.swing.SwingEnvironment;

public class MinicrmSpecApplication extends AbstractSwingApplication {
  private static IScoutLogger LOG = ScoutLogManager
      .getLogger(MinicrmSpecApplication.class);
  private TestingClientSessionRegistryService m_testingClientSessionRegistryService = null;

  @Override
  protected Object startInSubject(IApplicationContext context)
      throws Exception {
    LOG.info("Starting App");
    ScoutClientTestRunner.setDefaultClientSessionClass(ClientSession.class);
    m_testingClientSessionRegistryService = TestingClientSessionRegistryService
        .registerTestingClientSessionRegistryService();
    new SpecJob(ClientSession.class, Platform.getProduct()
        .getDefiningBundle().getSymbolicName()).schedule(200);
    return super.startInSubject(context);
  }

  @Override
  protected ISwingEnvironment createSwingEnvironment() {
    return new SwingEnvironment();
  }

  @Override
  protected IClientSession getClientSession() {
    return SERVICES.getService(IClientSessionRegistryService.class)
        .newClientSession(ClientSession.class, initUserAgent());
  }

  @Override
  public void stop() {
    super.stop();
    TestingClientSessionRegistryService
        .unregisterTestingClientSessionRegistryService(m_testingClientSessionRegistryService);
  }

}
