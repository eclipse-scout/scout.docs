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
package org.eclipse.scout.contacts.server.common;

import org.eclipse.scout.contacts.server.sql.IDataStoreService;
import org.eclipse.scout.contacts.shared.common.IResetDataStoreService;
import org.eclipse.scout.rt.platform.BEANS;

public class ResetDataStoreService implements IResetDataStoreService {

  @Override
  public void resetDataStore() {
    for (IDataStoreService service : BEANS.all(IDataStoreService.class)) {
      service.dropDataStore();
      service.createDataStore();
    }
  }
}
