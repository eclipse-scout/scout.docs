/**
 *
 */
package org.eclipsescout.contacts.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.CancelButton;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.CompanyBox;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.CompanyBox.LocationField;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.CompanyBox.NameField;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.CompanyBox.URLField;
import org.eclipsescout.contacts.client.ui.forms.CompanyForm.MainBox.OkButton;
import org.eclipsescout.contacts.shared.ui.forms.CompanyFormData;
import org.eclipsescout.contacts.shared.ui.forms.ICompanyService;
import org.eclipsescout.contacts.shared.ui.forms.UpdateCompanyPermission;

/**
 * @author mzi
 */
@FormData(value = CompanyFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class CompanyForm extends AbstractForm {

  private Long m_companyNr;
  private String m_companyId;

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public CompanyForm() throws ProcessingException {
    super();
  }

  /**
   * @return the CompanyNr
   */
  @FormData
  public Long getCompanyNr() {
    return m_companyNr;
  }

  /**
   * @param companyNr
   *          the CompanyNr to set
   */
  @FormData
  public void setCompanyNr(Long companyNr) {
    m_companyNr = companyNr;
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
   * @return the CancelButton
   */
  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  /**
   * @return the CompanyBox
   */
  public CompanyBox getCompanyBox() {
    return getFieldByClass(CompanyBox.class);
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
   * @return the URLField
   */
  public URLField getURLField() {
    return getFieldByClass(URLField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class CompanyBox extends AbstractGroupBox {

      @Order(10.0)
      public class NameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
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
      public class URLField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("URL");
        }
      }

    }

    @Order(50.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(60.0)
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
