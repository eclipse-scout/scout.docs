/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms.menu;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClassId("30c32c32-956f-4a98-a4f0-b7787bee67f6")
public abstract class AbstractToggleMenu extends AbstractMenu {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractToggleMenu.class);

  private boolean m_active = false;

  private String m_origText;

  private String m_origTooltipText;

  @Override
  protected void execAction() {
    setActive(!m_active); // toggle
  }

  @Override
  protected void initConfig() {
    super.initConfig();
    m_origText = getConfiguredText();
    m_origTooltipText = getConfiguredTooltipText();
    updateTexts();
  }

  public void reset() {
    setActive(false);
  }

  protected void setActive(boolean active) {
    m_active = active;
    if (m_active) {
      execToggleActive();
    }
    else {
      execToggleInactive();
    }
    execAlways();
    updateTexts();
    LOG.debug("Toggled menu " + this.getClass().getSimpleName() + ". Menu is now: " + (m_active ? "active" : "inactive"));
  }

  private void updateTexts() {
    // text
    String text = StringUtility.emptyIfNull(m_origText);
    text = "[" + (m_active ? "X" : "..") + "] " + text + " (click to " + (m_active ? "deactivate" : "activate") + ")";
    setText(text);

    // tooltip text
    String tooltipText = StringUtility.emptyIfNull(m_origTooltipText);
    if (StringUtility.hasText(tooltipText)) {
      tooltipText += "\n\n";
    }
    tooltipText += "Currently " + (m_active ? "active" : "not active");
    setTooltipText(tooltipText);
  }

  protected void execToggleInactive() {
  }

  protected void execToggleActive() {
  }

  protected void execAlways() {
  }

  public boolean isActive() {
    return m_active;
  }

}
