/**
 *
 */
package org.eclipsescout.contacts.client.premium.ui.desktop.outlines;

import org.eclipse.scout.commons.annotations.Data;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.client.extension.ui.basic.table.AbstractTableExtension;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.contacts.client.ui.desktop.outlines.ContactsTablePage;
import org.eclipsescout.contacts.client.ui.desktop.outlines.ContactsTablePage.Table;
import org.eclipsescout.contacts.shared.premium.ui.desktop.outlines.ContactsTableDataExtension;

/**
 * @author mzi
 */
@Data(ContactsTableDataExtension.class)
public class ContactsTableExtension extends AbstractTableExtension<ContactsTablePage.Table> {

  /**
   * @param owner
   */
  public ContactsTableExtension(Table owner) {
    super(owner);
  }

  @Order(10000.0)
  public class EventsColumn extends AbstractLongColumn {

    @Override
    protected String getConfiguredHeaderText() {
      return TEXTS.get("Events");
    }

    @Override
    protected int getConfiguredWidth() {
      return 100;
    }
  }

}
