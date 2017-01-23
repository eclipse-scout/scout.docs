package org.eclipse.scout.widgets.ui.html;

import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.ui.html.MultiSessionCookieStoreInstallListener;

@Replace
public class ClientOnlyMultiSessionCookieStoreInstallListener extends MultiSessionCookieStoreInstallListener {

  @Override
  public void stateChanged(PlatformEvent event) {
    // NOP - Don't install MultiSessionCookieStore in client-only mode!
  }
}
