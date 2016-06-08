/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.events.client.event;

import org.eclipse.scout.contacts.client.common.AbstractEditMenu;
import org.eclipse.scout.contacts.client.common.AbstractNewMenu;
import org.eclipse.scout.contacts.client.common.CountryLookupCall;
import org.eclipse.scout.contacts.client.organization.OrganizationNodePage;
import org.eclipse.scout.contacts.events.client.Icons;
import org.eclipse.scout.contacts.events.shared.event.EventTablePageData;
import org.eclipse.scout.contacts.events.shared.event.IEventService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@PageData(EventTablePageData.class)
public class EventTablePage extends AbstractPageWithTable<EventTablePage.Table> {

  private String organizationId;

  @Override
  protected String getConfiguredIconId() {
    return Icons.Event;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Events");
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execInitPage() {
    OrganizationNodePage organizationParentPage = getParentNode(OrganizationNodePage.class);

    if (organizationParentPage != null) {
      setOrganizationId(organizationParentPage.getOrganizationId());
    }
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IEventService.class).getTableData(filter, getOrganizationId()));
  }

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

    @Order(1)
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

    @Order(2)
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

    @Order(3)
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

    @Order(4)
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

    @Order(5)
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

    @Order(6)
    public class CountryColumn extends AbstractSmartColumn<String> {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Country");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }

      @Override
      protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
        return CountryLookupCall.class;
      }
    }

    @Order(7)
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

    @Order(8)
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

    @Order(1)
    public class EditMenu extends AbstractEditMenu {

      @Override
      protected void execAction() {
        final EventForm form = new EventForm();
        form.setEventId(getEventIdColumn().getSelectedValue());
        form.addFormListener(new FormListener() {

          @Override
          public void formChanged(FormEvent e) {
            if (FormEvent.TYPE_CLOSED == e.getType() && form.isFormStored()) {
              reloadPage();
            }
          }
        });

        form.startModify();
      }
    }

    @Order(2)
    public class NewMenu extends AbstractNewMenu {

      @Override
      protected void execAction() {
        final EventForm form = new EventForm();
        form.addFormListener(new FormListener() {

          @Override
          public void formChanged(FormEvent e) {
            if (FormEvent.TYPE_CLOSED == e.getType() && form.isFormStored()) {
              reloadPage();
            }
          }
        });

        form.startNew();
      }
    }
  }

  @FormData
  public String getOrganizationId() {
    return organizationId;
  }

  @FormData
  public void setOrganizationId(String organizationId) {
    this.organizationId = organizationId;
  }
}
