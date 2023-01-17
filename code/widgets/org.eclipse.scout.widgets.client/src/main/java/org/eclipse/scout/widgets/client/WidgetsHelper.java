/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.collection.OrderedCollection;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.ReadOnlyProperty;

@ApplicationScoped
public class WidgetsHelper {

  public void injectReadOnlyMenu(OrderedCollection<IMenu> menus) {
    if (CONFIG.getPropertyValue(ReadOnlyProperty.class)) {
      menus.addLast(new ReadOnlyMenu());
    }
  }

  @Order(50)
  @ClassId("8434d15b-ff8e-4e34-8767-a766861477c1")
  public class ReadOnlyMenu extends AbstractMenu {
    @Override
    protected byte getConfiguredHorizontalAlignment() {
      return HORIZONTAL_ALIGNMENT_RIGHT;
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ReadOnlyMenuLabel");
    }

    @Override
    protected String getConfiguredTooltipText() {
      return TEXTS.get("ReadOnlyMenuTooltip");
    }

    @Override
    protected String getConfiguredCssClass() {
      return "read-only-info";
    }
  }

}
