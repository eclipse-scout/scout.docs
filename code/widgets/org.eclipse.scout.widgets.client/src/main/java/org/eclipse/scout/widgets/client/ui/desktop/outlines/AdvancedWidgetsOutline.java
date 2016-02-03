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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.inventory.ClassInventory;
import org.eclipse.scout.rt.platform.inventory.IClassInfo;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ui.desktop.pages.FormPage;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;

/**
 * @author mzi
 */
@Order(1100.0)
public class AdvancedWidgetsOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("AdvancedWidgets");
  }

  /**
   * Adds all example forms that implement IAdvancedExampleForm to this page. From each form a FormPage is created and
   * added to the page list. The form pages are sorted according to their Order annotation.
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    // assemble a sorted list of all classes implementing IAdvancedExampleForm
    List<IClassInfo> forms = new ArrayList<>(ClassInventory.get().getAllKnownSubClasses(IAdvancedExampleForm.class));
    Collections.sort(forms, new Comparator<IClassInfo>() {

      @Override
      public int compare(IClassInfo arg1, IClassInfo arg2) {
        double o1 = -1;
        double o2 = -1;

        if (arg1 != null) {
          Class<IPageForm> c = (Class<IPageForm>) arg1.resolveClass();
          if (c.getAnnotation(Order.class) != null) {
            o1 = c.getAnnotation(Order.class).value();
          }
        }

        if (arg2 != null) {
          Class<IPageForm> c = (Class<IPageForm>) arg2.resolveClass();
          if (c.getAnnotation(Order.class) != null) {
            o2 = c.getAnnotation(Order.class).value();
          }
        }

        return (int) Math.signum(o1 - o2);
      }

    });

    for (IClassInfo classInfo : forms) {
      Class<IPageForm> pageForm = (Class<IPageForm>) classInfo.resolveClass();
      FormPage page = new FormPage(pageForm);
      pageList.add(page);
    }
  }
}
