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
package org.eclipse.scout.testing.client.runner;

import java.util.Collections;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.testing.shared.TestingUtility;
import org.eclipse.scout.rt.testing.shared.services.common.code.TestingCodeService;
import org.eclipse.scout.service.AbstractService;
import org.eclipsescout.demo.minifigcreator.client.Activator;
import org.eclipsescout.demo.minifigcreator.client.ClientSession;
import org.eclipsescout.demo.minifigcreator.shared.services.process.DesktopFormData;
import org.eclipsescout.demo.minifigcreator.shared.services.process.IDesktopProcessService;

public class CustomClientTestEnvironment implements IClientTestEnvironment {

  @Override
  public void setupGlobalEnvironment() {
    //Register a service for code types:
    TestingUtility.registerServices(Activator.getDefault().getBundle(), 500, new TestingCodeService(Collections.<ICodeType<?, ?>> emptyList()), new P_DefaultDesktopProcessService());

    //Set client session
    ScoutClientTestRunner.setDefaultClientSessionClass(ClientSession.class);
  }

  @Override
  public void setupInstanceEnvironment() {
  }

  /**
   * Default service, in order to be able to open the desktop.
   */
  public static class P_DefaultDesktopProcessService extends AbstractService implements IDesktopProcessService {
    @Override
    public DesktopFormData load(DesktopFormData formData) throws ProcessingException {
      return formData;
    }

    @Override
    public DesktopFormData store(DesktopFormData formData) throws ProcessingException {
      return formData;
    }
  }

}
