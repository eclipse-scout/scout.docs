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
package org.eclipsescout.demo.widgets.client.ui.template.menu;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.shell.IShellService;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.widgets.client.ClientSession;

public abstract class AbstractViewSourceOnGitHubMenu extends AbstractMenu {

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("ViewSourceOnGitHub");
  }

  @Override
  protected void execAction() throws ProcessingException {
    String linkaddress = "https://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo/tree/" +
        ClientSession.get().getBundle().getBundleContext().getProperty("git.branch") +
        "/widgets/org.eclipsescout.demo.widgets.client/src/" +
        provideSourceClass().getCanonicalName().replace(".", "/") + ".java";

    SERVICES.getService(IShellService.class).shellOpen(linkaddress);
  }

  abstract protected Class<?> provideSourceClass();
}
