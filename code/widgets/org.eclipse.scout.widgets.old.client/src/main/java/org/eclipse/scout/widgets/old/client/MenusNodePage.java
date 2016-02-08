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
package org.eclipse.scout.widgets.old.client;

import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.checkbox.AbstractCheckBoxMenu;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.old.client.ui.forms.MenusForm;
import org.eclipse.scout.widgets.client.ui.desktop.menu.AbstractViewSourceOnGitHubMenu;

/**
 * @author mzi
 */
public class MenusNodePage extends AbstractPageWithNodes {
  public MenusNodePage() {
    super(true, MenusForm.class.getSimpleName());
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
  protected void execInitPage() {
    MenusForm form = new MenusForm();
    setDetailForm(form);
    form.startPageForm();
  }

  @Order(10)
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
  public class MenuWithMenusMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("MenuWithMenus");
    }

    @Order(10)
    public class Menu1Menu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TreeMenuType.EmptySpace);
      }

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
    public class Menu2Menu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TreeMenuType.EmptySpace);
      }

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
    public class Menu3Menu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TreeMenuType.EmptySpace);
      }

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
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected Class<?> provideSourceClass() {
      return MenusNodePage.class;
    }
  }
}
