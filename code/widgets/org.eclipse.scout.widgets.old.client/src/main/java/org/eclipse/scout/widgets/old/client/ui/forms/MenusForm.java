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
package org.eclipse.scout.widgets.old.client.ui.forms;

import java.util.List;

import org.eclipse.scout.rt.client.ui.action.IAction;
import org.eclipse.scout.rt.client.ui.action.IActionVisitor;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.checkbox.AbstractCheckBoxMenu;
import org.eclipse.scout.rt.client.ui.action.menu.root.IContextMenuOwner;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IFormFieldVisitor;
import org.eclipse.scout.rt.client.ui.form.fields.ICompositeField;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.reflect.ConfigurationUtility;
import org.eclipse.scout.rt.platform.util.collection.OrderedCollection;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.old.client.ui.forms.MenusForm.MainBox.MenusButton;
import org.eclipse.scout.widgets.shared.Icons;

@ClassId("df3e491c-5a37-424c-8c22-5a3403895b94")
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

  @Order(10)
  @ClassId("18e80c91-fbb5-4b31-9184-d87386c2b450")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("e4648f07-6c4f-4936-890a-addad86c0322")
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

    @Order(15)
    @ClassId("dd94a800-23a0-422c-922c-a12d4694ae11")
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

    @Order(20)
    @ClassId("1e58dd2e-da88-4d92-b49d-a2ead78cf033")
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

    @Order(30)
    @ClassId("641afe83-8646-4ee5-941a-91e801674cce")
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

    @Order(40)
    @ClassId("8483666f-f641-4450-9341-54b1878be748")
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

    @Order(50)
    @ClassId("93b96321-2d17-4b81-9fb8-a0452f0e86a2")
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

    @Order(110)
    @ClassId("de873e0a-5a7e-4de7-b9d1-5307fe5c7c71")
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

    @Order(120)
    @ClassId("d26120c8-9eaf-4ec5-852c-753e7f3c24ea")
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

    @Order(130)
    @ClassId("26515145-3aa2-4bb6-b68e-12de6f9a0d57")
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

    @Order(140)
    @ClassId("46670348-0344-4d9c-bf67-5169ad0f9e3f")
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

    @Order(10)
    @ClassId("370431be-8ef3-489e-8f9a-061c65455524")
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected double getConfiguredGridWeightY() {
        return 1;
      }

      @Order(10)
      @ClassId("00a34602-21e7-4d07-b384-145f9598d367")
      public class StringField extends AbstractStringField {

        @Override
        protected void execInitField() {
          setLabel(getClass().getSimpleName());
          setValue("this string field has a menu");
        }

        @Order(10)
        @ClassId("c58ab871-3bf5-4598-8b10-8e70051b1085")
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

      @Order(20)
      @ClassId("ea526e8c-fd2d-4a4c-9c6c-0489953191dd")
      public class StringWithTooltipField extends AbstractStringField {

        @Override
        protected void execInitField() {
          setLabel(getClass().getSimpleName());
          setValue("this string field has a menu and a tooltip");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return "I am a tooltip";
        }

        @Order(10)
        @ClassId("381fb976-7cc8-4e51-8a2b-99e2013b1f1b")
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

      @Order(30)
      @ClassId("a935d939-4443-49ea-80e7-66191fcfb720")
      public class StringRightField extends AbstractStringField {

        @Override
        protected void execInitField() {
          setLabel(getClass().getSimpleName());
          setValue("is the menu correctly displayed?");
        }

        @Order(10)
        @ClassId("5b9d9bd2-1f2d-4ede-b07a-c5b75885d81a")
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

      @Order(40)
      @ClassId("63361853-9ba8-459e-a6b8-420f3467cddf")
      public class StringRightWithTooltipField extends AbstractStringField {

        @Override
        protected void execInitField() {
          setLabel(getClass().getSimpleName());
          setValue("is the tooltip correctly displayed?");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return "I am a tooltip";
        }
      }

      @Order(50)
      @ClassId("d6a8c9af-6271-4464-90b9-5f1b45f7eb8c")
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
          setLabel(getClass().getSimpleName());
          setImageId("eclipse_scout_logo");
        }

        @Order(10)
        @ClassId("b622fda2-f5e2-4b14-ba32-b8702f90c1e6")
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

      @Order(60)
      @ClassId("78345cbe-005f-4310-95df-a30398e9664e")
      public class ToggleEnabledStateButton extends AbstractLinkButton {

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Toggle enabled state for all fields and menus (except this one)";
        }

        @Override
        protected void execClickAction() {
          MenusForm.this.visitFields(new IFormFieldVisitor() {

            @Override
            public boolean visitField(IFormField field, int level, int fieldIndex) {
              if (field instanceof IContextMenuOwner) {
                IContextMenuOwner menuOwner = (IContextMenuOwner) field;
                if (menuOwner.getContextMenu() != null) {
                  menuOwner.getContextMenu().acceptVisitor(new IActionVisitor() {
                    @Override
                    public int visit(IAction action) {
                      action.setEnabled(!action.isEnabled());
                      return IActionVisitor.CONTINUE;
                    }
                  });
                }
              }
              if (!(field instanceof ICompositeField) && field != ToggleEnabledStateButton.this) {
                field.setEnabled(!field.isEnabled());
              }
              return true;
            }
          });
        }
      }

      @Order(10)
      @ClassId("fd97bc7c-73ed-4e17-932c-52c0e4afb161")
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

      @Order(15)
      @ClassId("8eff3c1e-8ea8-4c3f-b105-31d87f3f265e")
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

      @Order(20)
      @ClassId("4dbeb296-362d-4c2f-ad2c-6ae4f5d21588")
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

      @Order(30)
      @ClassId("88dc062f-ffa5-4813-ab00-51f59eecdf9c")
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

      @Order(40)
      @ClassId("f7125f03-e2d6-40c3-a30a-b22886d51929")
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

      @Order(50)
      @ClassId("e90da1ca-3102-46f8-a939-2864c990fa7a")
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

      @Order(110)
      @ClassId("10b3422e-03db-4d44-bc20-74a38fb09f4a")
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

      @Order(120)
      @ClassId("2919bca8-6477-42f2-8a10-94235585aa95")
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

      @Order(130)
      @ClassId("44901f86-ec22-42cc-b096-e765507217eb")
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

      @Order(140)
      @ClassId("7f172de1-1a7c-4c29-a488-1ea7f359ce5f")
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
    @ClassId("0a6d2bc9-159d-4c50-8927-a898219694c5")
    public class GroupBox2 extends AbstractGroupBox {
      @Override
      protected String getConfiguredLabel() {
        return "Another groupbox with more menus";
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10)
      @ClassId("d418f16b-84fb-49be-8589-3864510a39f8")
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

      @Order(20)
      @ClassId("eca8764e-1839-4912-9c0d-ff64b39c300a")
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

      @Order(30)
      @ClassId("f41c3683-7499-4654-9d33-94c50e6f473e")
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

      @Order(40)
      @ClassId("e5d9219a-4be5-4cde-b419-8d9b4129540f")
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

      @Order(50)
      @ClassId("bdf26a64-c9d9-413f-b5b2-e02a9dbb8c00")
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

      @Order(60)
      @ClassId("3a38da29-f4d9-4242-9252-a6739349a2f9")
      public class SpecialTextMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Special text: {}§°ÂÄÅyjp\u0BF5";
        }

        @Override
        protected void execAction() {
          MessageBoxes.createOk().withHeader("You clicked me!").show();
        }
      }

      @Order(30)
      @ClassId("92be9fd0-558f-4fa8-a528-1e110e711170")
      public class StringBottomField extends AbstractStringField {

        @Override
        protected void execInitField() {
          setLabel(getClass().getSimpleName());
          setValue("this string field is on the bottom, is the menu correctly displayed?");
        }

        @Order(10)
        @ClassId("c4fe1144-28c6-4b0e-9144-d525c6ab4eef")
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

        @Order(20)
        @ClassId("a51a2f9c-939c-4df8-a578-f5d52fcd9559")
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
        menu = ConfigurationUtility.newInnerInstance(this, menuClazz);
        menus.addOrdered(menu);
      }
    }

    @Order(10)
    @ClassId("93edcf21-a5de-44a6-b33f-ae5e75271703")
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

    @Order(20)
    @ClassId("66919a64-3dfd-42ec-8517-963f10f9851e")
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

    @Order(30)
    @ClassId("298860b2-651f-4b6c-8c8b-3cb61ded4063")
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

    @Order(40)
    @ClassId("a03037a0-4f36-4ee1-b520-742cc31511fb")
    public class MenuWithMenusMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("MenuWithMenus");
      }

      @Order(10)
      @ClassId("4035f395-bbee-4b88-9088-fec12b4b4787")
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

      @Order(20)
      @ClassId("ec432381-873e-4cfd-b951-008984664581")
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

      @Order(30)
      @ClassId("be0a9ac2-24c4-41ef-b98b-7547f91fc402")
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

    @Order(50)
    @ClassId("bf083759-7500-4d70-9702-89dab13a07cb")
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

    @Order(60)
    @ClassId("55213fae-81fd-47c1-adb2-43e522cebfda")
    public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

      @Override
      protected Class<?> provideSourceClass() {
        return MenusForm.class;
      }
    }
  }
}
