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
package org.eclipsescout.demo.bahbah.client.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipsescout.demo.bahbah.client.ui.desktop.outlines.pages.UserAdministrationTablePage;
import org.eclipsescout.demo.bahbah.shared.security.CreateUserPermission;
import org.eclipsescout.demo.bahbah.shared.security.DeleteUserPermission;
import org.eclipsescout.demo.bahbah.shared.security.ResetPasswordPermission;
import org.eclipsescout.demo.bahbah.shared.security.UpdateUserPermission;

public class AdministrationOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Administration");
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    UserAdministrationTablePage usersTablePage = new UserAdministrationTablePage();
    pageList.add(usersTablePage);
  }

  @Override
  protected void execInitTree() {
    setVisible(UserAgentUtility.isDesktopDevice() &&
        (ACCESS.check(new CreateUserPermission()) || ACCESS.check(new DeleteUserPermission()) ||
            ACCESS.check(new ResetPasswordPermission()) || ACCESS.check(new UpdateUserPermission())));
  }
}
