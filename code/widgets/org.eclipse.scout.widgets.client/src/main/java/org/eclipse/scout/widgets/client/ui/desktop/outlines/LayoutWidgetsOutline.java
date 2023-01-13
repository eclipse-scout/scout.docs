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

import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.widgets.client.ui.desktop.pages.FormPage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.bench.DesktopBenchLayoutPage;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxDynamicFieldsForm;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxForm;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm;
import org.eclipse.scout.widgets.client.ui.forms.WrappedFormFieldForm;
import org.eclipse.scout.widgets.shared.Icons;

@Order(1200)
@ClassId("98487649-864e-4295-b11e-ccbdf7cebc54")
public class LayoutWidgetsOutline extends AbstractWidgetsOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("LayoutWidgets");
  }

  @Override
  protected String getConfiguredIconId() {
    return null;
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    pageList.add(new FormPage(GroupBoxForm.class));
    pageList.add(new FormPage(GroupBoxHorizontalScrollingForm.class));
    pageList.add(new FormPage(GroupBoxDynamicFieldsForm.class));
    pageList.add(new FormPage(SequenceBoxForm.class));
    pageList.add(new FormPage(TabBoxForm.class));
    pageList.add(new FormPage(SplitBoxForm.class));
    pageList.add(new FormPage(WrappedFormFieldForm.class));
    pageList.add(new DesktopBenchLayoutPage());
    FormPage.sort(pageList);
  }

  @Order(1000)
  @ClassId("a3390108-f178-453d-96a9-ef8934b165ce")
  public class LayoutOutlineMenu extends AbstractMenu {

    @Override
    protected String getConfiguredIconId() {
      return Icons.Gear;
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.hashSet(TreeMenuType.Header);
    }

    @Override
    protected void execAction() {
      MessageBoxes.createOk().withBody(TEXTS.get("OutlineMenuDescription")).show();
    }
  }

}
