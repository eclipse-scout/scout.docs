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
package org.eclipsescout.demo.bahbah.server.services.custom.security;

import java.security.Permissions;

import org.eclipse.scout.rt.platform.Replace;
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
  protected Permissions execLoadPermissions(String userId) {
    Permissions permissions = new Permissions();

    ICode<Integer> permission = ServerSession.get().getPermission();
    if (permission != null) {
      // USERS
      if (permission.getId() >= UserCode.ID) {
        permissions.add(new RemoteServiceAccessPermission("*.shared.*", "*"));

        permissions.add(new CreateNotificationPermission());
        permissions.add(new ReadUsersPermission());
        permissions.add(new RegisterUserPermission());
        permissions.add(new UnregisterUserPermission());
        permissions.add(new UpdateIconPermission());
      }

      // ADMIN
      if (permission.getId() >= AdministratorCode.ID) {
        permissions.add(new CreateUserPermission());
        permissions.add(new DeleteUserPermission());
        permissions.add(new ResetPasswordPermission());
        permissions.add(new UpdateUserPermission());
        permissions.add(new UpdateServiceConfigurationPermission());
      }
    }
    return permissions;
  }
}
