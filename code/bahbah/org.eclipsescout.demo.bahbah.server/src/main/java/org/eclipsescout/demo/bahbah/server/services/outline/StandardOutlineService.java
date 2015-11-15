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
package org.eclipsescout.demo.bahbah.server.services.outline;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipsescout.demo.bahbah.server.ServerSession;
import org.eclipsescout.demo.bahbah.shared.services.outline.IStandardOutlineService;
import org.eclipsescout.demo.bahbah.shared.services.process.IUserProcessService;

public class StandardOutlineService implements IStandardOutlineService {

  @Override
  public String[] getOnlineUsers() {
    Set<String> allUsers = BEANS.get(IUserProcessService.class).getUsersOnline();
    Set<String> users = new HashSet<String>(allUsers);
    // remove myself
    users.remove(ServerSession.get().getUserId());
    return users.toArray(new String[users.size()]);
  }
}
