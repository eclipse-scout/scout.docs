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
package org.eclipse.scout.contacts.client.person;

import java.util.Set;

import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.client.common.CountryLookupCall;
import org.eclipse.scout.contacts.shared.organization.OrganizationLookupCall;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.contacts.shared.person.PersonTablePageData;
import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

// tag::PageInit[]
// tag::structure[]
// tag::linkToOrganization[]
@PageData(PersonTablePageData.class)
public class PersonTablePage extends AbstractPageWithTable<PersonTablePage.Table> {
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
  // tag::PageInit[]
  public class Table extends AbstractTable {
    // end::linkToOrganization[]
    // end::organizationColumn[]
    // end::structure[]
    // container class to hold columns and other elements for this table page <4>
    // end::PageInit[]

    public LastNameColumn getLastNameColumn() {
      return getColumnSet().getColumnByClass(LastNameColumn.class);
    }

    @Override
    protected String getConfiguredDefaultIconId() {
      return Icons.Person;
    }
    //tag::menu[]

    @Override
    protected Class<? extends IMenu> getConfiguredDefaultMenu() { // <1>
      return EditMenu.class;
    }
    //end::menu[]

    public CountryColumn getCountryColumn() {
      return getColumnSet().getColumnByClass(CountryColumn.class);
    }

    public EmailColumn getEmailColumn() {
      return getColumnSet().getColumnByClass(PersonTablePage.Table.EmailColumn.class);
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

    public PersonIdColumn getPersonIdColumn() {
      return getColumnSet().getColumnByClass(PersonIdColumn.class);
    }
    // tag::menu[]

    @Order(10)
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

    //tag::PersonIdColumn[]
    @Order(1)
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
    return AbstractIcons.Person;
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
