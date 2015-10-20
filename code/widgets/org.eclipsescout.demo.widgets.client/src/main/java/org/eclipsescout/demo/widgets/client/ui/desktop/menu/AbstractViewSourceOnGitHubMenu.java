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

import org.eclipse.scout.commons.ConfigUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ClientSession;

public abstract class AbstractViewSourceOnGitHubMenu extends AbstractMenu {

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("ViewSourceOnGitHub");
  }

  @Override
  protected void execAction() throws ProcessingException {
    String canonicalName = provideSourceClass().getCanonicalName();
    StringBuilder sb = new StringBuilder();
    sb.append("https://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo/tree/");
    sb.append(ConfigUtility.getProperty("git.branch"));
    sb.append("/widgets/");
    if (canonicalName.contains("widgets.client.old")) {
      sb.append("org.eclipsescout.demo.widgets.client.old");
    }
    else {
      sb.append("org.eclipsescout.demo.widgets.client");
    }
    sb.append("/src/");
    sb.append(canonicalName.replace(".", "/"));
    sb.append(".java");
    ClientSession.get().getDesktop().openUri(sb.toString(), null);
  }

  abstract protected Class<?> provideSourceClass();
}
