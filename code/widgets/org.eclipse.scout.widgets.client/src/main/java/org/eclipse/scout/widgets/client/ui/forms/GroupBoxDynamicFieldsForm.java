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

import java.util.List;
import java.util.Optional;

import org.eclipse.scout.rt.client.ui.action.menu.checkbox.AbstractCheckBoxMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.NullFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxDynamicFieldsForm.MainBox.AddFieldBox;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxDynamicFieldsForm.MainBox.AddFieldBox.BeforeField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxDynamicFieldsForm.MainBox.AddFieldBox.CreateFieldButton;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxDynamicFieldsForm.MainBox.AddFieldBox.FieldTypeField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxDynamicFieldsForm.MainBox.AddFieldBox.LabelField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxDynamicFieldsForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxDynamicFieldsForm.MainBox.DetailBox;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxDynamicFieldsForm.MainBox.DetailBox.FirstNameField;
import org.eclipse.scout.widgets.client.ui.forms.groupbox.DynamicTemplateFields;
import org.eclipse.scout.widgets.client.ui.forms.groupbox.SiblingFieldLookupCall;
import org.eclipse.scout.widgets.client.ui.template.formfield.FieldTypeLookupCall;
import org.eclipse.scout.widgets.client.ui.template.formfield.FieldTypeLookupCall.FIELD_TYPE;

/**
 * <h3>{@link GroupBoxDynamicFieldsForm}</h3>
 *
 * @author aho
 */
@ClassId("5a29da8b-d0df-4dc9-8925-4a1e67f369c8")
public class GroupBoxDynamicFieldsForm extends AbstractForm implements IPageForm {
  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("GroupBoxWithDynamicFields");
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public AddFieldBox getAddFieldBox() {
    return getFieldByClass(AddFieldBox.class);
  }

  public FieldTypeField getFieldTypeField() {
    return getFieldByClass(FieldTypeField.class);
  }

  public CreateFieldButton getCreateFieldButton() {
    return getFieldByClass(CreateFieldButton.class);
  }

  public BeforeField getBeforeField() {
    return getFieldByClass(BeforeField.class);
  }

  public LabelField getLabelField() {
    return getFieldByClass(LabelField.class);
  }

  public FirstNameField getFirstNameField() {
    return getFieldByClass(FirstNameField.class);
  }

  public DetailBox getDetailBox() {
    return getFieldByClass(DetailBox.class);
  }

  @Override
  public void startPageForm() {
    startInternal(new NullFormHandler());
  }

  @ClassId("12ab8530-9bb4-4f61-9b7a-42c169413fbe")
  public class MainBox extends AbstractGroupBox {

    @Order(1000)
    @ClassId("48565377-244a-4870-9f38-2c26db811e78")
    public class DetailBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("GroupBox");
      }

      @Order(1000)
      @ClassId("0ce3d72d-6655-461d-9007-6939cf70c599")
      public class EnabledMenu extends AbstractCheckBoxMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Enable");
        }

        @Override
        protected void execInitAction() {
          setSelected(getDetailBox().isEnabled());
        }

        @Override
        protected boolean getConfiguredInheritAccessibility() {
          return false;
        }

        @Override
        protected void execSelectionChanged(boolean selection) {
          getDetailBox().setEnabled(selection);
        }
      }

      @Order(1000)
      @ClassId("03a222ee-6692-4557-a262-7d1d7bfb6f26")
      public class FirstNameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FirstName");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }
      }
    }

    @Order(1500)
    @ClassId("9c38dedc-2a13-492a-98bf-dd3813dff644")
    public class AddFieldBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("AddDynamicField");
      }

      @Order(1000)
      @ClassId("949bc843-4313-4493-af1c-c41023921155")
      public class FieldTypeField extends AbstractSmartField<FIELD_TYPE> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FieldType");
        }

        @Override
        protected Class<? extends ILookupCall<FIELD_TYPE>> getConfiguredLookupCall() {
          return FieldTypeLookupCall.class;
        }

        @Override
        protected void execInitField() {
          setValue(FIELD_TYPE.StringField);
        }
      }

      @Order(2000)
      @ClassId("21c2be08-687d-4178-b9f1-4c4562e5f64d")
      public class LabelField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Label");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }
      }

      @Order(2500)
      @ClassId("9050d0c2-b620-4238-87a2-d8c619c94920")
      public class BeforeField extends AbstractSmartField<IFormField> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Before");
        }

        @Override
        protected Class<? extends ILookupCall<IFormField>> getConfiguredLookupCall() {
          return SiblingFieldLookupCall.class;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<IFormField> call) {
          ((SiblingFieldLookupCall) call).setParent(getDetailBox());
        }
      }

      @Order(3000)
      @ClassId("b3a381c5-4d5a-4aad-967d-e8dd1a0bd31e")
      public class CreateFieldButton extends AbstractButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CreateField");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() {
          IFormField field = DynamicTemplateFields.createField(getFieldTypeField().getValue());
          field.setLabel(Optional.ofNullable(getLabelField().getValue()).orElse(field.getLabel()));
          // order
          List<IFormField> fields = getDetailBox().getFields();
          IFormField sibling = getBeforeField().getValue();
          double order = 0;
          if (sibling != null) {
            int index = fields.indexOf(sibling);
            if (index == 0) {
              order = sibling.getOrder() - 100;
            }
            else if (index < 0) {
              order = -100;
            }
            else {
              order = sibling.getOrder() - (sibling.getOrder() - fields.get(index - 1).getOrder()) / 2.0;
            }
          }
          else {
            order = Optional.ofNullable(fields.size() > 0 ? fields.get(fields.size() - 1) : null).map(f -> f.getOrder() + 100).orElse(0.0);
          }
          field.setOrder(order);
          getDetailBox().addField(field);
        }
      }
    }

    @Order(2000)
    @ClassId("9ad50b0c-9080-4d90-b443-fe8e0b06c0bd")
    public class CloseButton extends AbstractCloseButton {
    }
  }
}
