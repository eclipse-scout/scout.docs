/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.form.fields.AbstractFormFieldMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.AbstractLocaleLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.LocaleLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.MenuBarForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.MenuBarForm.MainBox.DetailBox;
import org.eclipse.scout.widgets.client.ui.forms.MenuBarForm.MainBox.DetailBox.DateFieldValueField;
import org.eclipse.scout.widgets.client.ui.forms.MenuBarForm.MainBox.DetailBox.SmartFieldMenuValueField;
import org.eclipse.scout.widgets.client.ui.forms.MenuBarForm.MainBox.DetailBox.StringFieldValueField;
import org.eclipse.scout.widgets.shared.Icons;

@ClassId("370a5cbe-29a5-4ad4-942f-b9b033550f76")
public class MenuBarForm extends AbstractForm implements IAdvancedExampleForm {

  @Override
  public void startPageForm() {
    start();
  }

  public DetailBox getDetailBox() {
    return getFieldByClass(DetailBox.class);
  }

  @Override
  public AbstractCloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  @Override
  protected String getConfiguredTitle() {
    return "MenuBar";
  }

  public DateFieldValueField getDateFieldValueField() {
    return getFieldByClass(DateFieldValueField.class);
  }

  public SmartFieldMenuValueField getSmartFieldMenuValueField() {
    return getFieldByClass(SmartFieldMenuValueField.class);
  }

  public StringFieldValueField getStringFieldValueField() {
    return getFieldByClass(StringFieldValueField.class);
  }

  @ClassId("39b5ea3d-043e-4115-8810-f99c1e51b22c")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("f5cc50ce-c9f1-41eb-b697-766e26225915")
    public class DetailBox extends AbstractGroupBox {

      @Order(0)
      @ClassId("d5d0089e-106e-43f1-acf9-bee50c0d6abc")
      public class SmartFieldMenuValueField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return "SmartField Value";
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }
      }

      @Order(1000)
      @ClassId("de65cf39-0682-4767-b75b-ad31a46104ff")
      public class DateFieldValueField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return "DateField value";
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }
      }

      @Order(1000)
      @ClassId("166ebee6-10c8-42b4-8002-c20e43286f41")
      public class StringFieldValueField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return "StringField value";
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }
      }

      @Order(0)
      @ClassId("6d7451e2-5771-4de9-8788-6efb9b7628ac")
      public class ClickMeMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Click me";
        }

        @Override
        protected void execAction() {
          MessageBoxes.createOk().withHeader("Hello!").show();
        }
      }

      @Order(1000)
      @ClassId("da39efa2-8345-4dae-a6af-3b58e3a4c0d9")
      public class SmartFieldMenu extends AbstractFormFieldMenu {

        @Order(1000)
        @ClassId("4a8c6243-a2d2-476b-8b21-d7c1a50d3a19")
        public class MenuSmartField extends AbstractSmartField<Locale> {

          private boolean m_throwVetoException = false;

          @Override
          protected String getConfiguredLabel() {
            return "SmartField in MenuBar";
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execChangedValue() {
            getSmartFieldMenuValueField().setValue(Optional.ofNullable(getValue()).map(Locale::getDisplayName).orElse(null));
            System.out.println("value changed to: " + getValue());
          }

          @Override
          protected Class<? extends ILookupCall<Locale>> getConfiguredLookupCall() {
            return (Class<? extends ILookupCall<Locale>>) /*Remote*/ LocaleLookupCall.class;
          }

          @Override
          protected void execPrepareLookup(ILookupCall<Locale> call) {
            if (call instanceof LocaleLookupCall) { // for some tests the lookup class is changed dynamically
              ((AbstractLocaleLookupCall) call).setThrowVetoException(m_throwVetoException);
            }
          }

          public void setThrowVetoException(boolean throwVetoException) {
            m_throwVetoException = throwVetoException;
          }
        }
      }

      @Order(2000)
      @ClassId("fa7d5d8f-3cf5-469a-aa55-531814f1f420")
      public class DateFieldMenu extends AbstractFormFieldMenu {

        @Order(1000)
        @ClassId("6a5322a3-d789-4ff5-841a-deede4ecea30")
        public class MenuDateField extends AbstractDateField {

          private final SimpleDateFormat m_dateFormatter = new SimpleDateFormat("MM-dd-yyyy hh:mm");

          @Override
          protected String getConfiguredLabel() {
            return "DateField in MenuBar";
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execChangedValue() {
            getDateFieldValueField().setValue(Optional.ofNullable(getValue()).map(m_dateFormatter::format).orElse(null));
            System.out.println("Date changed to: " + getValue());
          }
        }
      }

      @Order(1000)
      @ClassId("a204d787-dc9e-4796-9108-6fbac20fcb9a")
      public class StringFieldMenu extends AbstractFormFieldMenu {

        @Order(1000)
        @ClassId("c91c3652-05a0-4955-ae1c-3b9e9fa65404")
        public class MenuStringField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "StringField in MenuBar";
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredStatusVisible() {
            return false;
          }

          @Override
          protected void execChangedValue() {
            getStringFieldValueField().setValue(getValue());
            System.out.println("String changed to: " + getValue());
          }
        }
      }

      @Order(2000)
      @ClassId("2659e196-3a0d-43dd-87a5-1ccc741a3568")
      public class IconMenu extends AbstractMenu {

        @Override
        protected String getConfiguredIconId() {
          return Icons.World;
        }

        @Override
        protected byte getConfiguredHorizontalAlignment() {
          return HORIZONTAL_ALIGNMENT_RIGHT;
        }

        @Override
        protected boolean getConfiguredStackable() {
          return false;
        }

        @Override
        protected void execAction() {
          MessageBoxes.createOk().withHeader("Hello!").show();
        }
      }
    }

    @Order(50)
    @ClassId("55fbcc51-58be-4aec-b191-8f4464a032a8")
    public class CloseButton extends AbstractCloseButton {
    }
  }
}
