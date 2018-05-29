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

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.PlatformException;
import org.eclipse.scout.rt.platform.inventory.ClassInventory;
import org.eclipse.scout.rt.platform.inventory.IClassInfo;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.ui.desktop.pages.FormPage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.IAdvancedFormPage;
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

  @Override
  protected String getConfiguredIconId() {
    return null;
  }

  /**
   * Adds all example forms that implement IAdvancedExampleForm to this page. From each form a FormPage is created and
   * added to the page list. The form pages are sorted alphabetically.
   */
  @Override
  @SuppressWarnings("unchecked")
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    ClassInventory.get()
        .getAllKnownSubClasses(IAdvancedFormPage.class).stream()
        .map(this::classInfoToPage)
        .forEach(pageList::add);

    // assemble a sorted list of all classes implementing IAdvancedExampleForm
    ClassInventory.get()
        .getAllKnownSubClasses(IAdvancedExampleForm.class).stream()
        .map(ci -> (Class<IPageForm>) ci.resolveClass())
        .map(FormPage::new)
        .forEach(pageList::add);

    FormPage.sort(pageList);
  }

  protected IPage<?> classInfoToPage(IClassInfo classInfo) {
    try {
      return (IPage<?>) classInfo.resolveClass().newInstance();
    }
    catch (ReflectiveOperationException e) {
      throw new PlatformException("Error creating page for classinfo {}.", classInfo, e);
    }
  }
}
