/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.jswidgets.ui.html.security;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.security.AbstractAccessControlService;
import org.eclipse.scout.rt.security.AllPermissionCollection;
import org.eclipse.scout.rt.security.IPermissionCollection;
import org.eclipse.scout.rt.shared.session.Sessions;

/**
 * Implementation of {@link org.eclipse.scout.rt.security.IAccessControlService}
 */
public class AccessControlService extends AbstractAccessControlService<String> {

  @Override
  protected String getCurrentUserCacheKey() {
    return Sessions.getCurrentUserId();
  }

  @Override
  public IPermissionCollection getPermissions() {
    return BEANS.get(AllPermissionCollection.class);
  }

  @Override
  protected IPermissionCollection execLoadPermissions(String userId) {
    throw new UnsupportedOperationException();
  }
}
