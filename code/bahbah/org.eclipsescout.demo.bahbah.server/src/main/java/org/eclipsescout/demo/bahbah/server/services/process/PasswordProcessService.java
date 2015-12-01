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
package org.eclipsescout.demo.bahbah.server.services.process;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringHolder;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.server.services.common.pwd.AbstractPasswordManagementService;
import org.eclipse.scout.rt.server.services.common.pwd.IPasswordPolicy;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipsescout.demo.bahbah.server.util.UserUtility;
import org.eclipsescout.demo.bahbah.shared.security.ResetPasswordPermission;
import org.eclipsescout.demo.bahbah.shared.services.process.IPasswordProcessService;
import org.eclipsescout.demo.bahbah.shared.util.SharedUserUtility;

public class PasswordProcessService extends AbstractPasswordManagementService implements IPasswordProcessService {

  @PostConstruct
  public void initializePasswordPolicy() {
    setPasswordPolicy(new IPasswordPolicy() {

      @Override
      public String getText() {
        return "";
      }

      @Override
      public void check(String userId, String newPassword, String userName, int historyIndex) {
        SharedUserUtility.checkPassword(newPassword);
        SharedUserUtility.checkUsername(userName);
      }
    });
  }

  @Override
  public Date getPasswordExpirationDate(String userId) {
    // never expire
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DATE, 1);
    return c.getTime();
  }

  @Override
  protected void checkAccess(String userId, String password) {
    if (!ACCESS.check(new ResetPasswordPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
  }

  @Override
  protected String getUsernameFor(String userId) {
    StringHolder holder = new StringHolder();
    SQL.selectInto("SELECT username FROM TABUSERS WHERE u_id = :uid INTO :uname", new NVPair("uid", userId), new NVPair("uname", holder));
    return holder.getValue();
  }

  @Override
  protected int getHistoryIndexFor(String userId, String password) {
    return 0;
  }

  @Override
  protected void resetPasswordInternal(String userId, String newPassword) {
    Long u_id = Long.parseLong(userId);
    UserUtility.resetPassword(u_id, newPassword);
  }
}
