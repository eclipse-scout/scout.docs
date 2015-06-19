/**
 *
 */
package org.eclipsescout.contacts.client.ui.desktop.outlines;

import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.annotations.PageData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.pages.AbstractExtensiblePageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.client.ui.desktop.outlines.ContactsTablePage.Table;
import org.eclipsescout.contacts.client.ui.forms.ContactForm;
import org.eclipsescout.contacts.shared.Icons;
import org.eclipsescout.contacts.shared.services.IStandardOutlineService;
import org.eclipsescout.contacts.shared.services.lookup.CompanyLookupCall;
import org.eclipsescout.contacts.shared.ui.desktop.outlines.ContactsTablePageData;

/**
 * @author mzi
 */
@PageData(ContactsTablePageData.class)
public class ContactsTablePage extends AbstractExtensiblePageWithTable<Table> {

  private String m_companyId;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Contacts");
  }

  @Override
  protected void execLoadData(SearchFilter filter) throws ProcessingException {
    importPageData(SERVICES.getService(IStandardOutlineService.class).getContactsTableData(filter, getCompanyId()));
  }

  @Order(1000.0)
  public class Table extends AbstractExtensibleTable {

    /**
     * @return the LastNameColumn
     */
    public LastNameColumn getLastNameColumn() {
      return getColumnSet().getColumnByClass(LastNameColumn.class);
    }

    @Override
    protected String getConfiguredDefaultIconId() {
      return Icons.Contact;
    }

    /**
     * @return the CountryColumn
     */
    public CountryColumn getCountryColumn() {
      return getColumnSet().getColumnByClass(CountryColumn.class);
    }

    /**
     * @return the EmailColumn
     */
    public EmailColumn getEmailColumn() {
      return getColumnSet().getColumnByClass(ContactsTablePage.Table.EmailColumn.class);
    }

    /**
     * @return the CompanyColumn
     */
    public CompanyColumn getCompanyColumn() {
      return getColumnSet().getColumnByClass(CompanyColumn.class);
    }

    @Override
    protected void execRowAction(ITableRow row) throws ProcessingException {
      getMenu(EditMenu.class).doAction();
    }

    /**
     * @return the CityColumn
     */
    public CityColumn getCityColumn() {
      return getColumnSet().getColumnByClass(CityColumn.class);
    }

    /**
     * @return the FirstNameColumn
     */
    public FirstNameColumn getFirstNameColumn() {
      return getColumnSet().getColumnByClass(FirstNameColumn.class);
    }

    /**
     * @return the ContactIdColumn
     */
    public ContactIdColumn getContactIdColumn() {
      return getColumnSet().getColumnByClass(ContactIdColumn.class);
    }

    @Order(1000.0)
    public class ContactIdColumn extends AbstractStringColumn {

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
    public class FirstNameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FirstName");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(3000.0)
    public class LastNameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("LastName");
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

    @Order(6000.0)
    public class PhoneColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Phone");
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(7000.0)
    public class MobileColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Mobile");
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(8000.0)
    public class EmailColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Email");
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(9000.0)
    public class CompanyColumn extends AbstractSmartColumn<String> {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Company");
      }

      @Override
      protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
        return CompanyLookupCall.class;
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(1000.0)
    public class EditMenu extends AbstractExtensibleMenu {

      @Override
      protected String getConfiguredKeyStroke() {
        return "alt-e";
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Edit");
      }

      @Override
      protected void execAction() throws ProcessingException {
        ContactForm form = new ContactForm();
        form.setContactId(getContactIdColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(2000.0)
    public class NewMenu extends AbstractExtensibleMenu {

      @Override
      protected String getConfiguredKeyStroke() {
        return "alt-n";
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("New");
      }

      @Override
      protected void execAction() throws ProcessingException {
        ContactForm form = new ContactForm();
        form.getCompanyField().setValue(getCompanyId());
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Contact;
  }

  @Override
  protected Class<? extends ISearchForm> getConfiguredSearchForm() {
    return ContactsSearchForm.class;
  }

  /**
   * @return the CompanyId
   */
  @FormData
  public String getCompanyId() {
    return m_companyId;
  }

  /**
   * @param companyId
   *          the CompanyId to set
   */
  @FormData
  public void setCompanyId(String companyId) {
    m_companyId = companyId;
  }
}
