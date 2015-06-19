/**
 *
 */
package org.eclipsescout.contacts.client.premium.ui.desktop.outlines;

import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.annotations.PageData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm;
import org.eclipsescout.contacts.client.ui.desktop.outlines.CompanyDetailsNodePage;
import org.eclipsescout.contacts.shared.premium.Icons;
import org.eclipsescout.contacts.shared.premium.services.IEventManagementOutlineService;
import org.eclipsescout.contacts.shared.premium.ui.desktop.outlines.EventsTablePageData;

/**
 * @author mzi
 */
@PageData(EventsTablePageData.class)
public class EventsTablePage extends AbstractPageWithTable<EventsTablePage.Table> {

  private String m_companyId;

  @Override
  protected String getConfiguredIconId() {
    return Icons.Event;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Events");
  }

  /**
   * checks for parent pages (contact and company) and set's event page variables accordingly.
   *
   * @see org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPage#execInitPage()
   */
  @Override
  protected void execInitPage() throws ProcessingException {
    CompanyDetailsNodePage companyParentPage = getParentNode(CompanyDetailsNodePage.class);

    if (companyParentPage != null) {
      setCompanyId(companyParentPage.getCompanyId());
    }
  }

  @Override
  protected void execLoadData(SearchFilter filter) throws ProcessingException {
    importPageData(SERVICES.getService(IEventManagementOutlineService.class).getEventsTableData(filter, getCompanyId()));
  }

  @Order(1000.0)
  public class Table extends AbstractExtensibleTable {

    /**
     * @return the TitleColumn
     */
    public TitleColumn getTitleColumn() {
      return getColumnSet().getColumnByClass(TitleColumn.class);
    }

    /**
     * @return the StartsColumn
     */
    public StartsColumn getStartsColumn() {
      return getColumnSet().getColumnByClass(StartsColumn.class);
    }

    /**
     * @return the CountryColumn
     */
    public CountryColumn getCountryColumn() {
      return getColumnSet().getColumnByClass(CountryColumn.class);
    }

    @Override
    protected String getConfiguredDefaultIconId() {
      return Icons.Event;
    }

    @Override
    protected void execRowAction(ITableRow row) throws ProcessingException {
      getMenu(EditMenu.class).doAction();
    }

    /**
     * @return the ParticipantsColumn
     */
    public ParticipantsColumn getParticipantsColumn() {
      return getColumnSet().getColumnByClass(ParticipantsColumn.class);
    }

    /**
     * @return the HomepageColumn
     */
    public HomepageColumn getHomepageColumn() {
      return getColumnSet().getColumnByClass(EventsTablePage.Table.HomepageColumn.class);
    }

    /**
     * @return the CityColumn
     */
    public CityColumn getCityColumn() {
      return getColumnSet().getColumnByClass(CityColumn.class);
    }

    /**
     * @return the EndsColumn
     */
    public EndsColumn getEndsColumn() {
      return getColumnSet().getColumnByClass(EndsColumn.class);
    }

    /**
     * @return the EventIdColumn
     */
    public EventIdColumn getEventIdColumn() {
      return getColumnSet().getColumnByClass(EventIdColumn.class);
    }

    @Order(1000.0)
    public class EventIdColumn extends AbstractStringColumn {

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
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(4000.0)
    public class EndsColumn extends AbstractDateTimeColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Ends");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(5000.0)
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

    @Order(6000.0)
    public class CountryColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Country");
      }
    }

    @Order(6250.0)
    public class HomepageColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Homepage");
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }

      @Override
      protected int getConfiguredWidth() {
        return 250;
      }
    }

    @Order(6500.0)
    public class ParticipantsColumn extends AbstractIntegerColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Participants");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(1000.0)
    public class EditMenu extends AbstractExtensibleMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Edit");
      }

      @Override
      protected void execAction() throws ProcessingException {
        EventForm form = new EventForm();
        form.setEventId(getEventIdColumn().getSelectedValue());
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
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("New");
      }

      @Override
      protected void execAction() throws ProcessingException {
        EventForm form = new EventForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }
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
