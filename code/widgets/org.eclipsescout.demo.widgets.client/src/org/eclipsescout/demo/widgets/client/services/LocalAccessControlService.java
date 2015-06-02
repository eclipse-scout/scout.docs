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
package org.eclipsescout.demo.widgets.client.services;

import java.security.AllPermission;
import java.security.Permission;
import java.security.Permissions;
import java.util.Collection;

import org.eclipse.scout.rt.shared.services.common.security.IAccessControlService;
import org.eclipse.scout.service.AbstractService;

public class LocalAccessControlService extends AbstractService implements IAccessControlService {

  private final Permissions m_permissions;

  public LocalAccessControlService() {
    m_permissions = new Permissions();
    m_permissions.add(new AllPermission());
  }

  @Override
  public boolean checkPermission(Permission p) {
    return m_permissions.implies(p);
  }

  @Override
  public int getPermissionLevel(Permission p) {
    return 100;
  }

  @Override
  public Permissions getPermissions() {
    return m_permissions;
  }

  @Override
  public boolean isProxyService() {
    return false;
  }

  @Override
  public void clearCache() {
    //nop
  }

  @Override
  public String getUserIdOfCurrentSubject() {
    return null;
  }

  @Override
  public void clearCacheOfUserIds(Collection<String> userIds) {
    //nop
  }
}
