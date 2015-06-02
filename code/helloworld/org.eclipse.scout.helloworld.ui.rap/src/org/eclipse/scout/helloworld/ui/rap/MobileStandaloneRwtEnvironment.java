package org.eclipse.scout.helloworld.ui.rap;

import org.eclipse.scout.helloworld.client.ClientSession;
import org.eclipse.scout.rt.ui.rap.mobile.AbstractMobileStandaloneRwtEnvironment;

public class MobileStandaloneRwtEnvironment extends AbstractMobileStandaloneRwtEnvironment {

  public MobileStandaloneRwtEnvironment() {
    super(Activator.getDefault().getBundle(), ClientSession.class);
  }
}
