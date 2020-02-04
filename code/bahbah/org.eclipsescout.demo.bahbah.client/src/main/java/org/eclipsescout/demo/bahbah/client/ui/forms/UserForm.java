/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipsescout.demo.bahbah.client.ui.forms;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipsescout.demo.bahbah.client.ui.forms.UserForm.MainBox.UserBox.CancelButton;
import org.eclipsescout.demo.bahbah.client.ui.forms.UserForm.MainBox.UserBox.OkButton;
import org.eclipsescout.demo.bahbah.client.ui.forms.UserForm.MainBox.UserBox.PasswordField;
import org.eclipsescout.demo.bahbah.client.ui.forms.UserForm.MainBox.UserBox.UserRoleField;
import org.eclipsescout.demo.bahbah.client.ui.forms.UserForm.MainBox.UserBox.UsernameField;
import org.eclipsescout.demo.bahbah.shared.security.CreateUserPermission;
import org.eclipsescout.demo.bahbah.shared.security.UpdateUserPermission;
import org.eclipsescout.demo.bahbah.shared.services.code.UserRoleCodeType;
import org.eclipsescout.demo.bahbah.shared.services.code.UserRoleCodeType.UserCode;
import org.eclipsescout.demo.bahbah.shared.services.process.IUserProcessService;
import org.eclipsescout.demo.bahbah.shared.services.process.UserFormData;
import org.eclipsescout.demo.bahbah.shared.util.SharedUserUtility;

@ClassId("8ceb6e8e-5f97-48c5-a6b7-558b6dc7ed95")
@FormData(value = UserFormData.class, sdkCommand = SdkCommand.CREATE)
public class UserForm extends AbstractForm {

  private Long m_userId;

  @FormData
  public Long getUserId() {
    return m_userId;
  }

  @FormData
  public void setUserId(Long userId) {
    m_userId = userId;
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public void startModify() {
    startInternal(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public PasswordField getPasswordField() {
    return getFieldByClass(PasswordField.class);
  }

  public UserRoleField getUserRoleField() {
    return getFieldByClass(UserRoleField.class);
  }

  public UsernameField getUsernameField() {
    return getFieldByClass(UsernameField.class);
  }

  @Order(10)
  @ClassId("ae6fda96-bf5c-4a74-ad7f-94e5e9e8a2c8")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    @ClassId("c1712ed1-41f8-4ba2-9ddb-d4785f8e81c3")
    public class UserBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("User");
      }

      @Order(10)
      @ClassId("4cf3c671-d368-4f5d-a542-557f5b3cad7f")
      public class UsernameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Username");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected int getConfiguredMaxLength() {
          return SharedUserUtility.MAX_USERNAME_LENGTH;
        }

        @Override
        protected String execValidateValue(String rawValue) {
          SharedUserUtility.checkUsername(rawValue);
          return rawValue;
        }
      }

      @Order(20)
      @ClassId("628401f7-6482-42cb-95e2-b009e31b4021")
      public class PasswordField extends AbstractStringField {

        @Override
        protected boolean getConfiguredInputMasked() {
          return true;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Password");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected int getConfiguredMaxLength() {
          return SharedUserUtility.MAX_PASSWORD_LENGTH;
        }

        @Override
        protected String execValidateValue(String rawValue) {
          char[] chars = null;
          if (rawValue != null) {
            chars = rawValue.toCharArray();
          }
          SharedUserUtility.checkPassword(chars);
          return rawValue;
        }
      }

      @Order(30)
      @ClassId("5f45d3de-e652-4cbe-8e23-25511e892d1e")
      public class UserRoleField extends AbstractSmartField<Integer> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UserRole");
        }

        @Override
        protected Class<? extends ICodeType<?, Integer>> getConfiguredCodeType() {
          return UserRoleCodeType.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected void execInitField() {
          setValue(UserCode.ID);
        }
      }

      @Order(40)
      @ClassId("313af847-fe02-4d2e-81ef-62695fd23a49")
      public class OkButton extends AbstractOkButton {
      }

      @Order(50)
      @ClassId("b27fa2be-b9e8-4d72-bc4f-c3df8bf1e910")
      public class CancelButton extends AbstractCancelButton {
      }
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      setEnabledPermission(new UpdateUserPermission());
      getPasswordField().setVisibleGranted(false);
      getPasswordField().setMandatory(false);
    }

    @Override
    protected void execStore() {
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      BEANS.get(IUserProcessService.class).updateUser(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      setEnabledPermission(new CreateUserPermission());
    }

    @Override
    protected void execStore() {
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      BEANS.get(IUserProcessService.class).createUser(formData);
    }
  }
}
