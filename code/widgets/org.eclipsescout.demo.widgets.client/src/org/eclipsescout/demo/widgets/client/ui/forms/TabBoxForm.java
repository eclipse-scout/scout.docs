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
package org.eclipsescout.demo.widgets.client.ui.forms;

import java.util.Date;
import java.util.TimeZone;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.TimezonesLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.TabBoxForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox;
import org.eclipsescout.demo.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.Tab1Box;
import org.eclipsescout.demo.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.Tab2Box;
import org.eclipsescout.demo.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.Tab2Box.MultilineStringField;
import org.eclipsescout.demo.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.Tab3Box;
import org.eclipsescout.demo.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.Tab3Box.EnabledButton;
import org.eclipsescout.demo.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.Tab3Box.InnerTabBox;
import org.eclipsescout.demo.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.Tab3Box.InnerTabBox.InnerTab1Box;
import org.eclipsescout.demo.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.Tab3Box.InnerTabBox.InnerTab1Box.StringField;
import org.eclipsescout.demo.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.Tab3Box.InnerTabBox.InnerTab2Box;
import org.eclipsescout.demo.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.Tab3Box.InnerTabBox.InnerTab2Box.FirstTabButton;

public class TabBoxForm extends AbstractForm implements IPageForm {

  public TabBoxForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("TabBox");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public EnabledButton getEnabledButton() {
    return getFieldByClass(EnabledButton.class);
  }

  public FirstTabButton getFirstTabButton() {
    return getFieldByClass(FirstTabButton.class);
  }

  public InnerTab1Box getInnerTab1Box() {
    return getFieldByClass(InnerTab1Box.class);
  }

  public InnerTab2Box getInnerTab2Box() {
    return getFieldByClass(InnerTab2Box.class);
  }

  public InnerTabBox getInnerTabBox() {
    return getFieldByClass(InnerTabBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public StringField getStringField() {
    return getFieldByClass(StringField.class);
  }

  public Tab1Box getTab1Box() {
    return getFieldByClass(Tab1Box.class);
  }

  public Tab2Box getTab2Box() {
    return getFieldByClass(Tab2Box.class);
  }

  public Tab3Box getTab3Box() {
    return getFieldByClass(Tab3Box.class);
  }

  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  public MultilineStringField getMultiRowStringField() {
    return getFieldByClass(MultilineStringField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class TabBox extends AbstractTabBox {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Order(10.0)
      public class Tab1Box extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Tab1");
        }

        @Order(10.0)
        public class TimezoneField extends AbstractSmartField<TimeZone> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Timezone");
          }

          @Override
          protected Class<? extends LookupCall> getConfiguredLookupCall() {
            return TimezonesLookupCall.class;
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            if (getValue() != null) {
              int offset = getValue().getRawOffset();
              offset = offset - TimeZone.getDefault().getRawOffset();
              for (IFormField f : getAllFields()) {
                if (f instanceof AbstractDateField) {
                  ((AbstractDateField) f).setValue(new Date(System.currentTimeMillis() + offset));
                }
              }
            }
            else {
              for (IFormField f : getAllFields()) {
                if (f instanceof AbstractDateField) {
                  ((AbstractDateField) f).setValue(new Date());
                }
              }
            }
          }
        }

        @Order(20.0)
        public class DateField extends AbstractDateField {

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DateField");
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(new Date());
          }
        }

        @Order(30.0)
        public class DateTimeField extends AbstractDateTimeField {

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DateTimeField");
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(new Date());
          }
        }

        @Order(40.0)
        public class TimeField extends AbstractTimeField {

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("TimeField");
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(new Date());
          }
        }
      }

      @Order(20.0)
      public class Tab2Box extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Tab2");
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10.0)
        public class MultilineStringField extends AbstractStringField {

          @Override
          protected int getConfiguredGridH() {
            return 4;
          }

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }

          @Override
          protected boolean getConfiguredWrapText() {
            return true;
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(TEXTS.get("Lorem"));
          }
        }
      }

      @Order(30.0)
      public class Tab3Box extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Tab3");
        }

        @Order(10.0)
        public class InnerTabBox extends AbstractTabBox {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Order(10.0)
          public class InnerTab1Box extends AbstractGroupBox {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Tab1");
            }

            @Order(10.0)
            public class StringField extends AbstractStringField {

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("StringField");
              }
            }
          }

          @Order(20.0)
          public class InnerTab2Box extends AbstractGroupBox {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Tab2");
            }

            @Order(10.0)
            public class FirstTabButton extends AbstractLinkButton {

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("FirstTab");
              }

              @Override
              protected void execClickAction() throws ProcessingException {
                getInnerTabBox().setSelectedTab(getInnerTab1Box());
              }
            }
          }
        }

        @Order(20.0)
        public class EnabledButton extends AbstractButton {

          @Override
          protected int getConfiguredDisplayStyle() {
            return DISPLAY_STYLE_TOGGLE;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Enabled");
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            getInnerTabBox().setEnabled(isSelected());
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setSelected(true);
          }
        }
      }
    }

    @Order(20.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
