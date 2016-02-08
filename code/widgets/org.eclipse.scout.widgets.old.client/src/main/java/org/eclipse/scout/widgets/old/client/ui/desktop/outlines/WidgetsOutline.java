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
package org.eclipse.scout.widgets.old.client.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.old.client.FormFieldsNodePage;
import org.eclipse.scout.widgets.old.client.MenusNodePage;

/**
 * @author mzi
 */
@Order(1300.0)
public class WidgetsOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Widgets");
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    FormFieldsNodePage formFieldsNodePage = new FormFieldsNodePage();
    pageList.add(formFieldsNodePage);
    MenusNodePage menusNodePage = new MenusNodePage();
    pageList.add(menusNodePage);
  }
}
