package org.eclipsescout.contacts.server;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.IServerJobFactory;
import org.eclipse.scout.rt.server.IServerJobService;
import org.eclipse.scout.rt.server.ITransactionRunnable;
import org.eclipse.scout.rt.server.ServerJob;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.shared.services.IServerStartupService;

/**
 * Dummy application in order to manage server side product configurations in *.product files.
 * A typical config.ini for such a product has (among others) the following properties:
 * osgi.clean=true
 * osgi.console=
 * eclipse.consoleLog=true
 * org.eclipse.equinox.http.jetty.http.port=8080
 * org.eclipse.equinox.http.jetty.context.path=/contacts_server
 * osgi.bundles=org.eclipse.equinox.common@2:start, org.eclipse.update.configurator@start,
 * org.eclipse.equinox.http.jetty@start, org.eclipse.equinox.http.registry@start, org.eclipse.core.runtime@start
 * osgi.bundles.defaultStartLevel=4
 * osgi.noShutdown=true
 * eclipse.ignoreApp=false
 * eclipse.product=org.eclipsescout.contacts.server.product
 */
public class ServerApplication implements IApplication {
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(ServerApplication.class);

  // tag::ModularScoutApps.startServerApplication[]
  @Override
  public Object start(IApplicationContext context) throws Exception {
    final IServerJobFactory factory = SERVICES.getService(IServerJobService.class).createJobFactory();

    ServerJob installJob = factory.create("Install contacts DB schema", new ITransactionRunnable() {

      @Override
      public IStatus run(IProgressMonitor monitor) {
        try {
          for (IServerStartupService service : SERVICES.getServices(IServerStartupService.class)) {
            service.init();
          }

          LOG.info("Contacts DB schema successfully created");
        }
        catch (Throwable t) {
          return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error installing schema", t);
        }

        return Status.OK_STATUS;
      }
    });

    installJob.schedule();
    installJob.join(20000);

    LOG.info("Contacts server application started");
    return EXIT_OK;
  }
  // end::ModularScoutApps.startServerApplication[]

  @Override
  public void stop() {
  }
}
