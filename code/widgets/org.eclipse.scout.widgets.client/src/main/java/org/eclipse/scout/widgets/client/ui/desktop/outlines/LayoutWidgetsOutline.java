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
package org.eclipse.scout.widgets.client.ui.desktop.outlines;

import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
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

/**
 * @author mzi
 */
@Order(1200.0)
public class LayoutWidgetsOutline extends AbstractOutline {

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
