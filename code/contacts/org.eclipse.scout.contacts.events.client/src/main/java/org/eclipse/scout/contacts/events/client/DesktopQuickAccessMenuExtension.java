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

import java.util.Set;

import org.eclipse.scout.contacts.client.Desktop.QuickAccessMenu;
import org.eclipse.scout.contacts.events.client.event.EventForm;
import org.eclipse.scout.rt.client.extension.ui.action.menu.AbstractMenuExtension;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

public class DesktopQuickAccessMenuExtension extends AbstractMenuExtension<QuickAccessMenu> {

  public DesktopQuickAccessMenuExtension(QuickAccessMenu owner) {
    super(owner);
  }

  @Order(30)
  @ClassId("42bde310-57cf-4873-9824-6d56c02a55a9")
  public class NewEventMenu extends AbstractMenu {

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet();
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("CreateNewEvent");
    }

    @Override
    protected void execAction() {
      new EventForm().startNew();
    }
  }
}
