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
package org.eclipsescout.demo.widgets.client.ui.desktop.menu;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.shell.IShellService;
import org.eclipse.scout.service.SERVICES;

public abstract class AbstractViewSourceOnGitHubMenu extends AbstractMenu {

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("ViewSourceOnGitHub");
  }

  @Override
  protected void execAction() throws ProcessingException {
    String canonicalName = provideSourceClass().getCanonicalName();

    StringBuilder sb = new StringBuilder();
    sb.append(String.format("%s/%s/%s/", TEXTS.get("git.repo"), TEXTS.get("git.branch"), TEXTS.get("git.path")));

    if (canonicalName.contains("widgets.client.old")) {
      sb.append("org.eclipsescout.demo.widgets.client.old");
    }
    else {
      sb.append("org.eclipsescout.demo.widgets.client");
    }

    sb.append(String.format("/src/%s.java", canonicalName.replace(".", "/")));

    SERVICES.getService(IShellService.class).shellOpen(sb.toString());
  }

  abstract protected Class<?> provideSourceClass();
}
