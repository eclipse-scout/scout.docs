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
package org.eclipsescout.demo.widgets.ui.swing.defaultlaf;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.net.NetActivator;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.services.common.session.IClientSessionRegistryService;
import org.eclipse.scout.rt.shared.ui.UserAgent;
import org.eclipse.scout.rt.ui.swing.AbstractSwingApplicationExtension;
import org.eclipse.scout.rt.ui.swing.ISwingEnvironment;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.widgets.client.ClientSession;

public class SwingDefaultApplication extends AbstractSwingApplicationExtension {
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(SwingDefaultApplication.class);

  public SwingDefaultApplication() {
    super("SwingDefaultApplication");
  }

  /**
   * @param extensionId
   */
  public SwingDefaultApplication(String extensionId) {
    super(extensionId);
  }

  @Override
  public Object execStartInSubject(IApplicationContext context, IProgressMonitor progressMonitor) throws Exception {
    LOG.info("Starting {0} {1} application...", Platform.getProduct().getName(), Platform.getProduct().getDefiningBundle().getVersion().toString());
    try {
      NetActivator.install();
    }
    catch (Exception e) {
      // no net handler found
      LOG.warn("NetActivator is not available", e);
    }
    return super.execStartInSubject(context, progressMonitor);
  }

  @Override
  protected IClientSession createClientSession(UserAgent userAgent) {
    return SERVICES.getService(IClientSessionRegistryService.class).newClientSession(ClientSession.class, userAgent);
  }

  @Override
  protected ISwingEnvironment createEnvironment() {
    return new SwingEnvironment();
  }
}
