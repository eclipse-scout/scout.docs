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
package org.eclipse.scout.contacts.client.module.events;

import org.eclipse.scout.contacts.client.module.events.event.EventOutlineExtension;
import org.eclipse.scout.contacts.client.module.events.event.EventPageExtension;
import org.eclipse.scout.contacts.client.module.events.person.PersonFormTabExtension;
import org.eclipse.scout.contacts.client.module.events.person.PersonTablePageExtension;
import org.eclipse.scout.contacts.shared.module.events.person.PersonFormTabExtensionData;
import org.eclipse.scout.contacts.shared.module.events.person.PersonTablePageDataExtension;
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
