/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.desktop.pages;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.HybridJsForm;
import org.eclipse.scout.widgets.client.ui.forms.hybrid.HybridJsFormModelDo;

@ClassId("c7320996-3854-46ef-8177-9f179f48cb0a")
public class HybridNodePage extends AbstractPageWithNodes {

  @Override
  protected String getConfiguredTitle() {
    return "Hybrid Node Page";
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected Class<? extends IForm> getConfiguredDetailForm() {
    return HybridJsForm.class;
  }

  @Override
  protected void decorateDetailForm() {
    super.decorateDetailForm();
    ((HybridJsForm) getDetailForm()).setJsFormModel(BEANS.get(HybridJsFormModelDo.class)
        .withPageTitle(getCell().getText()));
  }

  @Order(10)
  @ClassId("747f8fc2-ea3f-4c7a-8836-7a2246a213f7")
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected Class<?> provideSourceClass() {
      return HybridNodePage.class;
    }
  }
}
