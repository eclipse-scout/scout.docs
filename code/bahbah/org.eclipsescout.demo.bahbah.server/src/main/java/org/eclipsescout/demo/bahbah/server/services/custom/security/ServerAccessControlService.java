/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipsescout.demo.bahbah.server.services.custom.security;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.security.DefaultPermissionCollection;
import org.eclipse.scout.rt.security.IPermissionCollection;
import org.eclipse.scout.rt.security.PermissionLevel;
import org.eclipse.scout.rt.shared.security.RemoteServiceAccessPermission;
import org.eclipse.scout.rt.shared.security.UpdateServiceConfigurationPermission;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipsescout.demo.bahbah.server.ServerSession;
import org.eclipsescout.demo.bahbah.shared.security.CreateNotificationPermission;
import org.eclipsescout.demo.bahbah.shared.security.CreateUserPermission;
import org.eclipsescout.demo.bahbah.shared.security.DeleteUserPermission;
import org.eclipsescout.demo.bahbah.shared.security.ReadUsersPermission;
import org.eclipsescout.demo.bahbah.shared.security.RegisterUserPermission;
import org.eclipsescout.demo.bahbah.shared.security.ResetPasswordPermission;
import org.eclipsescout.demo.bahbah.shared.security.UnregisterUserPermission;
import org.eclipsescout.demo.bahbah.shared.security.UpdateIconPermission;
import org.eclipsescout.demo.bahbah.shared.security.UpdateUserPermission;
import org.eclipsescout.demo.bahbah.shared.services.code.UserRoleCodeType.AdministratorCode;
import org.eclipsescout.demo.bahbah.shared.services.code.UserRoleCodeType.UserCode;
import org.eclipsescout.demo.bahbah.shared.services.custom.security.AccessControlService;

@Replace
public class ServerAccessControlService extends AccessControlService {

  @Override
  protected IPermissionCollection execLoadPermissions(String userId) {
    IPermissionCollection permissions = BEANS.get(DefaultPermissionCollection.class);

    ICode<Integer> permission = ServerSession.get().getPermission();
    if (permission != null) {
      // USERS
      if (permission.getId() >= UserCode.ID) {
        permissions.add(new RemoteServiceAccessPermission("*.shared.*", "*"), PermissionLevel.ALL);

        permissions.add(new CreateNotificationPermission(), PermissionLevel.ALL);
        permissions.add(new ReadUsersPermission(), PermissionLevel.ALL);
        permissions.add(new RegisterUserPermission(), PermissionLevel.ALL);
        permissions.add(new UnregisterUserPermission(), PermissionLevel.ALL);
        permissions.add(new UpdateIconPermission(), PermissionLevel.ALL);
      }

      // ADMIN
      if (permission.getId() >= AdministratorCode.ID) {
        permissions.add(new CreateUserPermission(), PermissionLevel.ALL);
        permissions.add(new DeleteUserPermission(), PermissionLevel.ALL);
        permissions.add(new ResetPasswordPermission(), PermissionLevel.ALL);
        permissions.add(new UpdateUserPermission(), PermissionLevel.ALL);
        permissions.add(new UpdateServiceConfigurationPermission(), PermissionLevel.ALL);
      }
    }
    permissions.setReadOnly();
    return permissions;
  }
}
