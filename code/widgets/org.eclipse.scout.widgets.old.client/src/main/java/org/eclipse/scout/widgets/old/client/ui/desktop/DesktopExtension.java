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
package org.eclipse.scout.widgets.old.client.ui.desktop;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.AbstractFormMenu;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.old.client.ui.desktop.outlines.PagesSearchFormsOutline;
import org.eclipse.scout.widgets.old.client.ui.desktop.outlines.WidgetsOutline;
import org.eclipse.scout.widgets.old.client.ui.forms.Menu1Form;
import org.eclipse.scout.widgets.old.client.ui.forms.Menu2Form;
import org.eclipse.scout.widgets.shared.Icons;

public class DesktopExtension extends AbstractDesktopExtension {
  public DesktopExtension() {
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    List<Class<? extends IOutline>> outlines = new ArrayList<Class<? extends IOutline>>();
    outlines.add(WidgetsOutline.class);
    outlines.add(PagesSearchFormsOutline.class);
    return outlines;
  }

  @Order(40)
  public class WidgetsOutlineViewButton extends AbstractOutlineViewButton {
    public WidgetsOutlineViewButton() {
      super(getCoreDesktop(), WidgetsOutline.class);
    }
  }

  @Order(50)
  public class PagesSearchFormsOutlineViewButton extends AbstractOutlineViewButton {
    public PagesSearchFormsOutlineViewButton() {
      super(getCoreDesktop(), PagesSearchFormsOutline.class);
    }
  }

  @Order(1)
  public class Menu1 extends AbstractFormMenu<Menu1Form> {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Menu1");
    }

    @Override
    protected String getConfiguredIconId() {
      return Icons.Star;
    }

    @Override
    protected Class<Menu1Form> getConfiguredForm() {
      return Menu1Form.class;
    }
  }

  @Order(2)
  public class Menu2 extends AbstractFormMenu<Menu2Form> {

    @Override
    protected String getConfiguredIconId() {
      return Icons.Gear;
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Menu2");
    }

    @Override
    protected Class<Menu2Form> getConfiguredForm() {
      return Menu2Form.class;
    }
  }
}
