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
package org.eclipse.scout.contacts.events.client.person;

import org.eclipse.scout.contacts.events.client.Icons;
import org.eclipse.scout.contacts.client.person.PersonForm;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox;
import org.eclipse.scout.contacts.events.shared.person.PersonFormTabExtensionData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.extension.ui.form.fields.tabbox.AbstractTabBoxExtension;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;

@Data(PersonFormTabExtensionData.class)
public class PersonFormTabExtension extends AbstractTabBoxExtension<PersonForm.MainBox.DetailsBox> {

  public PersonFormTabExtension(DetailsBox owner) {
    super(owner);
  }

  @Order(25)
  public class EventBox extends AbstractGroupBox {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Events");
    }

    @Order(1000)
    public class EventsField extends AbstractTableField<EventsField.Table> {

      @Override
      protected int getConfiguredGridH() {
        return 3;
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      public class Table extends AbstractTable {

        @Override
        protected String getConfiguredDefaultIconId() {
          return Icons.Event;
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
          protected int getConfiguredSortIndex() {
            return 0;
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

        @Order(5)
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
