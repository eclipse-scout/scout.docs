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
package org.eclipse.scout.widgets.client.mobile.ui.desktop;

import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.ContributionCommand;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;

public class DesktopExtension extends AbstractDesktopExtension {
  private boolean m_active;

  public DesktopExtension() {
    setActive(!UserAgentUtility.isDesktopDevice());
  }

  public boolean isActive() {
    return m_active;
  }

  public void setActive(boolean active) {
    m_active = active;
  }

  @Override
  protected ContributionCommand execGuiAttached() {
    if (!isActive()) {
      return super.execGuiAttached();
    }
    return ContributionCommand.Continue;
  }

  @Override
  protected ContributionCommand execGuiDetached() {
    if (!isActive()) {
      return super.execGuiDetached();
    }
    return ContributionCommand.Continue;
  }
}
