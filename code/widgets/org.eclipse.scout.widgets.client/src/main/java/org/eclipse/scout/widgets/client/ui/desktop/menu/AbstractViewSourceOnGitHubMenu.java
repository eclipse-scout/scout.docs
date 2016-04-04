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
package org.eclipse.scout.widgets.client.ui.desktop.menu;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.platform.config.ConfigUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ClientSession;

public abstract class AbstractViewSourceOnGitHubMenu extends AbstractMenu {

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("ViewSourceOnGitHub");
  }

  @Override
  protected void execAction() {
    String canonicalName = provideSourceClass().getCanonicalName();
    StringBuilder sb = new StringBuilder();
    sb.append(ConfigUtility.getProperty("git.url")).append("/");
    sb.append(ConfigUtility.getProperty("git.branch")).append("/");
    sb.append(ConfigUtility.getProperty("git.folder")).append("/");
    sb.append("org.eclipse.scout.widgets");

    // maven module specific part
    if (canonicalName.contains("widgets.old.client")) {
      sb.append(".old.client").append("/");
    }
    else if (canonicalName.contains("widgets.heatmap.client")) {
      sb.append(".heatmap.client").append("/");
    }
    else {
      sb.append(".client").append("/");
    }

    sb.append(ConfigUtility.getProperty("git.source")).append("/");
    sb.append(canonicalName.replace(".", "/"));
    sb.append(".java");
    ClientSession.get().getDesktop().openUri(sb.toString(), OpenUriAction.NEW_WINDOW);
  }

  abstract protected Class<?> provideSourceClass();
}
