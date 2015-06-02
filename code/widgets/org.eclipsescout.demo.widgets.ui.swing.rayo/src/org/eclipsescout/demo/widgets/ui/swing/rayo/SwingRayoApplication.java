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
package org.eclipsescout.demo.widgets.ui.swing.rayo;

import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.services.common.session.IClientSessionRegistryService;
import org.eclipse.scout.rt.shared.ui.UserAgent;
import org.eclipse.scout.rt.ui.swing.AbstractSwingApplicationExtension;
import org.eclipse.scout.rt.ui.swing.ISwingEnvironment;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.widgets.client.ClientSession;

public class SwingRayoApplication extends AbstractSwingApplicationExtension {

  public SwingRayoApplication() {
    super("SwingRayoApplication");
  }

  /**
   * @param extensionId
   */
  public SwingRayoApplication(String extensionId) {
    super(extensionId);
  }

  @Override
  protected IClientSession createClientSession(UserAgent userAgent) {
    IClientSessionRegistryService service = SERVICES.getService(IClientSessionRegistryService.class);
    return service.newClientSession(ClientSession.class, userAgent);
  }

  @Override
  protected ISwingEnvironment createEnvironment() {
    return new SwingEnvironment();
  }
}
