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
package org.eclipsescout.demo.widgets.client.custom.ui.desktop;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.platform.Order;
import org.eclipsescout.demo.widgets.client.custom.ui.desktop.outlines.CustomWidgetsOutline;

public class DesktopExtension extends AbstractDesktopExtension {
  public DesktopExtension() {
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    List<Class<? extends IOutline>> outlines = new ArrayList<Class<? extends IOutline>>();
    outlines.add(CustomWidgetsOutline.class);
    return outlines;
  }

  @Order(40)
  public class CustomWidgetsOutlineViewButton extends AbstractOutlineViewButton {
    public CustomWidgetsOutlineViewButton() {
      super(getCoreDesktop(), CustomWidgetsOutline.class);
    }
  }
}
