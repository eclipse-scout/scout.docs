/**
 *
 */
package org.eclipsescout.contacts.client.ui.forms;

import java.net.URL;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagebox.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.client.ui.forms.PersonForm.MainBox.CancelButton;
import org.eclipsescout.contacts.client.ui.forms.PersonForm.MainBox.DetailBox;
import org.eclipsescout.contacts.client.ui.forms.PersonForm.MainBox.DetailBox.CompanyField;
import org.eclipsescout.contacts.client.ui.forms.PersonForm.MainBox.DetailBox.DateOfBirthField;
import org.eclipsescout.contacts.client.ui.forms.PersonForm.MainBox.DetailBox.HeadlineField;
import org.eclipsescout.contacts.client.ui.forms.PersonForm.MainBox.DetailBox.LocationField;
import org.eclipsescout.contacts.client.ui.forms.PersonForm.MainBox.OkButton;
import org.eclipsescout.contacts.client.ui.forms.PersonForm.MainBox.PersonBox;
import org.eclipsescout.contacts.client.ui.forms.PersonForm.MainBox.PersonBox.FirstNameField;
import org.eclipsescout.contacts.client.ui.forms.PersonForm.MainBox.PersonBox.LastNameField;
import org.eclipsescout.contacts.client.ui.forms.PersonForm.MainBox.PersonBox.PictureField;
import org.eclipsescout.contacts.client.ui.forms.PersonForm.MainBox.PersonBox.PictureURLField;
import org.eclipsescout.contacts.client.ui.forms.PersonForm.MainBox.ShowMapButton;
import org.eclipsescout.contacts.shared.services.lookup.CompanyLookupCall;
import org.eclipsescout.contacts.shared.ui.forms.IPersonService;
import org.eclipsescout.contacts.shared.ui.forms.PersonFormData;
import org.eclipsescout.contacts.shared.ui.forms.UpdatePersonPermission;

/**
 * @author mzi
 */
@FormData(value = PersonFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class PersonForm extends AbstractForm {

  private Long m_personNr;
  private String m_personId;

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public PersonForm() throws ProcessingException {
    super();
  }

  /**
   * @return the PersonNr
   */
  @FormData
  public Long getPersonNr() {
    return m_personNr;
  }

  /**
   * @param personNr
   *          the PersonNr to set
   */
  @FormData
  public void setPersonNr(Long personNr) {
    m_personNr = personNr;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Person");
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
   * @return the CancelButton
   */
  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  /**
   * @return the CompanyField
   */
  public CompanyField getCompanyField() {
    return getFieldByClass(CompanyField.class);
  }

  /**
   * @return the DateOfBirthField
   */
  public DateOfBirthField getDateOfBirthField() {
    return getFieldByClass(DateOfBirthField.class);
  }

  /**
   * @return the DetailBox
   */
  public DetailBox getDetailBox() {
    return getFieldByClass(DetailBox.class);
  }

  /**
   * @return the FirstNameField
   */
  public FirstNameField getFirstNameField() {
    return getFieldByClass(FirstNameField.class);
  }

  /**
   * @return the HeadlineField
   */
  public HeadlineField getHeadlineField() {
    return getFieldByClass(HeadlineField.class);
  }

  /**
   * @return the LastNameField
   */
  public LastNameField getLastNameField() {
    return getFieldByClass(LastNameField.class);
  }

  /**
   * @return the LocationField
   */
  public LocationField getLocationField() {
    return getFieldByClass(LocationField.class);
  }

  /**
   * @return the MainBox
   */
  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the OkButton
   */
  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  /**
   * @return the PersonBox
   */
  public PersonBox getPersonBox() {
    return getFieldByClass(PersonBox.class);
  }

  /**
   * @return the PictureField
   */
  public PictureField getPictureField() {
    return getFieldByClass(PictureField.class);
  }

  /**
   * @return the PictureURLField
   */
  public PictureURLField getPictureURLField() {
    return getFieldByClass(PictureURLField.class);
  }

  /**
   * @return the ShowMapButton
   */
  public ShowMapButton getShowMapButton() {
    return getFieldByClass(ShowMapButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class PersonBox extends AbstractGroupBox {

      @Order(10.0)
      public class FirstNameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FirstName");
        }
      }

      @Order(20.0)
      public class LastNameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LastName");
        }
      }

      @Order(30.0)
      public class PictureURLField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("PictureURL");
        }

        @Override
        protected boolean getConfiguredVisible() {
          return true;
        }
      }

      @Order(40.0)
      public class PictureField extends AbstractImageField {

        @Override
        protected boolean getConfiguredFocusable() {
          return true;
        }

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return PersonForm.MainBox.PersonBox.PictureURLField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
          try {
            URL url = new URL((String) newMasterValue);
            setImage(IOUtility.getContent(url.openStream()));
            setAutoFit(true);
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        }

        @Order(10.0)
        public class EditURLMenu extends AbstractExtensibleMenu {

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("EditURL");
          }

          @Override
          protected void execAction() throws ProcessingException {
            PictureURLForm form = new PictureURLForm();
            form.startModify();
            form.waitFor();
            if (form.isFormStored()) {
              getPictureURLField().setValue(form.getPictureURLField().getValue());
            }
          }
        }
      }
    }

    @Order(20.0)
    public class DetailBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Detail");
      }

      @Order(10.0)
      public class HeadlineField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Headline");
        }
      }

      @Order(20.0)
      public class LocationField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Location");
        }
      }

      @Order(30.0)
      public class DateOfBirthField extends AbstractDateField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DateOfBirth");
        }
      }

      @Order(40.0)
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
    }

    @Order(30.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(40.0)
    public class CancelButton extends AbstractCancelButton {
    }

    @Order(50.0)
    public class ShowMapButton extends AbstractLinkButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ShowMap");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        MapForm mapForm = new MapForm();
        mapForm.setAddress(getLocationField().getValue());
        mapForm.startModify();
      }
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      IPersonService service = SERVICES.getService(IPersonService.class);
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdatePersonPermission());
    }

    @Override
    protected void execStore() throws ProcessingException {
      IPersonService service = SERVICES.getService(IPersonService.class);
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      IPersonService service = SERVICES.getService(IPersonService.class);
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);

    }

    @Override
    protected void execStore() throws ProcessingException {
      IPersonService service = SERVICES.getService(IPersonService.class);
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      formData = service.create(formData);

    }
  }

  /**
   * @return the PersonId
   */
  @FormData
  public String getPersonId() {
    return m_personId;
  }

  /**
   * @param personId
   *          the PersonId to set
   */
  @FormData
  public void setPersonId(String personId) {
    m_personId = personId;
  }
}
