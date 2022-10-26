/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
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
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.CompanyTypeLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.ResetButton;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.SearchButton;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox.FieldBox;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox.FieldBox.BooleanField;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox.FieldBox.DateBox;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox.FieldBox.DateBox.DateFrom;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox.FieldBox.DateBox.DateTo;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox.FieldBox.IntegerBox;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox.FieldBox.IntegerBox.IntegerFrom;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox.FieldBox.IntegerBox.IntegerTo;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox.FieldBox.LongBox;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox.FieldBox.LongBox.LongFrom;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox.FieldBox.LongBox.LongTo;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox.FieldBox.SmartField;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm.MainBox.TabBox.FieldBox.StringField;

@ClassId("ef19288c-40fe-4f5e-a7a7-8526159c1cae")
@FormData(value = SearchFormData.class, sdkCommand = SdkCommand.CREATE)
public class SearchForm extends AbstractSearchForm {

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
  @ClassId("b83e341e-bf7d-497e-b607-755aff391394")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("905de600-eed5-4161-b2ae-a3da916ea3e0")
    public class TabBox extends AbstractTabBox {

      @Order(10)
      @ClassId("eb7a09f3-28f0-4d99-a884-0aee4d85e90f")
      public class FieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("searchCriteria");
        }

        @Order(10)
        @ClassId("ac28944e-b7d3-4b5b-a36a-11f7abeb93b1")
        public class StringField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("StringColumn");
          }
        }

        @Order(20)
        @ClassId("eabb577a-9e85-4e5b-b707-da21b599dccd")
        public class LongBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("LongColumn");
          }

          @Order(10)
          @ClassId("dfddba95-6862-4c52-8740-eb4576f5d94c")
          public class LongFrom extends AbstractLongField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20)
          @ClassId("818b0da9-b4ee-4973-8db0-dbd52e991bd1")
          public class LongTo extends AbstractLongField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(30)
        @ClassId("99df304e-24e1-4d82-b5e4-feb4873ce3e4")
        public class IntegerBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IntegerColumn");
          }

          @Order(10)
          @ClassId("02ab6052-5b62-4c6b-907a-f71b2dfbc36d")
          public class IntegerFrom extends AbstractIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20)
          @ClassId("023b47c0-bde6-4df7-8e59-102171d256e8")
          public class IntegerTo extends AbstractIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(50)
        @ClassId("0d7dc6b5-6514-4679-95d8-b0a1c204fcd1")
        public class DateBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DateColumn");
          }

          @Order(10)
          @ClassId("cddff8f2-71ef-4ecf-8aec-a95c6ff4d37e")
          public class DateFrom extends AbstractDateField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20)
          @ClassId("6426902a-ae05-4c97-bd91-ba6256c5d52c")
          public class DateTo extends AbstractDateField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(60)
        @ClassId("c3b66216-1fab-4fe6-8faf-ff689b3f7722")
        public class BooleanField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("BooleanColumn");
          }

          @Override
          protected boolean getConfiguredTriStateEnabled() {
            return true;
          }

          @Override
          protected void execInitField() {
            setValue(null);
          }
        }

        @Order(10)
        @ClassId("00839a31-351f-44fe-93b2-bbd1234db36d")
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

    @Order(30)
    @ClassId("21b10312-ad41-4c12-9677-7d7f776cc102")
    public class ResetButton extends AbstractResetButton {
    }

    @Order(40)
    @ClassId("4a2c98be-7587-4ee8-8cac-7f75f170d1e9")
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
