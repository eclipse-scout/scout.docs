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
package org.eclipsescout.demo.bahbah.server;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.IServerJobFactory;
import org.eclipse.scout.rt.server.IServerJobService;
import org.eclipse.scout.rt.server.ITransactionRunnable;
import org.eclipse.scout.rt.server.ServerJob;
import org.eclipse.scout.rt.server.services.common.clustersync.IClusterSynchronizationService;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.bahbah.server.services.db.IDbSetupService;
import org.eclipsescout.demo.bahbah.server.services.notification.RegisterUserNotificationListener;
import org.eclipsescout.demo.bahbah.server.services.notification.UnregisterUserNotificationListener;

public class ServerApplication implements IApplication {
  private static IScoutLogger LOG = ScoutLogManager.getLogger(ServerApplication.class);

  @Override
  public Object start(IApplicationContext context) throws Exception {
    final IServerJobFactory factory = SERVICES.getService(IServerJobService.class).createJobFactory();
    ServerJob initJob = factory.create("Install Db schema if necessary", new ITransactionRunnable() {

      @Override
      public IStatus run(IProgressMonitor monitor) throws ProcessingException {
        try {
          SERVICES.getService(IDbSetupService.class).installDb();
        }
        catch (Throwable t) {
          return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error while installing the bahbah server Db schema", t);
        }

        SERVICES.getService(IClusterSynchronizationService.class).addListener(new RegisterUserNotificationListener());
        SERVICES.getService(IClusterSynchronizationService.class).addListener(new UnregisterUserNotificationListener());

        return Status.OK_STATUS;
      }
    });
    initJob.schedule();
    initJob.join(20000);
    LOG.info("bahbah server initialized");
    return EXIT_OK;
  }

  @Override
  public void stop() {
  }
}
