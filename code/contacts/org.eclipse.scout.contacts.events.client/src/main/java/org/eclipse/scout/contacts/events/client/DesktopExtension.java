/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.events.client;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.contacts.events.client.event.EventOutline;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;

public class DesktopExtension extends AbstractDesktopExtension {

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    List<Class<? extends IOutline>> outlines = new ArrayList<>();
    outlines.add(EventOutline.class);
    return outlines;
  }

  @Order(2000)
  @ClassId("aa6e65d3-55e7-4a47-aad6-c5a4771d2c70")
  public class EventOutlineViewButton extends AbstractOutlineViewButton {

    public EventOutlineViewButton() {
      super(getCoreDesktop(), EventOutline.class);
    }

    @Override
    protected DisplayStyle getConfiguredDisplayStyle() {
      return DisplayStyle.MENU;
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return "ctrl-shift-e";
    }
  }
}
