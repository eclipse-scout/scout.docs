/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.desktop.outlines;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.config.PlatformConfigProperties.PlatformVersionProperty;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.GitBranchConfigProperty;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.GitFolderConfigProperty;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.GitUrlConfigProperty;

@ClassId("b13d5716-6ec9-4121-bd2f-60665d85731e")
public abstract class AbstractWidgetsOutline extends AbstractOutline implements IWidgetsOutline {

  @Override
  public String getDescription() {
    return TEXTS.get("AppDescription",
        getTitle(),
        getDocumentationUrl(),
        getGitHubUrl());
  }

  protected String getGitHubUrl() {
    return CONFIG.getPropertyValue(GitUrlConfigProperty.class) + "/"
        + CONFIG.getPropertyValue(GitBranchConfigProperty.class) + "/"
        + CONFIG.getPropertyValue(GitFolderConfigProperty.class) + "/";
  }

  protected String getDocumentationUrl() {
    String url = "https://eclipsescout.github.io/";
    String version = CONFIG.getPropertyValue(PlatformVersionProperty.class);
    if (version.contains(".")) {
      String[] versionParts = version.split("\\."); // cut of patch number
      url += versionParts[0] + "." + versionParts[1] + "/";
    }
    return url;
  }
}
