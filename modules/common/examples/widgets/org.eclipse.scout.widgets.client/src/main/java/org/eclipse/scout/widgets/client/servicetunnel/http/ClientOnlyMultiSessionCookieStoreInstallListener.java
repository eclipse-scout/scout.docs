package org.eclipse.scout.widgets.client.servicetunnel.http;

import org.eclipse.scout.rt.client.servicetunnel.http.MultiSessionCookieStoreInstallListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.Replace;

@Replace
public class ClientOnlyMultiSessionCookieStoreInstallListener extends MultiSessionCookieStoreInstallListener {

  @Override
  public void stateChanged(PlatformEvent event) {
    // NOP - Don't install MultiSessionCookieStore in client-only mode!
  }
}
