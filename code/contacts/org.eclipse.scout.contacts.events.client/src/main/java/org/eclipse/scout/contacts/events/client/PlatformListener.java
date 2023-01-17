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

import org.eclipse.scout.contacts.events.client.event.EventOutlineExtension;
import org.eclipse.scout.contacts.events.client.event.EventPageExtension;
import org.eclipse.scout.contacts.events.client.person.PersonFormTabExtension;
import org.eclipse.scout.contacts.events.client.person.PersonTablePageExtension;
import org.eclipse.scout.contacts.events.shared.person.PersonFormTabExtensionData;
import org.eclipse.scout.contacts.events.shared.person.PersonTablePageDataExtension;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.shared.extension.IExtensionRegistry;

public class PlatformListener implements IPlatformListener {

  @Override
  public void stateChanged(PlatformEvent event) {
    if (event.getState() == State.BeanManagerValid) {
      registerExtensions();
    }
  }

  private void registerExtensions() {
    IExtensionRegistry extensionRegistry = BEANS.get(IExtensionRegistry.class);

    // Register UI extensions
    extensionRegistry.register(DesktopQuickAccessMenuExtension.class);
    extensionRegistry.register(PersonFormTabExtension.class);
    extensionRegistry.register(PersonTablePageExtension.class);
    extensionRegistry.register(EventOutlineExtension.class);
    extensionRegistry.register(EventPageExtension.class);

    // Register DTO extensions
    extensionRegistry.register(PersonFormTabExtensionData.class);
    extensionRegistry.register(PersonTablePageDataExtension.class);
  }
}
