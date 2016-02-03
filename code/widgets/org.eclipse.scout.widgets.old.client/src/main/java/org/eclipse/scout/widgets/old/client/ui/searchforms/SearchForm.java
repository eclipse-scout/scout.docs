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
package org.eclipse.scout.widgets.old.client.ui.searchforms;

import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPageWithTable;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.ClientSession;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.ResetButton;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.SearchButton;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.BooleanField;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.DateBox;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.DateBox.DateFrom;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.DateBox.DateTo;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.DoubleBox;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.DoubleBox.BigDecimalFrom;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.DoubleBox.BigDecimalTo;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.IntegerBox;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.IntegerBox.IntegerFrom;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.IntegerBox.IntegerTo;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.LongBox;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.LongBox.LongFrom;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.LongBox.LongTo;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.SmartField;
import org.eclipse.scout.widgets.old.client.ui.searchforms.SearchForm.MainBox.TabBox.FieldBox.StringField;
import org.eclipse.scout.widgets.client.services.lookup.CompanyTypeLookupCall;

public class SearchForm extends AbstractSearchForm {

  public SearchForm() {
    super();
  }

  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public BooleanField getBooleanField() {
    return getFieldByClass(BooleanField.class);
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

  public BigDecimalFrom getBigDecimalFrom() {
    return getFieldByClass(BigDecimalFrom.class);
  }

  public BigDecimalTo getBigDecimalTo() {
    return getFieldByClass(BigDecimalTo.class);
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

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    public class TabBox extends AbstractTabBox {

      @Order(10)
      public class FieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("searchCriteria");
        }

        @Order(10)
        public class StringField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("StringColumn");
          }
        }

        @Order(20)
        public class LongBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("LongColumn");
          }

          @Order(10)
          public class LongFrom extends AbstractLongField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20)
          public class LongTo extends AbstractLongField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(30)
        public class IntegerBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IntegerColumn");
          }

          @Order(10)
          public class IntegerFrom extends AbstractIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20)
          public class IntegerTo extends AbstractIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(40)
        public class DoubleBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("BigDecimalColumn");
          }

          @Order(10)
          public class BigDecimalFrom extends AbstractBigDecimalField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20)
          public class BigDecimalTo extends AbstractBigDecimalField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(50)
        public class DateBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DateColumn");
          }

          @Order(10)
          public class DateFrom extends AbstractDateField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20)
          public class DateTo extends AbstractDateField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(60)
        public class BooleanField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("BooleanColumn");
          }
        }

        @Order(10)
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
          protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return CompanyTypeLookupCall.class;
          }
        }
      }
    }

    @Order(20)
    public class CloseButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("CloseButton");
      }

      @Override
      protected void execClickAction() {
        @SuppressWarnings("unchecked")
        IPageWithTable<ITable> page = (IPageWithTable<ITable>) ClientSession.get().getDesktop().getOutline().getActivePage();
        page.setSearchActive(false);
      }
    }

    @Order(30)
    public class ResetButton extends AbstractResetButton {
      @Override
      protected String getConfiguredKeyStroke() {
        return IKeyStroke.CONTROL + '-' + IKeyStroke.F6;
      }
    }

    @Order(40)
    public class SearchButton extends AbstractSearchButton {
    }
  }

  @Override
  public void start() {
    startInternal(new SearchHandler());
  }

  public class SearchHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
    }
  }
}
