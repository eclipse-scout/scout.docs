/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.PlatformException;
import org.eclipse.scout.rt.platform.inventory.ClassInventory;
import org.eclipse.scout.rt.platform.inventory.IClassInfo;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.ui.desktop.pages.FormPage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.IAdvancedFormPage;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.client.ui.forms.toggleswitch.ToggleSwitchForm;

@Order(1100)
@ClassId("2218fc69-7297-4478-8c1b-a18bea3191fd")
public class AdvancedWidgetsOutline extends AbstractWidgetsOutline {

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

    pageList.add(new FormPage(ToggleSwitchForm.class, "Switch"));

    FormPage.sort(pageList);
  }

  protected IPage<?> classInfoToPage(IClassInfo classInfo) {
    try {
      return (IPage<?>) classInfo.resolveClass().getConstructor().newInstance();
    }
    catch (ReflectiveOperationException e) {
      throw new PlatformException("Error creating page for classinfo {}.", classInfo, e);
    }
  }
}
