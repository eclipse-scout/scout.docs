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
package org.eclipse.scout.widgets.client.ui.desktop;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.checkbox.AbstractCheckBoxMenu;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.IDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.context.PropertyMap;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ClientSession;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.AdvancedWidgetsOutline;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.LayoutWidgetsOutline;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.SimpleWidgetsOutline;
import org.eclipse.scout.widgets.client.ui.forms.OptionsForm;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm;

public class Desktop extends AbstractDesktop implements IDesktop {

  @Override
  protected String getConfiguredDisplayStyle() {
    return resolveDesktopStyle();
  }

  /**
   * Returns the 'desktopStyle' provided as part of the URL, or the default style otherwise.<br/>
   * E.g. http://[host:port]/?desktopStyle=BENCH to start in bench mode.
   */
  protected String resolveDesktopStyle() {
    String desktopStyle = PropertyMap.CURRENT.get().get("desktopStyle");
    if (desktopStyle != null) {
      return desktopStyle.toLowerCase();
    }
    else {
      return DISPLAY_STYLE_DEFAULT;
    }
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    List<Class<? extends IOutline>> outlines = new ArrayList<Class<? extends IOutline>>();
    outlines.add(SimpleWidgetsOutline.class);
    outlines.add(AdvancedWidgetsOutline.class);
    outlines.add(LayoutWidgetsOutline.class);
    return outlines;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ScoutWidgetsDemoApp");
  }

  @Override
  protected String getConfiguredLogoId() {
    return "application_logo";
  }

  @Override
  protected void execDefaultView() {
    if (DISPLAY_STYLE_BENCH.equals(getDisplayStyle())) {
      // "bench-only" desktop
      IForm benchForm = null;
      for (IDesktopExtension ext : getDesktopExtensions()) {
        if (ext instanceof IBenchFormProvider) {
          benchForm = ((IBenchFormProvider) ext).provideForm();
        }
      }
      if (benchForm == null) {
        benchForm = new StringFieldForm();
      }
      benchForm.setDisplayHint(IForm.DISPLAY_HINT_VIEW);
      benchForm.start();
    }
    else {
      // default desktop
      IOutline firstOutline = CollectionUtility.firstElement(getAvailableOutlines());
      if (firstOutline != null) {
        setOutline(firstOutline);
      }
    }
  }

  @Order(10)
  public class FileMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("FileMenu");
    }

    @Order(100)
    public class ExitMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ExitMenu");
      }

      @Override
      protected void execAction() {
        ClientSessionProvider.currentSession(ClientSession.class).stop();
      }
    }
  }

  @Order(20)
  public class ToolsMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ToolsMenu");
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
        return AbstractIcons.Gear;
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
      protected void execAction() {
        System.out.println("execAction");
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

      @Override
      protected void execAction() {
        String menuname = this.getClass().getSimpleName();
        MessageBoxes.createOk().withHeader("Clicked on Menu").withBody("You have clicked on \"" + TEXTS.get(menuname.substring(0, menuname.length() - 4)) + "\"").show();
      }

      @Order(10)
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
    public class MenuWithKeyStrokeMenu extends AbstractMenu {

      @Override
      protected String getConfiguredKeyStroke() {
        return "alt-m";
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
    public class OptionsMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Options");
      }

      @Override
      protected void execAction() {
        OptionsForm form = new OptionsForm();
        form.startNew();
      }
    }
  }

  @Order(30)
  public class HelpMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("HelpMenu");
    }

    @Order(10)
    public class AboutMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("AboutMenu");
      }

      @Override
      protected void execAction() {
        ScoutInfoForm form = new ScoutInfoForm();
        form.startModify();
      }
    }
  }

  @Order(10)
  public class RefreshOutlineKeyStroke extends AbstractKeyStroke {

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F5;
    }

    @Override
    protected void execAction() {
      if (getOutline() != null) {
        IPage<?> page = getOutline().getActivePage();
        if (page != null) {
          page.reloadPage();
        }
      }
    }
  }

  @Order(10)
  public class SimpleWidgetsOutlineViewButton extends AbstractOutlineViewButton {
    public SimpleWidgetsOutlineViewButton() {
      super(Desktop.this, SimpleWidgetsOutline.class);
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F4;
    }
  }

  @Order(20)
  public class AdvancedWidgetsOutlineViewButton extends AbstractOutlineViewButton {
    public AdvancedWidgetsOutlineViewButton() {
      super(Desktop.this, AdvancedWidgetsOutline.class);
    }
  }

  @Order(30)
  public class LayoutWidgetsOutlineViewButton extends AbstractOutlineViewButton {
    public LayoutWidgetsOutlineViewButton() {
      super(Desktop.this, LayoutWidgetsOutline.class);
    }
  }
}
