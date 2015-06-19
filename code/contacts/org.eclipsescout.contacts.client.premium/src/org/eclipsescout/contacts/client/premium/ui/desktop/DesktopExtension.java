package org.eclipsescout.contacts.client.premium.ui.desktop;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipsescout.contacts.client.premium.ui.desktop.outlines.EventManagementOutline;

public class DesktopExtension extends AbstractDesktopExtension {
  public DesktopExtension() {
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    List<Class<? extends IOutline>> outlines = new ArrayList<Class<? extends IOutline>>();
    outlines.add(EventManagementOutline.class);
    return outlines;
  }

  @Order(2000.0)
  public class EventManagementOutlineViewButton extends AbstractOutlineViewButton {

    /**
     *
     */
    public EventManagementOutlineViewButton() {
      super(getCoreDesktop(), EventManagementOutline.class);
    }
  }
}
