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
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.old.ui.forms.MenusForm.MainBox.MenusButton;
import org.eclipsescout.demo.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;
import org.eclipsescout.demo.widgets.client.ui.forms.IPageForm;
import org.eclipsescout.demo.widgets.shared.Icons;

public class MenusForm extends AbstractForm implements IPageForm {

  public MenusForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Menus");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MenusButton getMenusButton() {
    return getFieldByClass(MenusButton.class);
  }

  @Override
  public AbstractCloseButton getCloseButton() {
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
      protected void execClickAction() {
        requestPopup();
      }

      @Override
      protected void injectMenusInternal(OrderedCollection<IMenu> menus) {
        new P_ConfiguredMenus().injectMenus(menus);
      }
    }

    @Order(15.0)
    public class MenusButtonAndIcon extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Menus") + " (Button)";
      }

      @Override
      protected String getConfiguredIconId() {
        return "font:\uE032"; // icons-star
      }

      @Override
      protected void execClickAction() {
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
      protected void execClickAction() {
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

    @Order(50.0)
    public class MenusIconAndTextMenu extends AbstractMenu {

      @Override
      protected String getConfiguredIconId() {
        return "font:\uE032"; // icons-star
      }

      @Override
      protected String getConfiguredText() {
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
      protected void execClickAction() {
        MessageBoxes.createOk().withHeader("You clicked me!").show();
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
      protected void execClickAction() {
        MessageBoxes.createOk().withHeader("You clicked me!").show();
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
      protected void execAction() {
        MessageBoxes.createOk().withHeader("You clicked me!").show();
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
      protected void execAction() {
        MessageBoxes.createOk().withHeader("You clicked me!").show();
      }
    }

    @Order(10.0)
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected double getConfiguredGridWeightY() {
        return 1;
      }

      @Order(10.0)
      public class StringField extends AbstractStringField {

        @Override
        protected void execInitField() {
          setValue("this string field has a menu");
        }

        @Order(10.0)
        public class StringFieldMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Click me (Menu)";
          }

          @Override
          protected void execAction() {
            MessageBoxes.createOk().withHeader("You clicked me!").show();
          }
        }
      }

      @Order(20.0)
      public class StringWithTooltipField extends AbstractStringField {

        @Override
        protected void execInitField() {
          setValue("this string field has a menu and a tooltip");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return "I am a tooltip";
        }

        @Order(10.0)
        public class StringFieldMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Click me (Menu)";
          }

          @Override
          protected void execAction() {
            MessageBoxes.createOk().withHeader("You clicked me!").show();
          }
        }
      }

      @Order(30.0)
      public class StringRightField extends AbstractStringField {

        @Override
        protected void execInitField() {
          setValue("is the menu correctly displayed?");
        }

        @Order(10.0)
        public class StringFieldMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Click me (Menu)";
          }

          @Override
          protected void execAction() {
            MessageBoxes.createOk().withHeader("You clicked me!").show();
          }
        }
      }

      @Order(40.0)
      public class StringRightWithTooltipField extends AbstractStringField {

        @Override
        protected void execInitField() {
          setValue("is the tooltip correctly displayed?");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return "I am a tooltip";
        }

      }

      @Order(50.0)
      public class ImageField extends AbstractImageField {

        @Override
        protected boolean getConfiguredAutoFit() {
          return true;
        }

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected void execInitField() {
          setImageId("eclipse_scout_logo");
        }

        @Order(10.0)
        public class StringFieldMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Click me (Menu)";
          }

          @Override
          protected void execAction() {
            MessageBoxes.createOk().withHeader("You clicked me!").show();
          }
        }
      }

      @Order(10.0)
      public class MenusButton extends AbstractButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Menus") + " (Button)";
        }

        @Override
        protected void execClickAction() {
          requestPopup();
        }

        @Override
        protected void injectMenusInternal(OrderedCollection<IMenu> menus) {
          new P_ConfiguredMenus().injectMenus(menus);
        }
      }

      @Order(15.0)
      public class MenusButtonAndIcon extends AbstractButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Menus") + " (Button)";
        }

        @Override
        protected String getConfiguredIconId() {
          return "font:\uE032"; // icons-star
        }

        @Override
        protected void execClickAction() {
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
        protected void execClickAction() {
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

      @Order(50.0)
      public class MenusIconAndTextMenu extends AbstractMenu {

        @Override
        protected String getConfiguredIconId() {
          return "font:\uE032"; // icons-star
        }

        @Override
        protected String getConfiguredText() {
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
        protected void execClickAction() {
          MessageBoxes.createOk().withHeader("You clicked me!").show();
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
        protected void execClickAction() {
          MessageBoxes.createOk().withHeader("You clicked me!").show();
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
        protected void execAction() {
          MessageBoxes.createOk().withHeader("You clicked me!").show();
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
        protected void execAction() {
          MessageBoxes.createOk().withHeader("You clicked me!").show();
        }
      }
    }

    @Order(20)
    public class GroupBox2 extends AbstractGroupBox {
      @Override
      protected String getConfiguredLabel() {
        return "Another groupbox with more menus";
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10.0)
      public class PngIconMenu extends AbstractMenu {

        @Override
        protected String getConfiguredIconId() {
          return Icons.Bookmark;
        }

        @Override
        protected String getConfiguredTooltipText() {
          return "PNG Icon Menu";
        }

        @Override
        protected void execAction() {
          MessageBoxes.createOk().withHeader("You clicked me!").show();
        }
      }

      @Order(20.0)
      public class PngIconWithTextMenu extends AbstractMenu {

        @Override
        protected String getConfiguredIconId() {
          return Icons.Bookmark;
        }

        @Override
        protected String getConfiguredText() {
          return "PNG Icon Menu";
        }

        @Override
        protected void execAction() {
          MessageBoxes.createOk().withHeader("You clicked me!").show();
        }
      }

      @Order(30.0)
      public class MenusPngIconMenu extends AbstractMenu {

        @Override
        protected String getConfiguredIconId() {
          return Icons.Bookmark;
        }

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Menus") + " PNG Icon Menu";
        }

        @Override
        protected void injectActionNodesInternal(OrderedCollection<IMenu> actionNodes) {
          new P_ConfiguredMenus().injectMenus(actionNodes);
        }
      }

      @Order(40.0)
      public class ToggleTextIconPngMenu extends AbstractMenu {

        @Override
        protected String getConfiguredIconId() {
          return Icons.Bookmark;
        }

        @Override
        protected String getConfiguredText() {
          return "Hide icon";
        }

        @Override
        protected void execAction() {
          if (getIconId() == null) {
            setIconId(Icons.Bookmark);
            setText(null);
          }
          else if (getText() == null) {
            setText("Hide icon");
          }
          else {
            setIconId(null);
            setText("Hide text, show icon");
          }
        }
      }

      @Order(50.0)
      public class OpenOtherMenu extends AbstractMenu {

        @Override
        protected String getConfiguredIconId() {
          return Icons.Bookmark;
        }

        @Override
        protected String getConfiguredText() {
          return "Toggle other menu";
        }

        @Override
        protected void execAction() {
          MenusPngIconMenu otherMenu = getMenuByClass(MenusPngIconMenu.class);
          otherMenu.setSelected(!otherMenu.isSelected());
        }
      }

      @Order(30.0)
      public class StringBottomField extends AbstractStringField {

        @Override
        protected void execInitField() {
          setValue("this string field is on the bottom, is the menu correctly displayed?");
        }

        @Order(10.0)
        public class StringFieldMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Click me (Menu)";
          }

          @Override
          protected void execAction() {
            MessageBoxes.createOk().withHeader("You clicked me!").show();
          }
        }

        @Order(20.0)
        public class StringField2Menu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Another string field menu";
          }

          @Override
          protected void execAction() {
            MessageBoxes.createOk().withHeader("You clicked me!").show();
          }
        }
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
      protected void execAction() {
        String menuname = this.getClass().getSimpleName();
        MessageBoxes.createOk().withHeader("Clicked on Menu").withBody("You have clicked on \"" + TEXTS.get(menuname.substring(0, menuname.length() - 4)) + "\"").show();
      }
    }

    @Order(20.0)
    public class MenuWithIconMenu extends AbstractMenu {

      @Override
      protected String getConfiguredIconId() {
        return org.eclipse.scout.rt.shared.AbstractIcons.Gear;
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("MenuWithIcon");
      }

      @Override
      protected void execAction() {
        String menuname = this.getClass().getSimpleName();
        MessageBoxes.createOk().withHeader("Clicked on Menu").withBody("You have clicked on \"" + TEXTS.get(menuname.substring(0, menuname.length() - 4)) + "\"").show();
      }
    }

    @Order(30.0)
    public class CheckableMenu extends AbstractCheckBoxMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("CheckableMenu");
      }

      @Override
      protected void execSelectionChanged(boolean selection) {
        if (selection == true) {
          MessageBoxes.createOk().withHeader("Checked the Menu").withBody("You have checked the \"" + TEXTS.get(this.getClass().getSimpleName()) + "\"").show();
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
        protected void execAction() {
          String menuname = this.getClass().getSimpleName();
          MessageBoxes.createOk().withHeader("Clicked on Menu").withBody("You have clicked on \"" + TEXTS.get(menuname.substring(0, menuname.length() - 4)) + "\"").show();
        }
      }

      @Order(20.0)
      public class Menu2Menu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Menu2");
        }

        @Override
        protected void execAction() {
          String menuname = this.getClass().getSimpleName();
          MessageBoxes.createOk().withHeader("Clicked on Menu").withBody("You have clicked on \"" + TEXTS.get(menuname.substring(0, menuname.length() - 4)) + "\"").show();
        }
      }

      @Order(30.0)
      public class Menu3Menu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Menu3");
        }

        @Override
        protected void execAction() {
          String menuname = this.getClass().getSimpleName();
          MessageBoxes.createOk().withHeader("Clicked on Menu").withBody("You have clicked on \"" + TEXTS.get(menuname.substring(0, menuname.length() - 4)) + "\"").show();
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
      protected void execAction() {
        String menuname = this.getClass().getSimpleName();
        MessageBoxes.createOk().withHeader("Clicked on Menu").withBody("You have clicked on \"" + TEXTS.get(menuname.substring(0, menuname.length() - 4)) + "\"").show();
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
