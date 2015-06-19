/**
 *
 */
package org.eclipsescout.contacts.client.ui.forms;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
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
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.CancelButton;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox.CommentsBox;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox.CommentsBox.CommentsField;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox.ContactDetailsBox;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox.ContactDetailsBox.AddressBox;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox.ContactDetailsBox.EmailField;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox.ContactDetailsBox.MobileField;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox.ContactDetailsBox.PhoneField;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox.WorkBox;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox.WorkBox.CompanyField;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox.WorkBox.EmailWorkField;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox.WorkBox.PhoneWorkField;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.DetailsBox.WorkBox.PositionField;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.GeneralBox;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.GeneralBox.DateOfBirthField;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.GeneralBox.FirstNameField;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.GeneralBox.GenderGroup;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.GeneralBox.LastNameField;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.GeneralBox.PictureBox;
import org.eclipsescout.contacts.client.ui.forms.ContactForm.MainBox.OkButton;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractAddressBox;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractAddressBox.LocationBox.CityField;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractAddressBox.LocationBox.CountryField;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractAddressBox.ShowOnMapButton;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractAddressBox.StreetField;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractEmailField;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractPhoneField;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractPictureBox;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractPictureBox.PictureField;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractPictureBox.PictureUrlField;
import org.eclipsescout.contacts.shared.ContactFormData;
import org.eclipsescout.contacts.shared.UpdateContactPermission;
import org.eclipsescout.contacts.shared.services.IContactService;
import org.eclipsescout.contacts.shared.services.code.GenderCodeType;
import org.eclipsescout.contacts.shared.services.lookup.CompanyLookupCall;

/**
 * @author mzi
 */
@FormData(value = ContactFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ContactForm extends AbstractForm {

  private String m_contactId;

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public ContactForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Contact");
  }

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  /**
   * @return the AddressBox
   */
  public AddressBox getAddressBox() {
    return getFieldByClass(AddressBox.class);
  }

  /**
   * @return the CancelButton
   */
  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  /**
   * @return the CityField
   */
  public CityField getCityField() {
    return getAddressBox().getCityField();
  }

  /**
   * @return the CommentsBox
   */
  public CommentsBox getCommentsBox() {
    return getFieldByClass(CommentsBox.class);
  }

  /**
   * @return the CommentsField
   */
  public CommentsField getCommentsField() {
    return getFieldByClass(CommentsField.class);
  }

  /**
   * @return the CompanyField
   */
  public CompanyField getCompanyField() {
    return getFieldByClass(CompanyField.class);
  }

  /**
   * @return the CompanyDetailsBox
   */
  public ContactDetailsBox getContactDetailsBox() {
    return getFieldByClass(ContactDetailsBox.class);
  }

  /**
   * @return the CountryField
   */
  public CountryField getCountryField() {
    return getAddressBox().getCountryField();
  }

  /**
   * @return the DateOfBirthField
   */
  public DateOfBirthField getDateOfBirthField() {
    return getFieldByClass(DateOfBirthField.class);
  }

  /**
   * @return the DetailsBox
   */
  public DetailsBox getDetailsBox() {
    return getFieldByClass(DetailsBox.class);
  }

  /**
   * @return the EmailField
   */
  public EmailField getEmailField() {
    return getFieldByClass(EmailField.class);
  }

  /**
   * @return the EmailWorkField
   */
  public EmailWorkField getEmailWorkField() {
    return getFieldByClass(EmailWorkField.class);
  }

  /**
   * @return the FirstNameField
   */
  public FirstNameField getFirstNameField() {
    return getFieldByClass(FirstNameField.class);
  }

  /**
   * @return the GenderGroup
   */
  public GenderGroup getGenderGroup() {
    return getFieldByClass(GenderGroup.class);
  }

  /**
   * @return the GeneralBox
   */
  public GeneralBox getGeneralBox() {
    return getFieldByClass(GeneralBox.class);
  }

  /**
   * @return the LastNameField
   */
  public LastNameField getLastNameField() {
    return getFieldByClass(LastNameField.class);
  }

  /**
   * @return the MainBox
   */
  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the MobileField
   */
  public MobileField getMobileField() {
    return getFieldByClass(MobileField.class);
  }

  /**
   * @return the OkButton
   */
  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  /**
   * @return the PhoneField
   */
  public PhoneField getPhoneField() {
    return getFieldByClass(PhoneField.class);
  }

  /**
   * @return the PhoneWorkField
   */
  public PhoneWorkField getPhoneWorkField() {
    return getFieldByClass(PhoneWorkField.class);
  }

  /**
   * @return the PictureBox
   */
  public PictureBox getPictureBox() {
    return getFieldByClass(PictureBox.class);
  }

  /**
   * @return the PictureField
   * @deprecated Use {@link #getPictureBox()#getPictureField()}
   */
  @Deprecated
  public PictureField getPictureField() {
    return getPictureBox().getPictureField();
  }

  /**
   * @return the PictureUrlField
   * @deprecated Use {@link #getPictureBox()#getPictureUrlField()}
   */
  @Deprecated
  public PictureUrlField getPictureUrlField() {
    return getPictureBox().getPictureUrlField();
  }

  /**
   * @return the PositionField
   */
  public PositionField getPositionField() {
    return getFieldByClass(PositionField.class);
  }

  /**
   * @return the ShowOnMapButton
   * @deprecated Use {@link #getAddressBox()#getShowOnMapButton()}
   */
  @Deprecated
  public ShowOnMapButton getShowOnMapButton() {
    return getAddressBox().getShowOnMapButton();
  }

  /**
   * @return the StreetField
   * @deprecated Use {@link #getAddressBox()#getStreetField()}
   */
  @Deprecated
  public StreetField getStreetField() {
    return getAddressBox().getStreetField();
  }

  /**
   * @return the WorkBox
   */
  public WorkBox getWorkBox() {
    return getFieldByClass(WorkBox.class);
  }

  @Order(1000.0)
  public class MainBox extends AbstractGroupBox {

    @Order(1000.0)
    public class GeneralBox extends AbstractGroupBox {

      @Order(0.0)
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

      @Order(3500.0)
      public class DateOfBirthField extends AbstractDateField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DateOfBirth");
        }
      }

      @Order(4000.0)
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

        @Order(-1000.0)
        public class AddressBox extends AbstractAddressBox {

        }

        @Order(1812.0)
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

        @Order(2000.0)
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

        @Order(3000.0)
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

    @Order(100000.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(101000.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      IContactService service = SERVICES.getService(IContactService.class);
      ContactFormData formData = new ContactFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateContactPermission());

    }

    @Override
    protected void execStore() throws ProcessingException {
      IContactService service = SERVICES.getService(IContactService.class);
      ContactFormData formData = new ContactFormData();
      exportFormData(formData);
      formData = service.store(formData);

    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execStore() throws ProcessingException {
      IContactService service = SERVICES.getService(IContactService.class);
      ContactFormData formData = new ContactFormData();
      exportFormData(formData);
      formData = service.create(formData);

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

  /**
   * @return the ContactId
   */
  @FormData
  public String getContactId() {
    return m_contactId;
  }

  /**
   * @param contactId
   *          the ContactId to set
   */
  @FormData
  public void setContactId(String contactId) {
    m_contactId = contactId;
  }
}
