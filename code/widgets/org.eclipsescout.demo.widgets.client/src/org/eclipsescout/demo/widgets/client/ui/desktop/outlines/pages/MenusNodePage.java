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
package org.eclipsescout.demo.widgets.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.checkbox.AbstractCheckBoxMenu;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.MenusForm;
import org.eclipsescout.demo.widgets.client.ui.template.menu.AbstractViewSourceOnGitHubMenu;

public class MenusNodePage extends AbstractPageWithNodes {

  public MenusNodePage() {
    super(true, MenusForm.class.getSimpleName());
  }

  @Override
  protected String getConfiguredIconId() {
    return org.eclipse.scout.rt.shared.AbstractIcons.NavigationCurrent;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected boolean getConfiguredTableVisible() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Menus");
  }

  @Override
  protected void execInitPage() throws ProcessingException {
    MenusForm form = new MenusForm();
    form.startPageForm();
    setDetailForm(form);
  }

  @Order(10.0)
  public class MenuWithTextMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("MenuWithText");
    }

    @Override
    protected void execAction() throws ProcessingException {
      String menuname = this.getClass().getSimpleName();
      MessageBox.showOkMessage("Clicked on Menu", "You have clicked on \"" + TEXTS.get(menuname.substring(0, menuname.length() - 4)) + "\"", null);
    }
  }

  @Order(20.0)
  public class MenuWithIconMenu extends AbstractMenu {

    @Override
    protected String getConfiguredIconId() {
      return org.eclipse.scout.rt.shared.AbstractIcons.Gears;
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("MenuWithIcon");
    }

    @Override
    protected void execAction() throws ProcessingException {
      String menuname = this.getClass().getSimpleName();
      MessageBox.showOkMessage("Clicked on Menu", "You have clicked on \"" + TEXTS.get(menuname.substring(0, menuname.length() - 4)) + "\"", null);
    }
  }

  @Order(30.0)
  public class CheckableMenu extends AbstractCheckBoxMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("CheckableMenu");
    }

    @Override
    protected void execToggleAction(boolean selected) throws ProcessingException {
      super.execToggleAction(selected);
      if (selected == true) {
        MessageBox.showOkMessage("Checked the Menu", "You have checked the \"" + TEXTS.get(this.getClass().getSimpleName()) + "\"", null);
      }
    }
  }

  @Order(40.0)
  public class MenuWithMenusMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("MenuWithMenus");
    }

    @Order(10.0)
    public class Menu1Menu extends AbstractMenu {

      @Override
      protected boolean getConfiguredEmptySpaceAction() {
        return true;
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Menu1");
      }

      @Override
      protected void execAction() throws ProcessingException {
        String menuname = this.getClass().getSimpleName();
        MessageBox.showOkMessage("Clicked on Menu", "You have clicked on \"" + TEXTS.get(menuname.substring(0, menuname.length() - 4)) + "\"", null);
      }
    }

    @Order(20.0)
    public class Menu2Menu extends AbstractMenu {

      @Override
      protected boolean getConfiguredEmptySpaceAction() {
        return true;
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Menu2");
      }

      @Override
      protected void execAction() throws ProcessingException {
        String menuname = this.getClass().getSimpleName();
        MessageBox.showOkMessage("Clicked on Menu", "You have clicked on \"" + TEXTS.get(menuname.substring(0, menuname.length() - 4)) + "\"", null);
      }
    }

    @Order(30.0)
    public class Menu3Menu extends AbstractMenu {

      @Override
      protected boolean getConfiguredEmptySpaceAction() {
        return true;
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Menu3");
      }

      @Override
      protected void execAction() throws ProcessingException {
        String menuname = this.getClass().getSimpleName();
        MessageBox.showOkMessage("Clicked on Menu", "You have clicked on \"" + TEXTS.get(menuname.substring(0, menuname.length() - 4)) + "\"", null);
      }
    }
  }

  @Order(50.0)
  public class MenuWithKeyStrokeMenu extends AbstractMenu {

    @Override
    protected String getConfiguredKeyStroke() {
      return "m";
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("MenuWithKeyStroke");
    }

    @Override
    protected void execAction() throws ProcessingException {
      String menuname = this.getClass().getSimpleName();
      MessageBox.showOkMessage("Clicked on Menu", "You have clicked on \"" + TEXTS.get(menuname.substring(0, menuname.length() - 4)) + "\"", null);
    }
  }

  @Order(60.0)
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected Class<?> provideSourceClass() {
      return MenusNodePage.class;
    }
  }
}
