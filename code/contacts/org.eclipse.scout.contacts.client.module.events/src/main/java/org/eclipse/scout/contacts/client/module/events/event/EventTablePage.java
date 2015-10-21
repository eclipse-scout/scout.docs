/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.client.module.events.event;

import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.annotations.PageData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.client.module.events.Icons;
import org.eclipse.scout.contacts.client.organization.OrganizationDetailsNodePage;
import org.eclipse.scout.contacts.shared.module.events.event.EventTablePageData;
import org.eclipse.scout.contacts.shared.module.events.event.IEventService;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@PageData(EventTablePageData.class)
public class EventTablePage extends AbstractPageWithTable<EventTablePage.Table> {

  private String m_organizationId;

  @Override
  protected String getConfiguredIconId() {
    return Icons.Event;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Events");
  }

  @Override
  protected void execInitPage() throws ProcessingException {
    // TODO [dwi] verify
    OrganizationDetailsNodePage organizationParentPage = getParentNode(OrganizationDetailsNodePage.class);

    if (organizationParentPage != null) {
      setOrganizationId(organizationParentPage.getOrganizationId());
    }
  }

  @Override
  protected void execLoadData(SearchFilter filter) throws ProcessingException {
    importPageData(BEANS.get(IEventService.class).getTableData(filter, getOrganizationId()));
  }

  @Order(1_000.0)
  public class Table extends AbstractTable {

    public EventIdColumn getEventIdColumn() {
      return getColumnSet().getColumnByClass(EventIdColumn.class);
    }

    public TitleColumn getTitleColumn() {
      return getColumnSet().getColumnByClass(TitleColumn.class);
    }

    public StartsColumn getStartsColumn() {
      return getColumnSet().getColumnByClass(StartsColumn.class);
    }

    public EndsColumn getEndsColumn() {
      return getColumnSet().getColumnByClass(EndsColumn.class);
    }

    public CityColumn getCityColumn() {
      return getColumnSet().getColumnByClass(CityColumn.class);
    }

    public CountryColumn getCountryColumn() {
      return getColumnSet().getColumnByClass(CountryColumn.class);
    }

    public HomepageColumn getHomepageColumn() {
      return getColumnSet().getColumnByClass(EventTablePage.Table.HomepageColumn.class);
    }

    public ParticipantsColumn getParticipantsColumn() {
      return getColumnSet().getColumnByClass(ParticipantsColumn.class);
    }

    @Override
    protected String getConfiguredDefaultIconId() {
      return Icons.Event;
    }

    @Override
    protected Class<? extends IMenu> getConfiguredDefaultMenu() {
      return EditMenu.class;
    }

    @Order(1_000.0)
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

    @Order(2_000.0)
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

    @Order(3_000.0)
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

    @Order(4_000.0)
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

    @Order(5_000.0)
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

    @Order(6_000.0)
    public class CountryColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Country");
      }
    }

    @Order(7_000.0)
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

    @Order(8_000.0)
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

    @Order(1_000.0)
    public class EditMenu extends AbstractMenu {

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

    @Order(2_000.0)
    public class NewMenu extends AbstractMenu {

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

  @FormData
  public String getOrganizationId() {
    return m_organizationId;
  }

  @FormData
  public void setOrganizationId(String organizationId) {
    m_organizationId = organizationId;
  }
}
