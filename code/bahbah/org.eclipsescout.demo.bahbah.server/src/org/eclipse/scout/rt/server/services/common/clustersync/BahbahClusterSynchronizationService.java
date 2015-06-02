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
package org.eclipse.scout.rt.server.services.common.clustersync;

import java.util.UUID;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.IServerSession;
import org.eclipsescout.demo.bahbah.server.ServerSession;

/**
 * Cluster sync service with custom backen session
 */
public class BahbahClusterSynchronizationService extends ClusterSynchronizationService {

  /**
   * Create an empty backend session
   */
  @Override
  protected IServerSession createBackendSession() {
    IServerSession session = new ServerSession() {
      private static final long serialVersionUID = 6734092829074019358L;

      @Override
      protected void execLoadSession() throws ProcessingException {
        super.execLoadSession();
      }
    };
    session.setIdInternal(UUID.randomUUID().toString());
    return session;
  }

}
