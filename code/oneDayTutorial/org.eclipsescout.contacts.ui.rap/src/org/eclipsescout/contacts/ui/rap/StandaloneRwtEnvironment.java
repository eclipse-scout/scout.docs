package org.eclipsescout.contacts.ui.rap;

import org.eclipse.scout.rt.ui.rap.AbstractStandaloneRwtEnvironment;
import org.eclipsescout.contacts.client.ClientSession;

public class StandaloneRwtEnvironment extends AbstractStandaloneRwtEnvironment {

  public StandaloneRwtEnvironment() {
    super(Activator.getDefault().getBundle(), ClientSession.class);
  }
}
