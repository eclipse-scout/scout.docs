package org.eclipsescout.contacts.client;

import java.net.URL;

import org.eclipse.scout.commons.UriUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.rt.client.servicetunnel.http.ClientHttpServiceTunnel;
import org.eclipse.scout.rt.shared.services.common.code.CODES;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.client.services.IClientStartupService;
import org.eclipsescout.contacts.client.ui.desktop.Desktop;

public class ClientSession extends AbstractClientSession {

  public ClientSession() {
    super(true);
  }

  public static ClientSession get() {
    return ClientJob.getCurrentSession(ClientSession.class);
  }

  // tag::ModularScoutApps.execLoadSession[]
  @Override
  public void execLoadSession() throws ProcessingException {
    URL url = UriUtility.toUrl(getBundle().getBundleContext().getProperty("server.url"));
    setServiceTunnel(new ClientHttpServiceTunnel(this, url));

    // Pre-load all known code types
    CODES.getAllCodeTypes(org.eclipsescout.contacts.shared.Activator.PLUGIN_ID);

    // Call all startup services to collect all available extensions
    for (IClientStartupService service : SERVICES.getServices(IClientStartupService.class)) {
      service.init();
    }

    setDesktop(new Desktop()); // <1>
  }
  // end::ModularScoutApps.execLoadSession[]

  @Override
  public void execStoreSession() throws ProcessingException {
  }
}
