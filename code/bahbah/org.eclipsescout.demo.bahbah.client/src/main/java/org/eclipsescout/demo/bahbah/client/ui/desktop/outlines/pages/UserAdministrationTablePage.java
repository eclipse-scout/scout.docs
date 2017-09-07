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

import java.util.Set;

import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenuSeparator;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.form.useradmin.DefaultPasswordForm;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
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
  protected void execLoadData(SearchFilter filter) {
    UserFormData formData = (UserFormData) filter.getFormData();
    if (formData == null) {
      formData = new UserFormData();
    }

    UserAdministrationTablePageData pageData = BEANS.get(IUserProcessService.class).getUserAdministrationTableData(formData);
    importPageData(pageData);
  }

  public class Table extends AbstractTable {

    public UsernameColumn getUsernameColumn() {
      return getColumnSet().getColumnByClass(UsernameColumn.class);
    }

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
      return true;
    }

    @Override
    protected String getConfiguredDefaultIconId() {
      return org.eclipsescout.demo.bahbah.shared.Icons.Eye;
    }

    @Override
    protected void execRowAction(ITableRow row) {
      getMenuByClass(ModifyUserMenu.class).execAction();
    }

    public RoleColumn getRoleColumn() {
      return getColumnSet().getColumnByClass(RoleColumn.class);
    }

    public UserIdColumn getUserIdColumn() {
      return getColumnSet().getColumnByClass(UserIdColumn.class);
    }

    @Order(10)
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

    @Order(20)
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

    @Order(30)
    public class RoleColumn extends AbstractSmartColumn<Integer> {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Role");
      }

      @Override
      protected boolean getConfiguredAutoOptimizeWidth() {
        return true;
      }

      @Override
      protected Class<? extends ICodeType<?, Integer>> getConfiguredCodeType() {
        return UserRoleCodeType.class;

      }

      @Override
      protected int getConfiguredWidth() {
        return 276;
      }
    }

    @Order(10)
    public class ModifyUserMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ModifyUser");
      }

      @Override
      protected void execInitAction() {
        setVisiblePermission(new UpdateUserPermission());
      }

      @Override
      protected void execAction() {
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
    }

    @Order(20)
    public class NewUserMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewUser");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.EmptySpace, TableMenuType.MultiSelection);
      }

      @Override
      protected void execInitAction() {
        setVisiblePermission(new CreateUserPermission());
      }

      @Override
      protected void execAction() {
        UserForm frm = new UserForm();
        frm.startNew();
        frm.waitFor();
        if (frm.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(30)
    public class ResetPasswordMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ResetPassword");
      }

      @Override
      protected void execInitAction() {
        setVisiblePermission(new ResetPasswordPermission());
      }

      @Override
      protected void execAction() {
        DefaultPasswordForm frm = new DefaultPasswordForm();
        frm.setUserId(getUserIdColumn().getSelectedValue().toString());
        frm.startReset();
        frm.waitFor();
      }

    }

    @Order(40)
    public class SeparatorMenu extends AbstractMenuSeparator {

    }

    @Order(50)
    public class DeleteUserMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("DeleteUser");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
      }

      @Override
      protected void execInitAction() {
        setVisiblePermission(new DeleteUserPermission());
      }

      @Override
      protected void execAction() {
        if (MessageBoxes.showDeleteConfirmationMessage(TEXTS.get("Users"), getUsernameColumn().getValues(getSelectedRows()))) {
          BEANS.get(IUserProcessService.class).deleteUsers(getUserIdColumn().getValues(getSelectedRows()));
          reloadPage();
        }
      }
    }
  }
}
