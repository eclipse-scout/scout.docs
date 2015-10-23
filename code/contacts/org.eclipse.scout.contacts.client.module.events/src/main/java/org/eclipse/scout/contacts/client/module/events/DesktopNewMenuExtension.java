/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.client.module.events;

import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.contacts.client.Desktop;
import org.eclipse.scout.contacts.client.Desktop.FileMenu.NewMenu;
import org.eclipse.scout.contacts.client.module.events.event.EventForm;
import org.eclipse.scout.rt.client.extension.ui.action.menu.AbstractMenuExtension;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.shared.TEXTS;

public class DesktopNewMenuExtension extends AbstractMenuExtension<Desktop.FileMenu.NewMenu> {

  public DesktopNewMenuExtension(NewMenu owner) {
    super(owner);
  }

  @Order(3000.0)
  public class EventMenu extends AbstractMenu {

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet();
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Event");
    }

    @Override
    protected void execAction() {
      new EventForm().startNew();
    }
  }
}
