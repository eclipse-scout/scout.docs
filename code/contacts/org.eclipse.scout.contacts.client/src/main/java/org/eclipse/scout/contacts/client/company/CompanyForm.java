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
package org.eclipse.scout.contacts.client.company;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.CancelButton;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.DetailsBox;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.DetailsBox.CommentsBox;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.DetailsBox.CommentsBox.CommentsField;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.DetailsBox.CompanyDetailsBox;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.DetailsBox.CompanyDetailsBox.AddressBox;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.DetailsBox.CompanyDetailsBox.EmailField;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.DetailsBox.CompanyDetailsBox.PhoneField;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.GeneralBox;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.GeneralBox.HomepageField;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.GeneralBox.LogoBox;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.GeneralBox.NameField;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.GeneralBox.OpenInBrowserButton;
import org.eclipse.scout.contacts.client.company.CompanyForm.MainBox.OkButton;
import org.eclipse.scout.contacts.client.template.AbstractAddressBox;
import org.eclipse.scout.contacts.client.template.AbstractEmailField;
import org.eclipse.scout.contacts.client.template.AbstractPhoneField;
import org.eclipse.scout.contacts.client.template.AbstractPictureBox;
import org.eclipse.scout.contacts.shared.Icons;
import org.eclipse.scout.contacts.shared.company.CompanyFormData;
import org.eclipse.scout.contacts.shared.company.ICompanyService;
import org.eclipse.scout.contacts.shared.company.UpdateCompanyPermission;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriHint;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.TEXTS;

@FormData(value = CompanyFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class CompanyForm extends AbstractForm {

  private String m_companyId;

  public CompanyForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Company");
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

  public CompanyDetailsBox getCompanyDetailsBox() {
    return getFieldByClass(CompanyDetailsBox.class);
  }

  public DetailsBox getDetailsBox() {
    return getFieldByClass(DetailsBox.class);
  }

  public EmailField getEmailField() {
    return getFieldByClass(EmailField.class);
  }

  public GeneralBox getGeneralBox() {
    return getFieldByClass(GeneralBox.class);
  }

  public HomepageField getHomepageField() {
    return getFieldByClass(HomepageField.class);
  }

  public LogoBox getLogoBox() {
    return getFieldByClass(LogoBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public OpenInBrowserButton getOpenInBrowserButton() {
    return getFieldByClass(OpenInBrowserButton.class);
  }

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
          return TEXTS.get("OpenInWebBrowser");
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
          getDesktop().openUri(getHomepageField().getValue(), OpenUriHint.NEW_WINDOW);
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

    @Order(3000.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(4000.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = BEANS.get(ICompanyService.class).load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateCompanyPermission());
    }

    @Override
    protected void execStore() throws ProcessingException {
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = BEANS.get(ICompanyService.class).store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = BEANS.get(ICompanyService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() throws ProcessingException {
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = BEANS.get(ICompanyService.class).create(formData);
    }
  }

  @FormData
  public String getCompanyId() {
    return m_companyId;
  }

  @FormData
  public void setCompanyId(String companyId) {
    m_companyId = companyId;
  }
}
