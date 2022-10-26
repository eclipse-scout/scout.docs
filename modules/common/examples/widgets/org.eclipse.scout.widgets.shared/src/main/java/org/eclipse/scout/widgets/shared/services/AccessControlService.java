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
package org.eclipse.scout.widgets.shared.services;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.security.AbstractAccessControlService;
import org.eclipse.scout.rt.security.AllPermissionCollection;
import org.eclipse.scout.rt.security.IPermissionCollection;
import org.eclipse.scout.rt.shared.session.Sessions;

/**
 * Implementation of {@link org.eclipse.scout.rt.shared.services.common.security.IAccessControlService}
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
