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
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.ServerJob;
import org.eclipse.scout.rt.server.services.common.session.IServerSessionRegistryService;
import org.eclipse.scout.service.SERVICES;

public class ServerApplication implements IApplication {
  private static IScoutLogger logger = ScoutLogManager.getLogger(ServerApplication.class);

  @Override
  public Object start(IApplicationContext context) throws Exception {
    //start the scheduler
    /*
    Scheduler scheduler=new Scheduler(Activator.getDefault().getBackendSubject(),ServerSession.class);
    scheduler.addJob(new LoadJobs());
    scheduler.start();
    Activator.getDefault().setScheduler(scheduler);
    */

    // create session using server principal
    ServerSession serverSession = SERVICES.getService(IServerSessionRegistryService.class).newServerSession(ServerSession.class, Activator.getDefault().getBackendSubject());

    // start a job that installs the database and creates all tables (if they do not exist already).
    ServerJob dbInstallJob = new ServerJob("Install Db schema if necessary", serverSession) {
      @Override
      protected IStatus runTransaction(IProgressMonitor monitor) {
        try {
          DbSetup.installDb();
        }
        catch (Throwable t) {
          return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error while installing the bahbah server Db schema", t);
        }
        return Status.OK_STATUS;
      }
    };
    dbInstallJob.schedule();
    dbInstallJob.join(20000);

    logger.info("bahbah server initialized");

    return EXIT_OK;
  }

  @Override
  public void stop() {

  }
}
