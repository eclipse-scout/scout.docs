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
package org.eclipsescout.demo.bahbah.server.services.notification;

import org.eclipse.scout.commons.CompareUtility;
import org.eclipse.scout.rt.server.services.common.clustersync.IClusterNotification;

public class RegisterUserNotification implements IClusterNotification {

  private static final long serialVersionUID = -8987801608205820581L;
  private String m_userName;

  public RegisterUserNotification(String username) {
    m_userName = username;
  }

  public String getUserName() {
    return m_userName;
  }

  @Override
  public boolean coalesce(IClusterNotification existingNotification0) {
    if (existingNotification0 instanceof RegisterUserNotification) {
      RegisterUserNotification existingNotification = (RegisterUserNotification) existingNotification0;
      return CompareUtility.equals(getUserName(), existingNotification.getUserName());
    }
    return false;
  }
}
