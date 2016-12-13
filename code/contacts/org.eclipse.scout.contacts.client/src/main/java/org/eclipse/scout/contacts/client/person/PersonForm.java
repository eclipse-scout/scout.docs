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
package org.eclipse.scout.contacts.client.person;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.regex.Pattern;

import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.client.common.AbstractDirtyFormHandler;
import org.eclipse.scout.contacts.client.common.CountryLookupCall;
import org.eclipse.scout.contacts.client.common.MapForm;
import org.eclipse.scout.contacts.client.common.PictureUrlForm;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.CancelButton;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.ContactInfoBox;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.ContactInfoBox.AddressBox;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.ContactInfoBox.AddressBox.LocationBox.CityField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.ContactInfoBox.AddressBox.LocationBox.CountryField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.ContactInfoBox.EmailField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.ContactInfoBox.MobileField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.ContactInfoBox.PhoneField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.NotesBox;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.DetailsBox.NotesBox.NotesField;
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
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.GeneralBox.PictureField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.GeneralBox.PictureUrlField;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.OkButton;
import org.eclipse.scout.contacts.shared.organization.OrganizationLookupCall;
import org.eclipse.scout.contacts.shared.person.GenderCodeType;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.util.CompareUtility;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

// tag::init[]
@FormData(value = PersonFormData.class, sdkCommand = FormData.SdkCommand.CREATE) // <1>
// tag::structure[]
// tag::validate[]
public class PersonForm extends AbstractForm {

  // end::validate[]
  // end::structure[]
  // represents the person's primary key
  private String personId;

  @FormData // <2>
  public String getPersonId() {
    return personId;
  }

  @FormData // <2>
  public void setPersonId(String personId) {
    this.personId = personId;
  }

  @Override
  public Object computeExclusiveKey() { // <3>
    return getPersonId();
  }

  @Override
  protected int getConfiguredDisplayHint() { // <4>
    return IForm.DISPLAY_HINT_VIEW;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Person");
  }
  //end::init[]

  public void startModify() {
    startInternalExclusive(new ModifyDirtyHandler());
  }

  public void startNew() {
    startInternal(new NewDirtyHandler());
  }

  public AddressBox getAddressBox() {
    return getFieldByClass(AddressBox.class);
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public PictureUrlField getPictureUrlField() {
    return getFieldByClass(PictureUrlField.class);
  }

  public NotesBox getNotesBox() {
    return getFieldByClass(NotesBox.class);
  }

  public NotesField getNotesField() {
    return getFieldByClass(NotesField.class);
  }

  public OrganizationField getOrganizationField() {
    return getFieldByClass(OrganizationField.class);
  }

  public ContactInfoBox getPersonDetailsBox() {
    return getFieldByClass(ContactInfoBox.class);
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

  public PictureField getPictureField() {
    return getFieldByClass(PictureField.class);
  }

  public PositionField getPositionField() {
    return getFieldByClass(PositionField.class);
  }

  public WorkBox getWorkBox() {
    return getFieldByClass(WorkBox.class);
  }

  //tag::layout[]
  @Order(10)
  public class MainBox extends AbstractGroupBox { // <1>

    @Order(10)
    public class GeneralBox extends AbstractGroupBox { // <2>
      //end::layout[]

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("General");
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      // in a real world scenario avoid copy&paste: delete the pictureUrlField and let PictureField extend AbstractUrlImageField
      // tag::pictureField[]

      @Order(10)
      public class PictureUrlField extends AbstractStringField {

        @Override // <1>
        protected boolean getConfiguredVisible() {
          return false;
        }
      }

      @Order(20)
      public class PictureField extends AbstractImageField {

        @Override // <2>
        protected Class<PictureUrlField> getConfiguredMasterField() {
          return PictureUrlField.class;
        }

        @Override // <3>
        protected void execChangedMasterValue(Object newMasterValue) {
          updateImage((String) newMasterValue);
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }
        // end::pictureField[]

        @Override
        protected String getConfiguredImageId() {
          return Icons.Person;
        }

        @Order(10)
        public class EditURLMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("EditURL");
          }

          @Override
          protected void execAction() {
            String oldUrl = getPictureUrlField().getValue();
            PictureUrlForm form = new PictureUrlForm();

            if (StringUtility.hasText(oldUrl)) {
              form.getUrlField().setValue(oldUrl);
            }

            form.startModify();
            form.waitFor();

            if (form.isFormStored()) {
              getPictureUrlField().setValue(form.getUrlField().getValue());
            }
          }
        }
        // tag::pictureField[]

        protected void updateImage(String url) {
          clearErrorStatus(); // <4>

          if (url == null) {
            setImage(null);
          }
          else {
            try {
              setImage(IOUtility.readFromUrl(new URL((String) url)));
              setAutoFit(true);
            }
            // end::pictureField[]
            catch (MalformedURLException e) {
              addErrorStatus(new Status(TEXTS.get("InvalidImageUrl"), IStatus.WARNING));
            }
            // tag::pictureField[]
            catch (Exception e) { // <5>
              String message = TEXTS.get("FailedToAccessImageFromUrl");
              addErrorStatus(new Status(message, IStatus.WARNING));
            }
          }
        }
      }
      // end::pictureField[]

      // tag::nameFields[]
      @Order(30)
      public class FirstNameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FirstName");
        }
      }

      @Order(40)
      public class LastNameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LastName");
        }
      }
      // end::nameFields[]
      // tag::dateOfBirthField[]

      @Order(50)
      public class DateOfBirthField extends AbstractDateField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DateOfBirth");
        }
        // end::dateOfBirthField[]

        @Override
        protected Date execValidateValue(Date rawValue) {
          if (CompareUtility.compareTo(rawValue, new Date()) > 0) {
            throw new VetoException(TEXTS.get("DateOfBirthCanNotBeInFuture"));
          }

          return super.execValidateValue(rawValue);
        }
        // tag::dateOfBirthField[]
      }
      // end::dateOfBirthField[]
      // tag::genderField[]

      @Order(60)
      public class GenderGroup extends AbstractRadioButtonGroup<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Gender");
        }

        @Override // <1>
        protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
          return GenderCodeType.class;
        }
      }
      // end::genderField[]
      // tag::layout[]
    }

    @Order(20)
    public class DetailsBox extends AbstractTabBox { // <3>

      @Order(10)
      public class ContactInfoBox extends AbstractGroupBox { // <4>

        //end::layout[]
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ContactInfo");
        }

        // tag::layout[]
        // tag::addressBox[]
        // tag::validateAddress[]
        @Order(10)
        public class AddressBox extends AbstractGroupBox {
          // end::layout[]
          // end::validateAddress[]

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Override
          protected int getConfiguredGridH() { // <1>
            return 3;
          }

          @Override
          protected int getConfiguredGridW() { // <1>
            return 1;
          }

          @Override
          protected int getConfiguredGridColumnCount() { // <2>
            return 1;
          }
          //end::addressBox[]

          public StreetField getStreetField() {
            return getFieldByClass(StreetField.class);
          }

          public LocationBox getLocationBox() {
            return getFieldByClass(LocationBox.class);
          }

          public CityField getCityField() {
            return getFieldByClass(CityField.class);
          }

          public CountryField getCountryField() {
            return getFieldByClass(CountryField.class);
          }

          public ShowOnMapButton getShowOnMapButton() {
            return getFieldByClass(ShowOnMapButton.class);
          }
          // tag::validateAddress[]

          // tag::addressBox[]
          @Order(10)
          public class StreetField extends AbstractStringField {
            // end::validateAddress[]

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Street");
            }
            // end::addressBox[]
            // tag::validateAddress[]

            @Override // <1>
            protected void execChangedValue() {
              validateAddressFields(); // <2>
            }
            // tag::addressBox[]
          }

          // end::validateAddress[]
          // use a sequence box for horizontal layout // <3>
          // tag::validateAddress[]
          @Order(20)
          public class LocationBox extends AbstractSequenceBox {
            // end::validateAddress[]

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Location");
            }

            @Override
            protected boolean getConfiguredAutoCheckFromTo() { // <4>
              return false;
            }
            // tag::validateAddress[]

            @Order(10)
            public class CityField extends AbstractStringField {
              // end::validateAddress[]

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("City");
              }

              @Override
              protected int getConfiguredLabelPosition() {
                return LABEL_POSITION_ON_FIELD; // <5>
              }
              // end::addressBox[]
              // tag::validateAddress[]

              @Override
              protected void execChangedValue() {
                validateAddressFields(); // <2>
              }
              // tag::addressBox[]
            }

            @Order(20)
            public class CountryField extends AbstractSmartField<String> {
              // end::validateAddress[]

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("Country");
              }
              // end::addressBox[]
              // tag::validateAddress[]

              @Override
              protected void execChangedValue() {
                validateAddressFields(); // <2>
              }
              // tag::addressBox[]
              // end::validateAddress[]

              @Override
              protected int getConfiguredLabelPosition() {
                return LABEL_POSITION_ON_FIELD;
              }

              @Override
              protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
                return CountryLookupCall.class;
              }
              // tag::validateAddress[]
            }
          }
          // end::validateAddress[]
          // end::addressBox[]

          @Order(30)
          public class ShowOnMapButton extends AbstractLinkButton {

            @Override
            protected int getConfiguredHorizontalAlignment() {
              return 1;
            }

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("ShowOnMap");
            }

            @Override
            protected Class<? extends IValueField> getConfiguredMasterField() {
              return CountryField.class;
            }

            @Override
            protected boolean getConfiguredMasterRequired() {
              return true;
            }

            @Override
            protected boolean getConfiguredProcessButton() {
              return false;
            }

            @Override
            protected void execClickAction() {
              MapForm mapForm = new MapForm();
              mapForm.setStreet(getStreetField().getValue());
              mapForm.setCity(getCityField().getValue());
              mapForm.setCountry(getCountryField().getValue());
              mapForm.startModify();
            }
          }
          // tag::validateAddress[]

          protected void validateAddressFields() {
            boolean hasStreet = StringUtility.hasText(getStreetField().getValue());
            boolean hasCity = StringUtility.hasText(getCityField().getValue());

            getCityField().setMandatory(hasStreet); // <3>
            getCountryField().setMandatory(hasStreet || hasCity);
          }
          // tag::addressBox[]
          // tag::layout[]
        }
        // end::validateAddress[]
        //end::layout[]
        //end::addressBox[]

        @Order(20)
        public class PhoneField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Phone");
          }
        }

        @Order(30)
        public class MobileField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Mobile");
          }
        }

        // tag::email[]
        @Order(40)
        public class EmailField extends AbstractStringField {

          // end::email[]
          // http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
          // tag::email[]
          private static final String EMAIL_PATTERN = // <1>
              "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                  "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Email");
          }

          @Override // <2>
          protected int getConfiguredMaxLength() {
            return 64;
          }

          @Override // <3>
          protected String execValidateValue(String rawValue) {
            if (rawValue != null && !Pattern.matches(EMAIL_PATTERN, rawValue)) {
              throw new VetoException(TEXTS.get("BadEmailAddress")); // <4>
            }

            return rawValue; // <5>
          }
        }
        // end::email[]
        // tag::layout[]
      }

      // tag::organizationField[]
      @Order(20)
      public class WorkBox extends AbstractGroupBox {
        // end::layout[]
        // end::organizationField[]

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Work");
        }

        @Order(10)
        public class PositionField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Position");
          }
        }
        // tag::organizationField[]

        @Order(20)
        public class OrganizationField extends AbstractSmartField<String> { // <1>

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Organization");
          }

          @Override // <2>
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return OrganizationLookupCall.class;
          }
        }
        // end::organizationField[]

        @Order(30)
        public class PhoneWorkField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Phone");
          }
        }

        @Order(40)
        public class EmailWorkField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Email");
          }
        }
        // tag::layout[]
        // tag::organizationField[]
      }
      // end::organizationField[]

      //tag::notes[]
      @Order(30)
      public class NotesBox extends AbstractGroupBox {
        //end::layout[]

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Notes");
        }

        @Order(10)
        public class NotesField extends AbstractStringField {

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
        //tag::layout[]
      }
    }
    //end::notes[]

    @Order(30)
    public class OkButton extends AbstractOkButton {
    }

    @Order(40)
    public class CancelButton extends AbstractCancelButton {
    }
  }
  //end::layout[]

  //These classes (ModifyHandler and NewHandler) are only for documentation, they are not used in the application production:
  //tag::handler[]
  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      IPersonService service = BEANS.get(IPersonService.class); // <1>
      PersonFormData formData = new PersonFormData();
      exportFormData(formData); // <2>
      formData = service.load(formData); // <3>
      importFormData(formData); // <4>

      getForm().setSubTitle(calculateSubTitle()); // <5>
    }

    @Override
    protected void execStore() {
      IPersonService service = BEANS.get(IPersonService.class);
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      service.store(formData); // <6>
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execStore() {
      IPersonService service = BEANS.get(IPersonService.class);
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      service.create(formData); // <7>
    }
  }
  // end::handler[]

  //This modify handler is used in the application:
  public class ModifyDirtyHandler extends AbstractDirtyFormHandler {

    @Override
    protected void execLoad() {
      IPersonService service = BEANS.get(IPersonService.class);
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);

      getForm().setSubTitle(calculateSubTitle());
    }

    @Override
    protected void execStore() {
      IPersonService service = BEANS.get(IPersonService.class);
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      service.store(formData);
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

  //This new handler is used in the application:
  public class NewDirtyHandler extends AbstractDirtyFormHandler {

    @Override
    protected void execStore() {
      IPersonService service = BEANS.get(IPersonService.class);
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      service.create(formData);
    }

    @Override
    protected void execDirtyStatusChanged(boolean dirty) {
      getForm().setSubTitle(calculateSubTitle());
    }
  }

  // tag::validate[]
  @Override // <1>
  protected boolean execValidate() {
    boolean noFirstName = StringUtility.isNullOrEmpty(getFirstNameField().getValue());
    boolean noLastName = StringUtility.isNullOrEmpty(getLastNameField().getValue());

    getGeneralBox().clearErrorStatus(); // <2>

    if (noFirstName && noLastName) {
      getGeneralBox().addErrorStatus(TEXTS.get("MissingName")); // <3>
      getFirstNameField().requestFocus();

      throw new VetoException(TEXTS.get("MissingName")); // <4>
    }

    return true; // <5>
  }
  // end::validate[]
  // tag::handler[]

  private String calculateSubTitle() {
    return StringUtility.join(" ", getFirstNameField().getValue(),
        getLastNameField().getValue());
  }
  // tag::handler[]
  // tag::structure[]
  // tag::init[]
  // tag::validate[]
}
// end::validate[]
// end::structure[]
// tag::init[]
