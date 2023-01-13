/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.client.person;

import java.util.Set;

import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.client.common.CountryLookupCall;
import org.eclipse.scout.contacts.client.person.PersonTablePage.Table;
import org.eclipse.scout.contacts.shared.organization.OrganizationLookupCall;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.contacts.shared.person.PersonTablePageData;
import org.eclipse.scout.rt.client.dto.ColumnData;
import org.eclipse.scout.rt.client.dto.ColumnData.SdkColumnCommand;
import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

// tag::PageInit[]
// tag::structure[]
// tag::linkToOrganization[]
@PageData(PersonTablePageData.class)
@ClassId("23c10251-66b1-4bd6-a9d7-93c7d1aedede")
public class PersonTablePage extends AbstractPageWithTable<Table> {
  // end::structure[]
  // end::PageInit[]

  private String organizationId; // <1>

  public String getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(String organizationId) {
    this.organizationId = organizationId;
  }
  // end::linkToOrganization[]
  // tag::PageInit[]

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Persons"); // <1>
  }
  // tag::linkToOrganization[]

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IPersonService.class)
        .getPersonTableData(filter, getOrganizationId())); // <2>
  }
  // end::linkToOrganization[]

  @Override // <3>
  protected boolean getConfiguredLeaf() {
    return true;
  }
  // tag::structure[]
  // tag::linkToOrganization[]

  // tag::organizationColumn[]
  @ClassId("3fa1374b-9635-441b-b2f8-feb24b50740a")
  public class Table extends AbstractTable {
    // end::linkToOrganization[]
    // end::organizationColumn[]
    // end::structure[]
    // container class to hold columns and other elements for this table page <4>
    // end::PageInit[]

    //tag::menu[]

    @Override
    protected Class<? extends IMenu> getConfiguredDefaultMenu() { // <1>
      return EditMenu.class;
    }
    //end::menu[]

    public SummaryColumn getSummaryColumn() {
      return getColumnSet().getColumnByClass(SummaryColumn.class);
    }

    public CountryColumn getCountryColumn() {
      return getColumnSet().getColumnByClass(CountryColumn.class);
    }

    public EmailColumn getEmailColumn() {
      return getColumnSet().getColumnByClass(EmailColumn.class);
    }

    public OrganizationColumn getOrganizationColumn() {
      return getColumnSet().getColumnByClass(OrganizationColumn.class);
    }

    public CityColumn getCityColumn() {
      return getColumnSet().getColumnByClass(CityColumn.class);
    }

    public FirstNameColumn getFirstNameColumn() {
      return getColumnSet().getColumnByClass(FirstNameColumn.class);
    }

    public LastNameColumn getLastNameColumn() {
      return getColumnSet().getColumnByClass(LastNameColumn.class);
    }

    public PersonIdColumn getPersonIdColumn() {
      return getColumnSet().getColumnByClass(PersonIdColumn.class);
    }
    // tag::menu[]

    @Order(10)
    @ClassId("4a8f5e0e-6eb8-4296-8ad7-012151f572f2")
    public class EditMenu extends AbstractMenu {
      // end::menu[]

      @Override
      protected String getConfiguredIconId() {
        return Icons.Pencil;
      }

      @Override
      protected String getConfiguredKeyStroke() {
        return "alt-e";
      }

      // tag::menu[]
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Edit");
      }

      @Override
      protected void execAction() {
        PersonForm form = new PersonForm();
        form.setPersonId(getPersonIdColumn().getSelectedValue()); // <2>
        form.addFormListener(new PersonFormListener());
        // start the form using its modify handler
        form.startModify();
      }
    }
    // tag::linkToOrganization[]

    @Order(20)
    @ClassId("8ac358f2-de17-4b2b-93f3-73e21a7415d8")
    public class NewMenu extends AbstractMenu {
      // end::menu[]
      // end::linkToOrganization[]

      @Override
      protected String getConfiguredKeyStroke() {
        return "alt-n";
      }

      @Override
      protected String getConfiguredIconId() {
        // get unicode from http://fontawesome.io/icon/magic/
        return "font:awesomeIcons \uf0d0";
      }
      // tag::menu[]

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("New");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() { // <3>
        return CollectionUtility.<IMenuType> hashSet(
            TableMenuType.EmptySpace, TableMenuType.SingleSelection);
      }
      // tag::linkToOrganization[]

      @Override
      protected void execAction() {
        PersonForm form = new PersonForm();
        // end::menu[]
        form.getOrganizationField().setValue(getOrganizationId()); // <3>
        // tag::menu[]
        form.addFormListener(new PersonFormListener());
        // start the form using its new handler
        form.startNew();
      }
    }
    // end::linkToOrganization[]

    private class PersonFormListener implements FormListener {

      @Override
      public void formChanged(FormEvent e) {
        // reload page to reflect new/changed data after saving any changes
        if (FormEvent.TYPE_CLOSED == e.getType() && e.getForm().isFormStored()) {
          reloadPage();
        }
      }
    }
    // end::menu[]

    // Custom summary column (relevant for mobile mode where the child nodes are visible)
    @Order(0)
    @ColumnData(SdkColumnCommand.IGNORE)
    @ClassId("c5b46c95-c355-4098-85bc-badc5f5557c8")
    public class SummaryColumn extends AbstractStringColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredSummary() {
        return true;
      }

      @Override
      protected void execDecorateCell(Cell cell, ITableRow row) {
        cell.setText(StringUtility.join(" ",
            getFirstNameColumn().getValue(row),
            getLastNameColumn().getValue(row),
            StringUtility.box("(", getCityColumn().getValue(row), ")")));
      }
    }

    //tag::PersonIdColumn[]
    @Order(1)
    @ClassId("1cbc9059-caef-4684-b013-bfa3bc0d0642")
    public class PersonIdColumn extends AbstractStringColumn {

      @Override // <1>
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override // <2>
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }
    }
    //end::PersonIdColumn[]

    //tag::FirstNameColumn[]
    @Order(2)
    @ClassId("99df594a-6731-4757-a799-aacdbb4788d3")
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
    //end::FirstNameColumn[]

    @Order(3)
    @ClassId("47703dea-ceed-4b56-b4ac-ff5028ac9ab3")
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

    @Order(4)
    @ClassId("6b463dfb-a837-402e-8ac9-d66d8e521f06")
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

    //tag::CountryColumn[]
    @Order(5)
    @ClassId("a39ad408-b5e5-4794-b86a-ddc13025862e")
    public class CountryColumn extends AbstractSmartColumn<String> {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Country");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }

      @Override // <1>
      protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
        return CountryLookupCall.class;
      }
    }
    //end::CountryColumn[]

    //tag::PhoneColumn[]
    @Order(6)
    @ClassId("fa879506-d38c-46a6-990c-1f1ae4b74d4e")
    public class PhoneColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Phone");
      }

      @Override // <1>
      protected boolean getConfiguredVisible() {
        return false;
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }
    //end::PhoneColumn[]

    @Order(7)
    @ClassId("eff4fe61-2718-401a-b515-ea83803fe548")
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

    @Order(8)
    @ClassId("dbe50fde-1777-4ff2-8ab7-ebe6cd6887eb")
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
    // tag::organizationColumn[]

    @Order(9)
    @ClassId("2e53e50e-5bd5-421e-8bca-fc50f27d790b")
    public class OrganizationColumn extends AbstractSmartColumn<String> {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Organization");
      }

      @Override
      protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
        return OrganizationLookupCall.class;
      }
      // end::organizationColumn[]

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
      // tag::organizationColumn[]
    }
    // tag::structure[]
    // tag::PageInit[]
    // tag::linkToOrganization[]
  }
  // end::PageInit[]
  // end::structure[]
  // end::organizationColumn[]
  // end::linkToOrganization[]

  @Override
  protected String getConfiguredIconId() {
    return AbstractIcons.PersonSolid;
  }

  @Override
  protected String getConfiguredOverviewIconId() {
    return Icons.MaleLine;
  }

  @Override
  protected Class<? extends ISearchForm> getConfiguredSearchForm() {
    return PersonSearchForm.class;
  }
  // tag::PageInit[]
  // tag::structure[]
  // tag::linkToOrganization[]
}
// end::PageInit[]
// end::structure[]
// end::linkToOrganization[]
