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
package org.eclipsescout.demo.bahbah.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.annotations.PageData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.form.useradmin.DefaultPasswordForm;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.bahbah.client.ui.forms.UserForm;
import org.eclipsescout.demo.bahbah.shared.security.CreateUserPermission;
import org.eclipsescout.demo.bahbah.shared.security.DeleteUserPermission;
import org.eclipsescout.demo.bahbah.shared.security.ResetPasswordPermission;
import org.eclipsescout.demo.bahbah.shared.security.UpdateUserPermission;
import org.eclipsescout.demo.bahbah.shared.services.UserAdministrationTablePageData;
import org.eclipsescout.demo.bahbah.shared.services.code.UserRoleCodeType;
import org.eclipsescout.demo.bahbah.shared.services.process.IUserProcessService;
import org.eclipsescout.demo.bahbah.shared.services.process.UserFormData;

@PageData(UserAdministrationTablePageData.class)
public class UserAdministrationTablePage extends AbstractPageWithTable<UserAdministrationTablePage.Table> {

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Users");
  }

  @Override
  protected void execLoadData(SearchFilter filter) throws ProcessingException {
    UserFormData formData = (UserFormData) filter.getFormData();
    if (formData == null) {
      formData = new UserFormData();
    }

    UserAdministrationTablePageData pageData = SERVICES.getService(IUserProcessService.class).getUserAdministrationTableData(formData);
    importPageData(pageData);
  }

  @Order(10.0)
  public class Table extends AbstractTable {

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
      return true;
    }

    @Override
    protected String getConfiguredDefaultIconId() {
      return org.eclipsescout.demo.bahbah.shared.Icons.Eye;
    }

    @Override
    protected void execRowAction(ITableRow row) throws ProcessingException {
      getMenu(ModifyUserMenu.class).execAction();
    }

    public RoleColumn getRoleColumn() {
      return getColumnSet().getColumnByClass(RoleColumn.class);
    }

    public UserIdColumn getUserIdColumn() {
      return getColumnSet().getColumnByClass(UserIdColumn.class);
    }

    public UsernameColumn getUsernameColumn() {
      return getColumnSet().getColumnByClass(UsernameColumn.class);
    }

    @Order(10.0)
    public class UserIdColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredMandatory() {
        return true;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }

      @Override
      protected int getConfiguredWidth() {
        return 0;
      }
    }

    @Order(20.0)
    public class UsernameColumn extends AbstractStringColumn {

      @Override
      protected boolean getConfiguredAutoOptimizeWidth() {
        return true;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Username");
      }

      @Override
      protected int getConfiguredSortIndex() {
        return 0;
      }

      @Override
      protected int getConfiguredWidth() {
        return 230;
      }
    }

    @Order(30.0)
    public class RoleColumn extends AbstractSmartColumn<Integer> {

      @Override
      protected boolean getConfiguredAutoOptimizeWidth() {
        return true;
      }

      @Override
      protected Class<? extends ICodeType> getConfiguredCodeType() {
        return UserRoleCodeType.class;

      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Role");
      }

      @Override
      protected int getConfiguredWidth() {
        return 276;
      }
    }

    @Order(10.0)
    public class ModifyUserMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ModifyUser");
      }

      @Override
      protected void execAction() throws ProcessingException {
        UserForm form = new UserForm();
        form.getUsernameField().setValue(getUsernameColumn().getSelectedValue());
        form.getUserRoleField().setValue(getRoleColumn().getSelectedValue());
        form.setUserId(getUserIdColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }

      @Override
      protected void execPrepareAction() throws ProcessingException {
        setVisiblePermission(new UpdateUserPermission());
      }
    }

    @Order(20.0)
    public class NewUserMenu extends AbstractMenu {

      @Override
      protected boolean getConfiguredEmptySpaceAction() {
        return true;
      }

      @Override
      protected boolean getConfiguredMultiSelectionAction() {
        return true;
      }

      @Override
      protected boolean getConfiguredSingleSelectionAction() {
        return false;
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewUser");
      }

      @Override
      protected void execAction() throws ProcessingException {
        UserForm frm = new UserForm();
        frm.startNew();
        frm.waitFor();
        if (frm.isFormStored()) {
          reloadPage();
        }
      }

      @Override
      protected void execPrepareAction() throws ProcessingException {
        setVisiblePermission(new CreateUserPermission());
      }
    }

    @Order(30.0)
    public class ResetPasswordMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ResetPassword");
      }

      @Override
      protected void execAction() throws ProcessingException {
        DefaultPasswordForm frm = new DefaultPasswordForm();
        frm.setUserId(getUserIdColumn().getSelectedValue().toString());
        frm.startReset();
        frm.waitFor();
      }

      @Override
      protected void execPrepareAction() throws ProcessingException {
        setVisiblePermission(new ResetPasswordPermission());
      }
    }

    @Order(40.0)
    public class SeparatorMenu extends AbstractMenu {

      @Override
      protected boolean getConfiguredSeparator() {
        return true;
      }

      @Override
      protected void execPrepareAction() throws ProcessingException {
        setVisiblePermission(new DeleteUserPermission());
      }
    }

    @Order(50.0)
    public class DeleteUserMenu extends AbstractMenu {

      @Override
      protected boolean getConfiguredMultiSelectionAction() {
        return true;
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("DeleteUser");
      }

      @Override
      protected void execAction() throws ProcessingException {
        if (MessageBox.showDeleteConfirmationMessage(TEXTS.get("Users"), getUsernameColumn().getValues(getSelectedRows()))) {
          SERVICES.getService(IUserProcessService.class).deleteUsers(getUserIdColumn().getValues(getSelectedRows()));
          reloadPage();
        }
      }

      @Override
      protected void execPrepareAction() throws ProcessingException {
        setVisiblePermission(new DeleteUserPermission());
      }
    }
  }
}
