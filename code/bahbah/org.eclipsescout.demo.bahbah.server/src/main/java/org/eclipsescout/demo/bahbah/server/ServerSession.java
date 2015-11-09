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
package org.eclipsescout.demo.bahbah.server;

import java.security.AccessController;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.server.AbstractServerSession;
import org.eclipse.scout.rt.server.session.ServerSessionProvider;
import org.eclipse.scout.rt.shared.services.common.code.CODES;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipsescout.demo.bahbah.shared.services.code.UserRoleCodeType;
import org.eclipsescout.demo.bahbah.shared.services.process.IUserProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerSession extends AbstractServerSession {
  private static final long serialVersionUID = -6930164140912861947L;
  private static final Logger LOG = LoggerFactory.getLogger(ServerSession.class);

  public ServerSession() {
    super(true);
  }

  /**
   * @return The {@link ServerSession} which is associated with the current thread, or <code>null</code> if not found.
   */
  public static ServerSession get() {
    return ServerSessionProvider.currentSession(ServerSession.class);
  }

  @SuppressWarnings("unchecked")
  public ICode<Integer> getPermission() {
    return getSharedContextVariable(IUserProcessService.PERMISSION_KEY, ICode.class);
  }

  public void setPermission(ICode<Integer> newValue) {
    setSharedContextVariable(IUserProcessService.PERMISSION_KEY, ICode.class, newValue);
  }

  @Override
  protected void execLoadSession() {
    if (getUserId() != null) {
      if (Subject.getSubject(AccessController.getContext()) == ServerApplication.getSubject()) {
        setPermission(CODES.getCode(UserRoleCodeType.UserCode.class));
      }
      else {
        LOG.info("created a new session for {}", getUserId());
        setPermission(BEANS.get(IUserProcessService.class).getUserPermission());
        BEANS.get(IUserProcessService.class).registerUser();
      }
    }
  }
}
