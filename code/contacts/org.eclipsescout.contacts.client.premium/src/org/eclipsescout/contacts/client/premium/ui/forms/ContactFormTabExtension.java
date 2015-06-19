/**
 *
 */
package org.eclipsescout.contacts.client.premium.ui.forms;

import org.eclipse.scout.commons.annotations.Data;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.client.extension.ui.form.fields.tabbox.AbstractTabBoxExtension;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox.ParticipantsBox.ParticipantsField.Table.CompanyColumn;
import org.eclipsescout.contacts.client.ui.forms.ContactForm;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox;
import org.eclipsescout.contacts.shared.premium.Icons;
import org.eclipsescout.contacts.shared.premium.ui.forms.ContactFormTabExtensionData;

/**
 * @author mzi
 */
@Data(ContactFormTabExtensionData.class) // <1>
public class ContactFormTabExtension extends AbstractTabBoxExtension<ContactForm.MainBox.DetailsBox> { // <2>

  public ContactFormTabExtension(DetailsBox owner) {
    super(owner);
  }

  @Order(3000) // <3>
  public class EventBox extends AbstractGroupBox {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Events");
    }

    @Order(1000.0)
    public class EventsField extends AbstractTableField<EventsField.Table> {
      // <4>
      @Override
      protected int getConfiguredGridH() {
        return 3;
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Order(1000.0)
      public class Table extends AbstractExtensibleTable {

        @Override
        protected String getConfiguredDefaultIconId() {
          return Icons.Event;
        }

        /**
         * @return the LastNameColumn
         */
        public StartsColumn getLastNameColumn() {
          return getColumnSet().getColumnByClass(StartsColumn.class);
        }

        /**
         * @return the CompanyColumn
         */
        public CompanyColumn getCompanyColumn() {
          return getColumnSet().getColumnByClass(CompanyColumn.class);
        }

        /**
         * @return the FirstNameColumn
         */
        public TitleColumn getFirstNameColumn() {
          return getColumnSet().getColumnByClass(TitleColumn.class);
        }

        /**
         * @return the ContactIdColumn
         */
        public IdColumn getIdColumn() {
          return getColumnSet().getColumnByClass(IdColumn.class);
        }

        @Order(1000.0)
        public class IdColumn extends AbstractStringColumn {

          @Override
          protected boolean getConfiguredDisplayable() {
            return false;
          }

          @Override
          protected boolean getConfiguredPrimaryKey() {
            return true;
          }
        }

        @Order(2000.0)
        public class TitleColumn extends AbstractStringColumn {

          @Override
          protected String getConfiguredHeaderText() {
            return TEXTS.get("Title");
          }

          @Override
          protected int getConfiguredWidth() {
            return 250;
          }
        }

        @Order(3000.0)
        public class StartsColumn extends AbstractDateTimeColumn {

          @Override
          protected String getConfiguredHeaderText() {
            return TEXTS.get("Starts");
          }

          @Override
          protected int getConfiguredSortIndex() {
            return 0;
          }

          @Override
          protected int getConfiguredWidth() {
            return 120;
          }

        }

        @Order(4000.0)
        public class CityColumn extends AbstractStringColumn {

          @Override
          protected String getConfiguredHeaderText() {
            return TEXTS.get("City");
          }

          @Override
          protected int getConfiguredWidth() {
            return 120;
          }
        }

        @Order(5000.0)
        public class CountryColumn extends AbstractStringColumn {

          @Override
          protected String getConfiguredHeaderText() {
            return TEXTS.get("Country");
          }
        }
      }
    }
  }
}
