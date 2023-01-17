/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.events.client.person;

import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox;
import org.eclipse.scout.contacts.events.client.Icons;
import org.eclipse.scout.contacts.events.client.person.PersonFormTabExtension.EventBox.EventsField.Table;
import org.eclipse.scout.contacts.events.shared.person.PersonFormTabExtensionData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.extension.ui.form.fields.tabbox.AbstractTabBoxExtension;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@Data(PersonFormTabExtensionData.class)
public class PersonFormTabExtension extends AbstractTabBoxExtension<DetailsBox> {

  public PersonFormTabExtension(DetailsBox owner) {
    super(owner);
  }

  @Order(25)
  @ClassId("aeb4c996-832c-4111-8120-83698a34cf84")
  public class EventBox extends AbstractGroupBox {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Events");
    }

    @Order(1000)
    @ClassId("0f91d144-2533-4d0f-976e-978b830e784f")
    public class EventsField extends AbstractTableField<Table> {

      @Override
      protected int getConfiguredGridH() {
        return 3;
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @ClassId("a98bb7e1-209b-4ac5-846e-73285d8e3df9")
      public class Table extends AbstractTable {

        @Override
        protected String getConfiguredDefaultIconId() {
          return Icons.Event;
        }

        @Override
        protected boolean getConfiguredAutoResizeColumns() {
          return true;
        }

        public IdColumn getIdColumn() {
          return getColumnSet().getColumnByClass(IdColumn.class);
        }

        public TitleColumn getTitleColumn() {
          return getColumnSet().getColumnByClass(TitleColumn.class);
        }

        public StartsColumn getStartsColumn() {
          return getColumnSet().getColumnByClass(StartsColumn.class);
        }

        public CityColumn getCityColumn() {
          return getColumnSet().getColumnByClass(CityColumn.class);
        }

        public CountryColumn getCountryColumn() {
          return getColumnSet().getColumnByClass(CountryColumn.class);
        }

        @Order(1)
        @ClassId("b5a30e59-d142-4170-8956-525bc3ec5d03")
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

        @Order(2)
        @ClassId("f4ab3e32-33fb-4348-8647-62cbfddc0c62")
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
        @ClassId("b4ddca2d-e35a-4081-ac1b-7ff463ab7538")
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

        @Order(4)
        @ClassId("26178748-aa59-435e-a920-1c69524dd4ff")
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

        @Order(5)
        @ClassId("28de793f-6b80-4402-bdc0-a9e333771cda")
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
