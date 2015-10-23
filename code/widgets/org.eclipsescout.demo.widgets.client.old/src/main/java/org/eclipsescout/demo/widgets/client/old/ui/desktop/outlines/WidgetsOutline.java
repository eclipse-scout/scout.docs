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
package org.eclipsescout.demo.widgets.client.old.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.old.FormFieldsNodePage;
import org.eclipsescout.demo.widgets.client.old.MenusNodePage;

/**
 * @author mzi
 */
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
