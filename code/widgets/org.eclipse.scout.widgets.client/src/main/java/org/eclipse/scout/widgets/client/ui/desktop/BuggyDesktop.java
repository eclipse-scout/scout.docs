/*
 * Copyright (c) 2023 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.desktop;

import java.util.List;

import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.widgets.client.bug.settings.SettingsOutline;
import org.eclipse.scout.widgets.client.bug.work.InvisibleOutline;
import org.eclipse.scout.widgets.client.bug.work.WorkOutline;

/**
 * <h3>{@link Desktop}</h3>
 *
 * @author Florian
 */
@ClassId("639622fe-6f60-48e6-8036-119ac3a8c60d")
public class BuggyDesktop extends AbstractDesktop {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ApplicationTitle");
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    return CollectionUtility.<Class<? extends IOutline>> arrayList(WorkOutline.class, SettingsOutline.class, InvisibleOutline.class);
  }

  @Override
  protected void execDefaultView() {
    selectFirstVisibleOutline();
  }

  protected void selectFirstVisibleOutline() {
    for (IOutline outline : getAvailableOutlines()) {
      if (outline.isEnabled() && outline.isVisible()) {
        setOutline(outline.getClass());
        return;
      }
    }
  }

  @Override
  protected void setOutlineInternal(IOutline newOutline) {
    IOutline old = getOutline();
    if (old instanceof InvisibleOutline) {
      List<IForm> forms = getForms(old);
      System.out.println(forms.size());
      forms.forEach(IForm::doClose);
    }
    List<IForm> work = getForms(findOutline(WorkOutline.class));
    System.out.println("work:" + work.size());
    super.setOutlineInternal(newOutline);
  }

  @Override
  protected void execOutlineChanged(IOutline oldOutline, IOutline newOutline) {
    setNavigationVisible(false);
    setNavigationHandleVisible(false);
  }

  @Override
  protected void execInit() {
    super.execInit();
    List<IOutline> availableOutlines = getAvailableOutlines();
    for (IOutline iOutline : availableOutlines) {
      if (iOutline.isVisible()) {
        activateOutline(iOutline);
        break;
      }
    }
  }

  @Order(1000)
  @ClassId("f2c257df-708b-4373-ab39-c281da5622b9")
  public class WorkOutlineViewButton extends AbstractOutlineViewButton {

    public WorkOutlineViewButton() {
      this(WorkOutline.class);
    }

    protected WorkOutlineViewButton(Class<? extends WorkOutline> outlineClass) {
      super(BuggyDesktop.this, outlineClass);
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F2;
    }

    @Override
    protected DisplayStyle getConfiguredDisplayStyle() {
      return DisplayStyle.TAB;
    }
  }

  @Order(3000)
  @ClassId("a5c1d8dc-b170-45b6-a16d-87bc7cd9a042")
  public class SettingsOutlineViewButton extends AbstractOutlineViewButton {

    public SettingsOutlineViewButton() {
      this(SettingsOutline.class);
    }

    protected SettingsOutlineViewButton(Class<? extends SettingsOutline> outlineClass) {
      super(BuggyDesktop.this, outlineClass);
    }

    @Override
    protected DisplayStyle getConfiguredDisplayStyle() {
      return DisplayStyle.TAB;
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F10;
    }
  }
}
