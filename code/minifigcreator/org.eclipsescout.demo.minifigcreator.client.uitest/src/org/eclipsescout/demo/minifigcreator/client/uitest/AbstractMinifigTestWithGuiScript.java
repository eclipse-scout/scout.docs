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
package org.eclipsescout.demo.minifigcreator.client.uitest;

import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.testing.client.AbstractTestWithGuiScript;
import org.eclipsescout.demo.minifigcreator.client.ClientSession;

/**
 * Abstract class for all tests with GUI test
 */
public abstract class AbstractMinifigTestWithGuiScript extends AbstractTestWithGuiScript {

  @Override
  protected Class<? extends IClientSession> getSessionClass() {
    return ClientSession.class;
  }

}
