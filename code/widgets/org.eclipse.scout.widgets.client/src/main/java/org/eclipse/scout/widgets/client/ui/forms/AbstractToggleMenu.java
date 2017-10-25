package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
