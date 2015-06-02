package org.eclipse.scout.helloworld.ui.rap;

import org.eclipse.scout.helloworld.client.ClientSession;
import org.eclipse.scout.rt.ui.rap.mobile.AbstractTabletStandaloneRwtEnvironment;

public class TabletStandaloneRwtEnvironment extends AbstractTabletStandaloneRwtEnvironment {

  public TabletStandaloneRwtEnvironment() {
    super(Activator.getDefault().getBundle(), ClientSession.class);
  }
}
