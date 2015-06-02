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
package org.eclipsescout.demo.bahbah.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.service.SERVICES;
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

@FormData(value = UserFormData.class, sdkCommand = SdkCommand.CREATE)
public class UserForm extends AbstractForm {

  private Long m_userId;

  public UserForm() throws ProcessingException {
    super();
  }

  @FormData
  public Long getUserId() {
    return m_userId;
  }

  @FormData
  public void setUserId(Long userId) {
    m_userId = userId;
  }

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
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

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10.0)
    public class UserBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("User");
      }

      @Order(10.0)
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
        protected String execValidateValue(String rawValue) throws ProcessingException {
          SharedUserUtility.checkUsername(rawValue);
          return rawValue;
        }
      }

      @Order(20.0)
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
        protected String execValidateValue(String rawValue) throws ProcessingException {
          SharedUserUtility.checkPassword(rawValue);
          return rawValue;
        }
      }

      @Order(30.0)
      public class UserRoleField extends AbstractSmartField<Integer> {

        @Override
        protected Class<? extends ICodeType<Integer>> getConfiguredCodeType() {
          return UserRoleCodeType.class;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UserRole");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(UserCode.ID);
        }
      }

      @Order(40.0)
      public class OkButton extends AbstractOkButton {
      }

      @Order(50.0)
      public class CancelButton extends AbstractCancelButton {
      }
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      setEnabledPermission(new UpdateUserPermission());
      getPasswordField().setVisibleGranted(false);
      getPasswordField().setMandatory(false);
    }

    @Override
    protected void execStore() throws ProcessingException {
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      SERVICES.getService(IUserProcessService.class).updateUser(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      setEnabledPermission(new CreateUserPermission());
    }

    @Override
    protected void execStore() throws ProcessingException {
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      SERVICES.getService(IUserProcessService.class).createUser(formData);
    }
  }
}
