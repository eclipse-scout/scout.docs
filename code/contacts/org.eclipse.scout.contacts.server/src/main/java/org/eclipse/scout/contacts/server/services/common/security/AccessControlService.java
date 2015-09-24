package org.eclipse.scout.contacts.server.services.common.security;

import java.security.AllPermission;
import java.security.Permissions;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.server.services.common.security.AbstractAccessControlService;
import org.eclipse.scout.rt.shared.security.RemoteServiceAccessPermission;

@Order(-1)
public class AccessControlService extends AbstractAccessControlService {

  @Override
  protected Permissions execLoadPermissions() {
    Permissions permissions = new Permissions();
    permissions.add(new RemoteServiceAccessPermission("*.shared.*", "*"));

    //TODO [dwi]: Fill access control service
    permissions.add(new AllPermission());
    return permissions;
  }
}
