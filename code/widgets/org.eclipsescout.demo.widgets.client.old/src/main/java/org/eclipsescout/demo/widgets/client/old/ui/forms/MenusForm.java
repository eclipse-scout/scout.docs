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
package org.eclipsescout.demo.widgets.client.old.ui.forms;

import java.util.List;

import org.eclipse.scout.commons.ConfigurationUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.annotations.OrderedCollection;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.checkbox.AbstractCheckBoxMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.old.ui.forms.MenusForm.MainBox.MenusButton;
import org.eclipsescout.demo.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;
import org.eclipsescout.demo.widgets.client.ui.forms.IPageForm;

public class MenusForm extends AbstractForm implements IPageForm {

  public MenusForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Menus");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MenusButton getMenusButton() {
    return getFieldByClass(MenusButton.class);
  }

  @Override
  public AbstractCloseButton getCloseButton() throws ProcessingException {
    return null;
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class MenusButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Menus") + " (Button)";
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        requestPopup();
      }

      @Override
      protected void injectMenusInternal(OrderedCollection<IMenu> menus) {
        new P_ConfiguredMenus().injectMenus(menus);
      }
    }

    @Order(20.0)
    public class MenusLinkButton extends AbstractLinkButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Menus") + " (Link)";
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        requestPopup();
      }

      @Override
      protected void injectMenusInternal(OrderedCollection<IMenu> menus) {
        new P_ConfiguredMenus().injectMenus(menus);
      }
    }

    @Order(30.0)
    public class MenusMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Menus") + " (Menu)";
      }

      @Override
      protected void injectActionNodesInternal(OrderedCollection<IMenu> actionNodes) {
        new P_ConfiguredMenus().injectMenus(actionNodes);
      }
    }

    @Order(40.0)
    public class MenusIconMenu extends AbstractMenu {

      @Override
      protected String getConfiguredIconId() {
        return "font:\uF0C9"; // icons-menu
      }

      @Override
      protected String getConfiguredTooltipText() {
        return TEXTS.get("Menus") + " (Menu)";
      }

      @Override
      protected void injectActionNodesInternal(OrderedCollection<IMenu> actionNodes) {
        new P_ConfiguredMenus().injectMenus(actionNodes);
      }
    }

    @Order(110.0)
    public class NoMenusButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return "Click me (Button)";
      }

      @Override
      protected int getConfiguredHorizontalAlignment() {
        return 1;
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        MessageBox.showOkMessage(null, "You clicked me!", null);
      }
    }

    @Order(120.0)
    public class NoMenusLinkButton extends AbstractLinkButton {

      @Override
      protected String getConfiguredLabel() {
        return "Click me (Link)";
      }

      @Override
      protected int getConfiguredHorizontalAlignment() {
        return 1;
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        MessageBox.showOkMessage(null, "You clicked me!", null);
      }
    }

    @Order(130.0)
    public class NoMenusMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return "Click me (Menu)";
      }

      @Override
      protected int getConfiguredHorizontalAlignment() {
        return 1;
      }

      @Override
      protected void execAction() throws ProcessingException {
        MessageBox.showOkMessage(null, "You clicked me!", null);
      }
    }

    @Order(140.0)
    public class NoMenusIconMenu extends AbstractMenu {

      @Override
      protected String getConfiguredIconId() {
        return "font:\uF0C9"; // icons-menu
      }

      @Override
      protected String getConfiguredTooltipText() {
        return "Click me (Menu)";
      }

      @Override
      protected int getConfiguredHorizontalAlignment() {
        return 1;
      }

      @Override
      protected void execAction() throws ProcessingException {
        MessageBox.showOkMessage(null, "You clicked me!", null);
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  private static class P_ConfiguredMenus {

    public void injectMenus(OrderedCollection<IMenu> menus) {
      // Get declared menu classes
      Class[] dca = ConfigurationUtility.getDeclaredPublicClasses(getClass());
      List<Class<IMenu>> menuClasses = ConfigurationUtility.filterClasses(dca, IMenu.class);
      List<Class<? extends IMenu>> declaredMenus = ConfigurationUtility.removeReplacedClasses(menuClasses);

      // Create instances and add to list
      for (Class<? extends IMenu> menuClazz : declaredMenus) {
        IMenu menu;
        try {
          menu = ConfigurationUtility.newInnerInstance(this, menuClazz);
          menus.addOrdered(menu);
        }
        catch (Exception e) {
          BEANS.get(ExceptionHandler.class).handle(new ProcessingException("error creating instance of class '" + menuClazz.getName() + "'.", e));
        }
      }
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
      protected void execSelectionChanged(boolean selection) throws ProcessingException {
        if (selection == true) {
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
        return MenusForm.class;
      }
    }
  }
}
