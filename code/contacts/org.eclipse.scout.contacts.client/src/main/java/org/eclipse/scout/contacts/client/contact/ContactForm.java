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
package org.eclipse.scout.contacts.client.contact;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.CancelButton;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.DetailsBox;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.DetailsBox.CommentsBox;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.DetailsBox.CommentsBox.CommentsField;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.DetailsBox.ContactDetailsBox;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.DetailsBox.ContactDetailsBox.AddressBox;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.DetailsBox.ContactDetailsBox.EmailField;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.DetailsBox.ContactDetailsBox.MobileField;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.DetailsBox.ContactDetailsBox.PhoneField;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.DetailsBox.WorkBox;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.DetailsBox.WorkBox.CompanyField;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.DetailsBox.WorkBox.EmailWorkField;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.DetailsBox.WorkBox.PhoneWorkField;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.DetailsBox.WorkBox.PositionField;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.GeneralBox;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.GeneralBox.DateOfBirthField;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.GeneralBox.FirstNameField;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.GeneralBox.GenderGroup;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.GeneralBox.LastNameField;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.GeneralBox.PictureBox;
import org.eclipse.scout.contacts.client.contact.ContactForm.MainBox.OkButton;
import org.eclipse.scout.contacts.client.template.AbstractAddressBox;
import org.eclipse.scout.contacts.client.template.AbstractEmailField;
import org.eclipse.scout.contacts.client.template.AbstractPhoneField;
import org.eclipse.scout.contacts.client.template.AbstractPictureBox;
import org.eclipse.scout.contacts.shared.company.CompanyLookupCall;
import org.eclipse.scout.contacts.shared.contact.ContactFormData;
import org.eclipse.scout.contacts.shared.contact.GenderCodeType;
import org.eclipse.scout.contacts.shared.contact.IContactService;
import org.eclipse.scout.contacts.shared.contact.UpdateContactPermission;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
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

@FormData(value = ContactFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ContactForm extends AbstractForm {

  private String m_contactId;

  public ContactForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Contact");
  }

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  public void startNew() throws ProcessingException {
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

  public CompanyField getCompanyField() {
    return getFieldByClass(CompanyField.class);
  }

  public ContactDetailsBox getContactDetailsBox() {
    return getFieldByClass(ContactDetailsBox.class);
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

  @Order(1000.0)
  public class MainBox extends AbstractGroupBox {

    @Order(1000.0)
    public class GeneralBox extends AbstractGroupBox {

      @Order(1000.0)
      public class PictureBox extends AbstractPictureBox {
      }

      @Order(2000.0)
      public class FirstNameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FirstName");
        }
      }

      @Order(3000.0)
      public class LastNameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LastName");
        }
      }

      @Order(4000.0)
      public class DateOfBirthField extends AbstractDateField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DateOfBirth");
        }
      }

      @Order(5000.0)
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

    @Order(2000.0)
    public class DetailsBox extends AbstractTabBox {

      @Order(1000.0)
      public class ContactDetailsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Details");
        }

        @Order(1000.0)
        public class AddressBox extends AbstractAddressBox {
        }

        @Order(2000.0)
        public class PhoneField extends AbstractPhoneField {

          @Override
          protected String execValidateValue(String rawValue) throws ProcessingException {
            String addressCountry = getAddressBox().getCountryField().getValue();

            if (StringUtility.isNullOrEmpty(getCountry()) && StringUtility.hasText(addressCountry)) {
              setCountry(addressCountry);
            }

            return super.execValidateValue(rawValue);
          }
        }

        @Order(3000.0)
        public class MobileField extends AbstractPhoneField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Mobile");
          }

          @Override
          protected String execValidateValue(String rawValue) throws ProcessingException {
            String addressCountry = getAddressBox().getCountryField().getValue();

            if (StringUtility.isNullOrEmpty(getCountry()) && StringUtility.hasText(addressCountry)) {
              setCountry(addressCountry);
            }

            return super.execValidateValue(rawValue);
          }
        }

        @Order(4000.0)
        public class EmailField extends AbstractEmailField {
        }
      }

      @Order(2000.0)
      public class WorkBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Work");
        }

        @Order(1000.0)
        public class PositionField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Position");
          }
        }

        @Order(2000.0)
        public class CompanyField extends AbstractSmartField<String> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Company");
          }

          @Override
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return CompanyLookupCall.class;
          }
        }

        @Order(3000.0)
        public class PhoneWorkField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Phone");
          }
        }

        @Order(4000.0)
        public class EmailWorkField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Email");
          }
        }
      }

      @Order(3000.0)
      public class CommentsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Comments");
        }

        @Order(1000.0)
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

    @Order(4000.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(5000.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      ContactFormData formData = new ContactFormData();
      exportFormData(formData);
      formData = BEANS.get(IContactService.class).load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateContactPermission());
    }

    @Override
    protected void execStore() throws ProcessingException {
      ContactFormData formData = new ContactFormData();
      exportFormData(formData);
      formData = BEANS.get(IContactService.class).store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execStore() throws ProcessingException {
      ContactFormData formData = new ContactFormData();
      exportFormData(formData);
      formData = BEANS.get(IContactService.class).create(formData);
    }
  }

  @Override
  protected boolean execValidate() throws ProcessingException {
    boolean noFirstName = StringUtility.isNullOrEmpty(getFirstNameField().getValue());
    boolean noLastName = StringUtility.isNullOrEmpty(getLastNameField().getValue());

    if (noFirstName && noLastName) {
      getFirstNameField().requestFocus();

      throw new ProcessingException(TEXTS.get("MissingName"));
    }

    return true;
  }

  @FormData
  public String getContactId() {
    return m_contactId;
  }

  @FormData
  public void setContactId(String contactId) {
    m_contactId = contactId;
  }
}
