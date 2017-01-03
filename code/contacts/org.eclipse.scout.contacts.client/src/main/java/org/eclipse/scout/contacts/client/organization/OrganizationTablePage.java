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
package org.eclipse.scout.contacts.client.organization;

import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.client.common.AbstractEditMenu;
import org.eclipse.scout.contacts.client.common.AbstractNewMenu;
import org.eclipse.scout.contacts.client.common.CountryLookupCall;
import org.eclipse.scout.contacts.shared.organization.IOrganizationService;
import org.eclipse.scout.contacts.shared.organization.OrganizationTablePageData;
import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

// tag::PageInit[]
// tag::childPage[]
@PageData(OrganizationTablePageData.class)
public class OrganizationTablePage extends AbstractPageWithTable<OrganizationTablePage.Table> {
// end::childPage[]

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Organizations"); // <1>
  }
  // end::PageInit[]
  // tag::childPage[]

  @Override
  protected IPage<?> execCreateChildPage(ITableRow row) {
    OrganizationNodePage childPage = new OrganizationNodePage();
    childPage.setOrganizationId(getTable().getOrganizationIdColumn().getValue(row));
    return childPage;
  }
  // end::childPage[]
  // tag::PageInit[]

  // tag::execLoadData[]
  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IOrganizationService.class).getOrganizationTableData(filter));
  }
  // end::execLoadData[]

  public class Table extends AbstractTable {
    // container class to hold columns and other elements for this table page
    //end::PageInit[]

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public CountryColumn getCountryColumn() {
      return getColumnSet().getColumnByClass(CountryColumn.class);
    }

    public HomepageColumn getHomepageColumn() {
      return getColumnSet().getColumnByClass(HomepageColumn.class);
    }

    @Override
    protected String getConfiguredDefaultIconId() {
      return Icons.Organization;
    }

    public CityColumn getCityColumn() {
      return getColumnSet().getColumnByClass(CityColumn.class);
    }

    public OrganizationIdColumn getOrganizationIdColumn() {
      return getColumnSet().getColumnByClass(OrganizationIdColumn.class);
    }

    @Order(1000)
    public class OrganizationIdColumn extends AbstractStringColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }
    }

    @Order(2000)
    public class NameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 250;
      }
    }

    @Order(3000)
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

    @Order(4000)
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

    @Order(5000)
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
        return 200;
      }
    }

    @Order(1000)
    public class EditMenu extends AbstractEditMenu {

      @Override
      protected void execAction() {
        final OrganizationForm form = new OrganizationForm();
        form.setOrganizationId(getOrganizationIdColumn().getSelectedValue());
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

    @Order(2000)
    public class NewMenu extends AbstractNewMenu {

      @Override
      protected void execAction() {
        final OrganizationForm form = new OrganizationForm();
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
    // tag::PageInit[]
  }
  // end::PageInit[]

  @Override
  protected String getConfiguredIconId() {
    return AbstractIcons.World;
  }

  // tag::childPage[]
  // tag::PageInit[]
}
// end::PageInit[]
// end::childPage[]
