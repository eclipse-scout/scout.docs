/**
 *
 */
package org.eclipsescout.contacts.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.shell.IShellService;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.CancelButton;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.DetailsBox;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.DetailsBox.CommentsBox;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.DetailsBox.CommentsBox.CommentsField;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.DetailsBox.CompanyDetailsBox;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.DetailsBox.CompanyDetailsBox.AddressBox;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.DetailsBox.CompanyDetailsBox.EmailField;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.DetailsBox.CompanyDetailsBox.PhoneField;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.GeneralBox;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.GeneralBox.HomepageField;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.GeneralBox.LogoBox;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.GeneralBox.NameField;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.GeneralBox.OpenInBrowserButton;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.OkButton;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractAddressBox;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractEmailField;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractPhoneField;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractPictureBox;
import org.eclipsescout.contacts.shared.CompanyFormData;
import org.eclipsescout.contacts.shared.Icons;
import org.eclipsescout.contacts.shared.UpdateCompanyPermission;
import org.eclipsescout.contacts.shared.services.ICompanyService;

/**
 * @author mzi
 */
@FormData(value = CompanyFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class CompanyForm extends AbstractForm {

  private String m_companyId;

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public CompanyForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Company");
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
   * @return the CompanyDetailsBox
   */
  public CompanyDetailsBox getCompanyDetailsBox() {
    return getFieldByClass(CompanyDetailsBox.class);
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
   * @return the GeneralBox
   */
  public GeneralBox getGeneralBox() {
    return getFieldByClass(GeneralBox.class);
  }

  /**
   * @return the HomepageField
   */
  public HomepageField getHomepageField() {
    return getFieldByClass(HomepageField.class);
  }

  /**
   * @return the LogoBox
   */
  public LogoBox getLogoBox() {
    return getFieldByClass(LogoBox.class);
  }

  /**
   * @return the MainBox
   */
  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the NameField
   */
  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  /**
   * @return the OkButton
   */
  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  /**
   * @return the OpenInBrowserButton
   */
  public OpenInBrowserButton getOpenInBrowserButton() {
    return getFieldByClass(OpenInBrowserButton.class);
  }

  /**
   * @return the PhoneField
   */
  public PhoneField getPhoneField() {
    return getFieldByClass(PhoneField.class);
  }

  @Order(1000.0)
  public class MainBox extends AbstractGroupBox {

    @Order(1000.0)
    public class GeneralBox extends AbstractGroupBox {

      @Order(1000.0)
      public class LogoBox extends AbstractPictureBox {

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected void execInitField() throws ProcessingException {
          getPictureField().setImageId(Icons.House);
        }
      }

      @Order(2000.0)
      public class NameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(3000.0)
      public class HomepageField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Homepage");
        }
      }

      @Order(4000.0)
      public class OpenInBrowserButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("OpenInBrowser");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return CompanyForm.MainBox.GeneralBox.HomepageField.class;
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
        protected void execClickAction() throws ProcessingException {
          SERVICES.getService(IShellService.class).shellOpen(getHomepageField().getValue());
        }
      }
    }

    @Order(2000.0)
    public class DetailsBox extends AbstractTabBox {

      @Order(1000.0)
      public class CompanyDetailsBox extends AbstractGroupBox {

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
          protected String getConfiguredLabel() {
            return TEXTS.get("Phone");
          }
        }

        @Order(3000.0)
        public class EmailField extends AbstractEmailField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Email");
          }
        }
      }

      @Order(2000.0)
      public class CommentsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Comments");
        }

        @Order(1000.0)
        public class CommentsField extends AbstractStringField {

          @Override
          protected int getConfiguredGridH() {
            return 3;
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
      ICompanyService service = SERVICES.getService(ICompanyService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateCompanyPermission());

    }

    @Override
    protected void execStore() throws ProcessingException {
      ICompanyService service = SERVICES.getService(ICompanyService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.store(formData);

    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      ICompanyService service = SERVICES.getService(ICompanyService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);

    }

    @Override
    protected void execStore() throws ProcessingException {
      ICompanyService service = SERVICES.getService(ICompanyService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.create(formData);

    }
  }

  /**
   * @return the CompanyId
   */
  @FormData
  public String getCompanyId() {
    return m_companyId;
  }

  /**
   * @param companyId
   *          the CompanyId to set
   */
  @FormData
  public void setCompanyId(String companyId) {
    m_companyId = companyId;
  }
}
