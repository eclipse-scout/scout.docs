/**
 *
 */
package org.eclipsescout.contacts.client.premium.ui.desktop;

import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.extension.ui.action.menu.AbstractMenuExtension;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm;
import org.eclipsescout.contacts.client.ui.desktop.Desktop.FileMenu.NewMenu;

/**
 * @author mzi
 */
//tag::ModularScoutApps.MenuExtension[]
public class NewMenuExtension extends AbstractMenuExtension<NewMenu> { // <1>

  public NewMenuExtension(NewMenu owner) {
    super(owner);
  }

  @Order(3000.0) // <2>
  public class EventMenu extends AbstractExtensibleMenu {

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet();
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Event_");
    }

    @Override
    protected void execAction() throws ProcessingException {
      EventForm form = new EventForm();
      form.startNew();
    }
  }
}
//end::ModularScoutApps.MenuExtension[]
