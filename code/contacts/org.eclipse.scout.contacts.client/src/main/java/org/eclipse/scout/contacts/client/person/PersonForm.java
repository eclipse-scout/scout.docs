/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.client.person;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.contacts.client.common.AbstractDirtyFormHandler;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.CancelButton;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.CommentsBox;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.CommentsBox.CommentsField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.PersonDetailsBox;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.PersonDetailsBox.AddressBox;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.PersonDetailsBox.EmailField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.PersonDetailsBox.MobileField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.PersonDetailsBox.PhoneField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.WorkBox;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.WorkBox.EmailWorkField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.WorkBox.OrganizationField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.WorkBox.PhoneWorkField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.WorkBox.PositionField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.GeneralBox;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.GeneralBox.DateOfBirthField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.GeneralBox.FirstNameField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.GeneralBox.GenderGroup;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.GeneralBox.LastNameField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.GeneralBox.PictureBox;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.OkButton;
import org.eclipse.scout.contacts.client.template.AbstractAddressBox;
import org.eclipse.scout.contacts.client.template.AbstractEmailField;
import org.eclipse.scout.contacts.client.template.AbstractPhoneField;
import org.eclipse.scout.contacts.client.template.AbstractPictureBox;
import org.eclipse.scout.contacts.shared.organization.OrganizationLookupCall;
import org.eclipse.scout.contacts.shared.person.GenderCodeType;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.contacts.shared.person.PersonUpdatePermission;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = PersonFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class PersonForm extends AbstractForm {

  private String m_personId;

  public PersonForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Person");
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return IForm.DISPLAY_HINT_VIEW;
  }

  public void startModify() {
    startInternalExclusive(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public AddressBox getAddressBox() {
    return getFieldByClass(AddressBox.class);
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public CommentsBox getCommentsBox() {
    return getFieldByClass(CommentsBox.class);
  }

  public CommentsField getCommentsField() {
    return getFieldByClass(CommentsField.class);
  }

  public OrganizationField getOrganizationField() {
    return getFieldByClass(OrganizationField.class);
  }

  public PersonDetailsBox getPersonDetailsBox() {
    return getFieldByClass(PersonDetailsBox.class);
  }

  public DateOfBirthField getDateOfBirthField() {
    return getFieldByClass(DateOfBirthField.class);
  }

  public DetailsBox getDetailsBox() {
    return getFieldByClass(DetailsBox.class);
  }

  public EmailField getEmailField() {
    return getFieldByClass(EmailField.class);
  }

  public EmailWorkField getEmailWorkField() {
    return getFieldByClass(EmailWorkField.class);
  }

  public FirstNameField getFirstNameField() {
    return getFieldByClass(FirstNameField.class);
  }

  public GenderGroup getGenderGroup() {
    return getFieldByClass(GenderGroup.class);
  }

  public GeneralBox getGeneralBox() {
    return getFieldByClass(GeneralBox.class);
  }

  public LastNameField getLastNameField() {
    return getFieldByClass(LastNameField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MobileField getMobileField() {
    return getFieldByClass(MobileField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public PhoneField getPhoneField() {
    return getFieldByClass(PhoneField.class);
  }

  public PhoneWorkField getPhoneWorkField() {
    return getFieldByClass(PhoneWorkField.class);
  }

  public PictureBox getPictureBox() {
    return getFieldByClass(PictureBox.class);
  }

  public PositionField getPositionField() {
    return getFieldByClass(PositionField.class);
  }

  public WorkBox getWorkBox() {
    return getFieldByClass(WorkBox.class);
  }

  @Override
  public Object computeExclusiveKey() {
    return getPersonId();
  }

  @Order(1)
  public class MainBox extends AbstractGroupBox {

    @Order(1)
    public class GeneralBox extends AbstractGroupBox {

      @Order(1)
      public class PictureBox extends AbstractPictureBox {
      }

      @Order(2)
      public class FirstNameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FirstName");
        }
      }

      @Order(3)
      public class LastNameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LastName");
        }
      }

      @Order(4)
      public class DateOfBirthField extends AbstractDateField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DateOfBirth");
        }
      }

      @Order(5)
      public class GenderGroup extends AbstractRadioButtonGroup<String> {

        @Override
        protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
          return GenderCodeType.class;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Gender");
        }
      }
    }

    @Order(2)
    public class DetailsBox extends AbstractTabBox {

      @Order(1)
      public class PersonDetailsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Details");
        }

        @Order(1)
        public class AddressBox extends AbstractAddressBox {
        }

        @Order(2)
        public class PhoneField extends AbstractPhoneField {

          @Override
          protected String execValidateValue(String rawValue) {
            String addressCountry = getAddressBox().getCountryField().getValue();

            if (StringUtility.isNullOrEmpty(getCountry()) && StringUtility.hasText(addressCountry)) {
              setCountry(addressCountry);
            }

            return super.execValidateValue(rawValue);
          }
        }

        @Order(3)
        public class MobileField extends AbstractPhoneField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Mobile");
          }

          @Override
          protected String execValidateValue(String rawValue) {
            String addressCountry = getAddressBox().getCountryField().getValue();

            if (StringUtility.isNullOrEmpty(getCountry()) && StringUtility.hasText(addressCountry)) {
              setCountry(addressCountry);
            }

            return super.execValidateValue(rawValue);
          }
        }

        @Order(4)
        public class EmailField extends AbstractEmailField {
        }
      }

      @Order(2)
      public class WorkBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Work");
        }

        @Order(1)
        public class PositionField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Position");
          }
        }

        @Order(2)
        public class OrganizationField extends AbstractSmartField<String> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Organization");
          }

          @Override
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return OrganizationLookupCall.class;
          }
        }

        @Order(3)
        public class PhoneWorkField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Phone");
          }
        }

        @Order(4)
        public class EmailWorkField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Email");
          }
        }
      }

      @Order(3)
      public class CommentsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Comments");
        }

        @Order(1)
        public class CommentsField extends AbstractStringField {

          @Override
          protected int getConfiguredGridH() {
            return 4;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }
        }
      }
    }

    @Order(100)
    public class OkButton extends AbstractOkButton {
    }

    @Order(101)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractDirtyFormHandler {

    @Override
    protected void execLoad() {
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      formData = BEANS.get(IPersonService.class).load(formData);
      importFormData(formData);
      setEnabledPermission(new PersonUpdatePermission());

      getForm().setSubTitle(calculateSubTitle());
    }

    @Override
    protected void execStore() {
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      formData = BEANS.get(IPersonService.class).store(formData);
    }

    @Override
    protected void execDirtyStatusChanged(boolean dirty) {
      getForm().setSubTitle(calculateSubTitle());
    }

    @Override
    protected boolean getConfiguredOpenExclusive() {
      return true;
    }
  }

  public class NewHandler extends AbstractDirtyFormHandler {

    @Override
    protected void execStore() {
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      formData = BEANS.get(IPersonService.class).create(formData);
    }

    @Override
    protected void execDirtyStatusChanged(boolean dirty) {
      getForm().setSubTitle(calculateSubTitle());
    }
  }

  @Override
  protected boolean execValidate() {
    boolean noFirstName = StringUtility.isNullOrEmpty(getFirstNameField().getValue());
    boolean noLastName = StringUtility.isNullOrEmpty(getLastNameField().getValue());

    if (noFirstName && noLastName) {
      getFirstNameField().requestFocus();

      throw new VetoException(TEXTS.get("MissingName"));
    }

    return true;
  }

  @FormData
  public String getPersonId() {
    return m_personId;
  }

  @FormData
  public void setPersonId(String personId) {
    m_personId = personId;
  }

  private String calculateSubTitle() {
    return StringUtility.join(" ", getFirstNameField().getValue(), getLastNameField().getValue());
  }
}
