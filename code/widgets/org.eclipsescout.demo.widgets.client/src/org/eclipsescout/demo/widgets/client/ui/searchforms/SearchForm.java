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
package org.eclipsescout.demo.widgets.client.ui.searchforms;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPageWithTable;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.doublefield.AbstractDoubleField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipsescout.demo.widgets.client.ClientSession;
import org.eclipsescout.demo.widgets.client.services.lookup.CompanyTypeLookupCall;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.ResetButton;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.SearchButton;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.BooleanField;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.DateBox;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.DateBox.DateFrom;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.DateBox.DateTo;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.DoubleBox;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.DoubleBox.DoubleFrom;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.DoubleBox.DoubleTo;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.IntegerBox;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.IntegerBox.IntegerFrom;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.IntegerBox.IntegerTo;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.LongBox;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.LongBox.LongFrom;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.LongBox.LongTo;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.SmartField;
import org.eclipsescout.demo.widgets.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.StringField;

public class SearchForm extends AbstractSearchForm {

  public SearchForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredIconId() {
    return org.eclipse.scout.rt.shared.AbstractIcons.SmartFieldBrowse;
  }

  @Override
  public void startSearch() throws ProcessingException {
    startInternal(new SearchHandler());
  }

  public BooleanField getBooleanField() {
    return getFieldByClass(BooleanField.class);
  }

  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DateBox getDateBox() {
    return getFieldByClass(DateBox.class);
  }

  public DateFrom getDateFrom() {
    return getFieldByClass(DateFrom.class);
  }

  public DateTo getDateTo() {
    return getFieldByClass(DateTo.class);
  }

  public DoubleBox getDoubleBox() {
    return getFieldByClass(DoubleBox.class);
  }

  public DoubleFrom getDoubleFrom() {
    return getFieldByClass(DoubleFrom.class);
  }

  public DoubleTo getDoubleTo() {
    return getFieldByClass(DoubleTo.class);
  }

  public FieldBox getFieldBox() {
    return getFieldByClass(FieldBox.class);
  }

  public IntegerBox getIntegerBox() {
    return getFieldByClass(IntegerBox.class);
  }

  public IntegerFrom getIntegerFrom() {
    return getFieldByClass(IntegerFrom.class);
  }

  public IntegerTo getIntegerTo() {
    return getFieldByClass(IntegerTo.class);
  }

  public LongBox getLongBox() {
    return getFieldByClass(LongBox.class);
  }

  public LongFrom getLongFrom() {
    return getFieldByClass(LongFrom.class);
  }

  public LongTo getLongTo() {
    return getFieldByClass(LongTo.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ResetButton getResetButton() {
    return getFieldByClass(ResetButton.class);
  }

  public SearchButton getSearchButton() {
    return getFieldByClass(SearchButton.class);
  }

  public SmartField getSmartField() {
    return getFieldByClass(SmartField.class);
  }

  public StringField getStringField() {
    return getFieldByClass(StringField.class);
  }

  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10.0)
    public class TabBox extends AbstractTabBox {

      @Order(10.0)
      public class FieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("searchCriteria");
        }

        @Order(10.0)
        public class SmartField extends AbstractListBox<Long> {

          @Override
          protected int getConfiguredGridH() {
            return 4;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("SmartColumn");
          }

          @Override
          protected Class<? extends LookupCall> getConfiguredLookupCall() {
            return CompanyTypeLookupCall.class;
          }
        }

        @Order(10.0)
        public class StringField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("StringColumn");
          }
        }

        @Order(20.0)
        public class LongBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("LongColumn");
          }

          @Order(10.0)
          public class LongFrom extends AbstractLongField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20.0)
          public class LongTo extends AbstractLongField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(30.0)
        public class IntegerBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IntegerColumn");
          }

          @Order(10.0)
          public class IntegerFrom extends AbstractIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20.0)
          public class IntegerTo extends AbstractIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(40.0)
        public class DoubleBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DoubleColumn");
          }

          @Order(10.0)
          public class DoubleFrom extends AbstractDoubleField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20.0)
          public class DoubleTo extends AbstractDoubleField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(50.0)
        public class DateBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DateColumn");
          }

          @Order(10.0)
          public class DateFrom extends AbstractDateField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20.0)
          public class DateTo extends AbstractDateField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(60.0)
        public class BooleanField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("BooleanColumn");
          }
        }
      }
    }

    @Order(20.0)
    public class CloseButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("CloseButton");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        @SuppressWarnings("unchecked")
        IPageWithTable<ITable> page = (IPageWithTable<ITable>) ClientSession.get().getDesktop().getOutline().getActivePage();
        page.setSearchActive(false);
      }
    }

    @Order(30.0)
    public class ResetButton extends AbstractResetButton {
    }

    @Order(40.0)
    public class SearchButton extends AbstractSearchButton {
    }
  }

  public class SearchHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
    }
  }
}
