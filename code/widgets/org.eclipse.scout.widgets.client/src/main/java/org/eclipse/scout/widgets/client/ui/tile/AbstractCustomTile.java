package org.eclipse.scout.widgets.client.ui.tile;

import org.eclipse.scout.rt.client.ui.tile.AbstractTile;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.annotations.ConfigProperty;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("f1be145a-6c22-4888-99cc-bb9fe7b8d2ef")
public abstract class AbstractCustomTile extends AbstractTile implements ICustomTile {

  private String m_group;

  public AbstractCustomTile() {
    super();
  }

  public AbstractCustomTile(boolean callInitializer) {
    super(callInitializer);
  }

  @Override
  protected void initConfig() {
    super.initConfig();
    setLabel(getConfiguredLabel());
  }

  @Override
  protected String getConfiguredDisplayStyle() {
    return DISPLAY_STYLE_PLAIN;
  }

  @ConfigProperty(ConfigProperty.TEXT)
  @Order(10)
  protected String getConfiguredLabel() {
    return null;
  }

  @Override
  public String getLabel() {
    return propertySupport.getPropertyString(PROP_LABEL);
  }

  @Override
  public void setLabel(String label) {
    propertySupport.setProperty(PROP_LABEL, label);
  }

  @Override
  public String getGroup() {
    return m_group;
  }

  @Override
  public void setGroup(String group) {
    m_group = group;
  }
}
