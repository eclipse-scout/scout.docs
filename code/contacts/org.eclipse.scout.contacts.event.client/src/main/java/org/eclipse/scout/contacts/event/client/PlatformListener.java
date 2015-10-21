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
package org.eclipse.scout.contacts.event.client;

import org.eclipse.scout.contacts.event.client.person.PersonFormTabExtension;
import org.eclipse.scout.contacts.event.client.person.PersonTablePageExtension;
import org.eclipse.scout.contacts.event.shared.person.PersonFormTabExtensionData;
import org.eclipse.scout.contacts.event.shared.person.PersonTablePageDataExtension;
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
    extensionRegistry.register(DesktopNewMenuExtension.class);
    extensionRegistry.register(PersonFormTabExtension.class);
    extensionRegistry.register(PersonTablePageExtension.class);

    // Register DTO extensions
    extensionRegistry.register(PersonFormTabExtensionData.class);
    extensionRegistry.register(PersonTablePageDataExtension.class);
  }
}
