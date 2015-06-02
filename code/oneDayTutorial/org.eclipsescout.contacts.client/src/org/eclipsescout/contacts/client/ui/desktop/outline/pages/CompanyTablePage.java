/**
 *
 */
package org.eclipsescout.contacts.client.ui.desktop.outline.pages;

import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.annotations.PageData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm;
import org.eclipsescout.contacts.shared.services.IStandardOutlineService;
import org.eclipsescout.contacts.shared.ui.desktop.outline.pages.CompanyTablePageData;

/**
 * @author mzi
 */
@PageData(CompanyTablePageData.class)
public class CompanyTablePage extends AbstractPageWithTable<CompanyTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Company");
  }

  @Override
  protected IPage execCreateChildPage(ITableRow row) throws ProcessingException {
    PersonTablePage childPage = new PersonTablePage();
    childPage.setCompanyId(getTable().getCompanyIdColumn().getValue(row));
    return childPage;
  }

  @Override
  protected Object[][] execLoadTableData(SearchFilter filter) throws ProcessingException {
    return SERVICES.getService(IStandardOutlineService.class).getCompanyTableData();
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {

    /**
     * @return the NameColumn
     */
    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    /**
     * @return the LocationColumn
     */
    public LocationColumn getLocationColumn() {
      return getColumnSet().getColumnByClass(LocationColumn.class);
    }

    @Override
    protected void execRowAction(ITableRow row) throws ProcessingException {
      getMenu(EditCompanyMenu.class).execAction();
    }

    /**
     * @return the CompanyIdColumn
     */
    public CompanyIdColumn getCompanyIdColumn() {
      return getColumnSet().getColumnByClass(CompanyIdColumn.class);
    }

    @Order(10.0)
    public class CompanyIdColumn extends AbstractStringColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }
    }

    @Order(20.0)
    public class NameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }
    }

    @Order(30.0)
    public class LocationColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Location");
      }
    }

    @Order(10.0)
    public class EditCompanyMenu extends AbstractExtensibleMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("EditCompany");
      }

      @Override
      protected void execAction() throws ProcessingException {
        CompanyForm form = new CompanyForm();
        form.setCompanyId(getCompanyIdColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(20.0)
    public class NewCompanyMenu extends AbstractExtensibleMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewCompany");
      }

      @Override
      protected void execAction() throws ProcessingException {
        CompanyForm form = new CompanyForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }
  }
}
