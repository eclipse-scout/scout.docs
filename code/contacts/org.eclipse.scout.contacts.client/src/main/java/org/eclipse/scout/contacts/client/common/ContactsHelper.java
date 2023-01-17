/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.client.common;

import org.eclipse.scout.contacts.client.ConfigProperties.ReadOnlyProperty;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.IButton;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.collection.OrderedCollection;

@ApplicationScoped
public class ContactsHelper {

  public void handleReadOnly(AbstractOkButton okButton) {
    if (CONFIG.getPropertyValue(ReadOnlyProperty.class)) {
      okButton.setLabel(TEXTS.get("CloseButton"));
      okButton.setSystemType(IButton.SYSTEM_TYPE_CLOSE);
    }
  }

  public void injectReadOnlyMenu(OrderedCollection<IMenu> menus) {
    if (CONFIG.getPropertyValue(ReadOnlyProperty.class)) {
      menus.addLast(new ReadOnlyMenu());
    }
  }

  @Order(50)
  @ClassId("2fb75903-af19-4dd7-b980-c2d7e9726f2c")
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
