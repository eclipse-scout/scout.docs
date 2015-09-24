package org.eclipse.scout.contacts.client;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.client.desktop.Desktop;
import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.shared.services.common.code.CODES;

public class ClientSession extends AbstractClientSession {

  public ClientSession() {
    super(true);
  }

  /**
   * @return The {@link IClientSession} which is associated with the current thread, or <code>null</code> if not found.
   */
  public static ClientSession get() {
    return ClientSessionProvider.currentSession(ClientSession.class);
  }

  @Override
  protected void execLoadSession() throws ProcessingException {

    //pre-load all known code types
    CODES.getAllCodeTypes("org.eclipse.scout.contacts.shared");

    setDesktop(new Desktop());
  }

  @Override
  protected void execStoreSession() throws ProcessingException {
  }
}
