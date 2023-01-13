/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.client.organization;

import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.client.common.AbstractAddressBox;
import org.eclipse.scout.contacts.client.common.AbstractDirtyFormHandler;
import org.eclipse.scout.contacts.client.common.AbstractEmailField;
import org.eclipse.scout.contacts.client.common.AbstractNotesBox;
import org.eclipse.scout.contacts.client.common.AbstractNotesBox.NotesField;
import org.eclipse.scout.contacts.client.common.AbstractUrlImageField;
import org.eclipse.scout.contacts.client.common.ContactsHelper;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.CancelButton;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.DetailsBox;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.DetailsBox.ContactInfoBox;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.DetailsBox.ContactInfoBox.AddressBox;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.DetailsBox.ContactInfoBox.EmailField;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.DetailsBox.ContactInfoBox.PhoneField;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.DetailsBox.NotesBox;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.GeneralBox;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.GeneralBox.HomepageField;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.GeneralBox.NameField;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.GeneralBox.OpenInBrowserButtonBox.OpenInBrowserButton;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.GeneralBox.PictureField;
import org.eclipse.scout.contacts.client.organization.OrganizationForm.MainBox.OkButton;
import org.eclipse.scout.contacts.shared.organization.IOrganizationService;
import org.eclipse.scout.contacts.shared.organization.OrganizationFormData;
import org.eclipse.scout.contacts.shared.organization.UpdateOrganizationPermission;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.collection.OrderedCollection;

@ClassId("584b05a7-dc8d-4411-ad9f-009cfc531672")
@FormData(value = OrganizationFormData.class, sdkCommand = SdkCommand.CREATE)
// tag::layout[]
public class OrganizationForm extends AbstractForm {

  private String organizationId;

  @FormData
  public String getOrganizationId() {
    return organizationId;
  }

  @FormData
  public void setOrganizationId(String organizationId) {
    this.organizationId = organizationId;
  }

  @Override
  public Object computeExclusiveKey() {
    return getOrganizationId();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Organization");
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return IForm.DISPLAY_HINT_VIEW;
  }
  // end::layout[]

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

  public NotesBox getNotesBox() {
    return getFieldByClass(NotesBox.class);
  }

  public NotesField getNotesField() {
    return getNotesBox().getNotesField();
  }

  public ContactInfoBox getOrganizationDetailsBox() {
    return getFieldByClass(ContactInfoBox.class);
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

  public PictureField getLogoField() {
    return getFieldByClass(PictureField.class);
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
  protected void execInitForm() {
    BEANS.get(ContactsHelper.class).handleReadOnly(getOkButton());
  }

  // tag::layout[]
  // tag::refactor[]
  @Order(10)
  @ClassId("e7efc084-fe7a-462f-ba23-914e58f7b82d")
  public class MainBox extends AbstractGroupBox {
    // end::refactor[]
    // end::layout[]

    @Override
    protected void injectMenusInternal(OrderedCollection<IMenu> menus) {
      BEANS.get(ContactsHelper.class).injectReadOnlyMenu(menus);
    }

    // tag::layout[]
    // tag::refactor[]
    @Order(10)
    @ClassId("b20aad47-e070-4f3c-bafc-ddbaa3ae2a4c")
    public class GeneralBox extends AbstractGroupBox {
      // end::refactor[]
      // tag::picture[]

      @Order(10)
      @ClassId("d80625e3-b548-47e4-9cae-42d70aaa568f")
      public class PictureField extends AbstractUrlImageField { // <1>
        // end::picture[]

        @Override
        protected int getConfiguredGridH() { // <2>
          return 4;
        }

        @Override
        protected double getConfiguredGridWeightY() { // <3>
          return 0;
        }
        // end::layout[]

        @Override
        protected String getConfiguredImageId() {
          return Icons.OrganizationLarge;
        }
        // tag::layout[]
        // tag::picture[]
      }
      // end::layout[]

      // additional form field
      // tag::layout[]

      // end::picture[]
      @Order(20)
      @ClassId("4c1a0dea-6c04-4cad-b26b-8d5cc1b786a9")
      public class NameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
        }

        @Override
        protected boolean getConfiguredMandatory() { // <4>
          return true;
        }
      }

      @Order(30)
      @ClassId("68008603-257f-45dc-b8ea-d1e066682205")
      public class HomepageField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Homepage");
        }
      }
      // end::layout[]

      @Order(40)
      @ClassId("ab6cf8e8-ada7-42e0-816c-e0367f4acef0")
      public class OpenInBrowserButtonBox extends AbstractSequenceBox {

        @Order(10)
        @ClassId("b94b724a-4c8a-4fdf-ba96-4cd26da3814d")
        public class OpenInBrowserButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("OpenInWebBrowser");
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.Organization;
          }

          @Override
          protected Class<? extends IValueField> getConfiguredMasterField() {
            return HomepageField.class;
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
      // tag::layout[]
      // tag::refactor[]
    }
    // end::refactor[]

    @Order(20)
    @ClassId("4e48c196-22e4-4e22-965a-5e305af5e6a9")
    public class DetailsBox extends AbstractTabBox {

      @Order(10)
      @ClassId("c6c9e644-2ab3-436e-9d8a-bdcc5482eb5b")
      public class ContactInfoBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ContactInfo");
        }

        @Order(10)
        @ClassId("2a10bd00-de56-4a97-a5b2-6a8a0aae925f")
        public class AddressBox extends AbstractAddressBox { // <5>
        }

        @Order(20)
        @ClassId("504a4845-d307-4238-a2e9-9e785c1477ac")
        public class PhoneField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Phone");
          }
        }

        @Order(30)
        @ClassId("0b4d059d-ec81-4e93-9a99-2512d734ebac")
        public class EmailField extends AbstractEmailField { // <6>
        }
      }

      @Order(20)
      @ClassId("85f4dfb0-f375-4e90-be92-b59e9bc2ebcf")
      public class NotesBox extends AbstractNotesBox { // <7>
      }
    }

    @Order(30)
    @ClassId("97c3ceed-d005-47da-b44d-def4b07f92ab")
    public class OkButton extends AbstractOkButton {
    }

    @Order(40)
    @ClassId("d63bfcd6-7464-4e4f-a07e-eb1173a77f8c")
    public class CancelButton extends AbstractCancelButton {
    }
    // tag::refactor[]
  }
  // end::refactor[]
  // end::layout[]

  public class ModifyHandler extends AbstractDirtyFormHandler {

    @Override
    protected void execLoad() {
      OrganizationFormData formData = new OrganizationFormData();
      exportFormData(formData);
      formData = BEANS.get(IOrganizationService.class).load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateOrganizationPermission());

      getForm().setSubTitle(calculateSubTitle());
    }

    @Override
    protected void execStore() {
      OrganizationFormData formData = new OrganizationFormData();
      exportFormData(formData);
      BEANS.get(IOrganizationService.class).store(formData);
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
      OrganizationFormData formData = new OrganizationFormData();
      exportFormData(formData);
      formData = BEANS.get(IOrganizationService.class).create(formData);
      importFormData(formData);
    }

    @Override
    protected void execDirtyStatusChanged(boolean dirty) {
      getForm().setSubTitle(calculateSubTitle());
    }
  }

  private String calculateSubTitle() {
    return getNameField().getValue();
  }
  // tag::layout[]
}
// end::layout[]
