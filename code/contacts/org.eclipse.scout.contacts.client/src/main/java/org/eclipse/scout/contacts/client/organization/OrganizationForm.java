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
package org.eclipse.scout.contacts.client.organization;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.client.common.AbstractDirtyFormHandler;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.CancelButton;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.DetailsBox;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.DetailsBox.CommentsBox;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.DetailsBox.CommentsBox.CommentsField;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.DetailsBox.OrganizationDetailsBox;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.DetailsBox.OrganizationDetailsBox.AddressBox;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.DetailsBox.OrganizationDetailsBox.EmailField;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.DetailsBox.OrganizationDetailsBox.PhoneField;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.GeneralBox;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.GeneralBox.HomepageField;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.GeneralBox.LogoBox;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.GeneralBox.NameField;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.GeneralBox.OpenInBrowserButton;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.OkButton;
import org.eclipse.scout.contacts.client.template.AbstractAddressBox;
import org.eclipse.scout.contacts.client.template.AbstractEmailField;
import org.eclipse.scout.contacts.client.template.AbstractPhoneField;
import org.eclipse.scout.contacts.client.template.AbstractPictureBox;
import org.eclipse.scout.contacts.shared.organization.IOrganizationService;
import org.eclipse.scout.contacts.shared.organization.OrganizationFormData;
import org.eclipse.scout.contacts.shared.organization.OrganizationUpdatePermission;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.TEXTS;

@FormData(value = OrganizationFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class OrganizationForm extends AbstractForm {

  private String m_organizationId;

  public OrganizationForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Organization");
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

  public OrganizationDetailsBox getOrganizationDetailsBox() {
    return getFieldByClass(OrganizationDetailsBox.class);
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

  @Override
  public Object computeExclusiveKey() {
    return getOrganizationId();
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {

    @Order(1000)
    public class GeneralBox extends AbstractGroupBox {

      @Order(1000)
      public class LogoBox extends AbstractPictureBox {

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected String getConfiguredImageId() {
          return Icons.Organization;
        }
      }

      @Order(2000)
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

      @Order(3000)
      public class HomepageField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Homepage");
        }
      }

      @Order(4000)
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
          return OrganizationForm.MainBox.GeneralBox.HomepageField.class;
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
          getDesktop().openUri(getHomepageField().getValue(), OpenUriAction.NEW_WINDOW);
        }
      }
    }

    @Order(2000)
    public class DetailsBox extends AbstractTabBox {

      @Order(1000)
      public class OrganizationDetailsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Details");
        }

        @Order(1000)
        public class AddressBox extends AbstractAddressBox {
        }

        @Order(2000)
        public class PhoneField extends AbstractPhoneField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Phone");
          }
        }

        @Order(3000)
        public class EmailField extends AbstractEmailField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Email");
          }
        }
      }

      @Order(2000)
      public class CommentsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Comments");
        }

        @Order(1000)
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

    @Order(3000)
    public class OkButton extends AbstractOkButton {
    }

    @Order(4000)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractDirtyFormHandler {

    @Override
    protected void execLoad() {
      OrganizationFormData formData = new OrganizationFormData();
      exportFormData(formData);
      formData = BEANS.get(IOrganizationService.class).load(formData);
      importFormData(formData);
      setEnabledPermission(new OrganizationUpdatePermission());

      getForm().setSubTitle(calculateSubTitle());
    }

    @Override
    protected void execStore() {
      OrganizationFormData formData = new OrganizationFormData();
      exportFormData(formData);
      formData = BEANS.get(IOrganizationService.class).store(formData);
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
    protected void execLoad() {
      OrganizationFormData formData = new OrganizationFormData();
      exportFormData(formData);
      formData = BEANS.get(IOrganizationService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      OrganizationFormData formData = new OrganizationFormData();
      exportFormData(formData);
      formData = BEANS.get(IOrganizationService.class).create(formData);
    }

    @Override
    protected void execDirtyStatusChanged(boolean dirty) {
      getForm().setSubTitle(calculateSubTitle());
    }
  }

  @FormData
  public String getOrganizationId() {
    return m_organizationId;
  }

  @FormData
  public void setOrganizationId(String organizationId) {
    m_organizationId = organizationId;
  }

  private String calculateSubTitle() {
    return getNameField().getValue();
  }
}
