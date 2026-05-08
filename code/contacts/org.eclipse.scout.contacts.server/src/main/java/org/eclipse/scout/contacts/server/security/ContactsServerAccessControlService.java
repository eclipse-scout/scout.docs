/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.server.security;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.scout.contacts.shared.ContactsSharedConfigProperties.ReadOnlyProperty;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.internal.BeanInstanceUtil;
import org.eclipse.scout.rt.platform.inventory.ClassInventory;
import org.eclipse.scout.rt.platform.inventory.IClassInfo;
import org.eclipse.scout.rt.platform.security.User;
import org.eclipse.scout.rt.security.DefaultPermissionCollection;
import org.eclipse.scout.rt.security.IPermission;
import org.eclipse.scout.rt.security.IPermissionCollection;
import org.eclipse.scout.rt.security.PermissionLevel;
import org.eclipse.scout.rt.shared.security.CopyToClipboardPermission;
import org.eclipse.scout.rt.shared.security.RemoteServiceAccessPermission;

/**
 * The Contacts application logic is extracted to this extra class to not pollute the super class used in the One-Day-Tutorial.
 * The extra readOnly Contacts logic is implemented here, extending the default behavior of the super class.
 */
@Replace
public class ContactsServerAccessControlService extends ServerAccessControlService {
  @Override
  protected IPermissionCollection execLoadPermissions(User user) {
    if (CONFIG.getPropertyValue(ReadOnlyProperty.class)) {
      DefaultPermissionCollection permissions = BEANS.get(DefaultPermissionCollection.class);
      getReadPermissions().forEach(permissions::add);
      permissions.add(new RemoteServiceAccessPermission("*.shared.*", "*"), PermissionLevel.ALL);
      permissions.add(new CopyToClipboardPermission(), PermissionLevel.ALL);
      return permissions;
    }
    return super.execLoadPermissions(user);
  }

  protected Stream<? extends IPermission> getReadPermissions() {
    return getReadPermissionClasses()
        .map(BeanInstanceUtil::beanInstanceCreator)
        .map(Supplier::get);
  }

  @SuppressWarnings("unchecked")
  protected Stream<Class<? extends IPermission>> getReadPermissionClasses() {
    return ClassInventory.get().getAllKnownSubClasses(IPermission.class).stream()
        .filter(IClassInfo::isInstanciable)
        .filter(IClassInfo::hasNoArgsConstructor)
        .filter(classInfo -> classInfo.name().contains("Read"))
        .map(classInfo -> (Class<? extends IPermission>) classInfo.resolveClass());
  }
}
