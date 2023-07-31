/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.desktop.pages;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.SwitchJsForm;

@ClassId("dfb05c7f-fbcc-4fe4-9cce-fed9a8cec2d6")
public class SwitchJsNodePage extends AbstractPageWithNodes {

  @Override
  protected String getConfiguredTitle() {
    return "Switch";
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected Class<? extends IForm> getConfiguredDetailForm() {
    return SwitchJsForm.class;
  }

  @Order(10)
  @ClassId("21c2a0c4-ebee-415e-8fe2-88e7e3acb5ee")
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected Class<?> provideSourceClass() {
      return SwitchJsNodePage.class;
    }
  }
}
