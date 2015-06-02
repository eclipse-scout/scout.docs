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
package org.eclipse.scout.testing.server.runner;

import org.eclipse.scout.rt.testing.server.runner.IServerTestEnvironment;
import org.eclipse.scout.rt.testing.server.runner.ScoutServerTestRunner;
import org.eclipsescout.demo.minifigcreator.server.ServerSession;

public class CustomServerTestEnvironment implements IServerTestEnvironment {

  @Override
  public void setupGlobalEnvironment() {
    ScoutServerTestRunner.setDefaultServerSessionClass(ServerSession.class);
    ScoutServerTestRunner.setDefaultPrincipalName("test_default");
  }

  @Override
  public void setupInstanceEnvironment() {
  }
}
