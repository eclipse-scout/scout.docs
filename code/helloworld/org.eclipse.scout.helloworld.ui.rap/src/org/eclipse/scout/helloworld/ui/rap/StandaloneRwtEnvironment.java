package org.eclipse.scout.helloworld.ui.rap;

import org.eclipse.scout.helloworld.client.ClientSession;
import org.eclipse.scout.rt.ui.rap.AbstractStandaloneRwtEnvironment;

public class StandaloneRwtEnvironment extends AbstractStandaloneRwtEnvironment {

  public StandaloneRwtEnvironment() {
    super(Activator.getDefault().getBundle(), ClientSession.class);
  }
}
