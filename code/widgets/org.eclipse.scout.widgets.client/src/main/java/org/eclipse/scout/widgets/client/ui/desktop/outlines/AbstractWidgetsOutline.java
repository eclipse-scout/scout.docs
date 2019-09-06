/*
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.desktop.outlines;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.OfficialVersion;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.GitBranchConfigProperty;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.GitFolderConfigProperty;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.GitUrlConfigProperty;

public abstract class AbstractWidgetsOutline extends AbstractOutline implements IWidgetsOutline {

  @Override
  public String getDescription() {
    return TEXTS.get("AppDescription",
        getTitle(),
        getDocumentationUrl(),
        getGitHubUrl());
  }

  protected String getGitHubUrl() {
    StringBuilder sb = new StringBuilder();
    sb.append(CONFIG.getPropertyValue(GitUrlConfigProperty.class)).append("/");
    sb.append(CONFIG.getPropertyValue(GitBranchConfigProperty.class)).append("/");
    sb.append(CONFIG.getPropertyValue(GitFolderConfigProperty.class)).append("/");
    return sb.toString();
  }

  protected String getDocumentationUrl() {
    String[] version = OfficialVersion.VERSION.split("\\."); // cut of patch number
    StringBuilder sb = new StringBuilder();
    sb.append("https://eclipsescout.github.io/");
    sb.append(version[0]).append(".").append(version[1]).append("/");
    return sb.toString();
  }

}
