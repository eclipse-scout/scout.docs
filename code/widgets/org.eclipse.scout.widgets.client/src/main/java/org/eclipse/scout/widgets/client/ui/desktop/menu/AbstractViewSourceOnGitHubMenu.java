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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.widgets.client.ClientSession;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.GitBranchConfigProperty;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.GitFolderConfigProperty;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.GitSourceConfigProperty;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.GitUrlConfigProperty;

public abstract class AbstractViewSourceOnGitHubMenu extends AbstractMenu {

  protected static final Pattern MODULE_PATTERN = Pattern.compile("(org\\.eclipse\\.scout\\.widgets\\..*?client)\\..*");

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("ViewSourceOnGitHub");
  }

  @Override
  protected void execInitAction() {
    setVisibleGranted(calculateSourceModule() != null);
  }

  @Override
  protected void execAction() {
    String sourceModule = calculateSourceModule();
    String canonicalName = provideSourceClass().getCanonicalName();
    StringBuilder sb = new StringBuilder();
    sb.append(CONFIG.getPropertyValue(GitUrlConfigProperty.class)).append("/");
    sb.append(CONFIG.getPropertyValue(GitBranchConfigProperty.class)).append("/");
    sb.append(CONFIG.getPropertyValue(GitFolderConfigProperty.class)).append("/");
    sb.append(sourceModule).append("/");
    sb.append(CONFIG.getPropertyValue(GitSourceConfigProperty.class)).append("/");
    sb.append(canonicalName.replace(".", "/"));
    sb.append(".java");
    ClientSession.get().getDesktop().openUri(sb.toString(), OpenUriAction.NEW_WINDOW);
  }

  protected abstract Class<?> provideSourceClass();

  protected String calculateSourceModule() {
    String canonicalName = ObjectUtility.nvl(provideSourceClass().getCanonicalName(), "");
    Matcher m = MODULE_PATTERN.matcher(canonicalName);
    return (m.matches() ? m.group(1) : null);
  }
}
