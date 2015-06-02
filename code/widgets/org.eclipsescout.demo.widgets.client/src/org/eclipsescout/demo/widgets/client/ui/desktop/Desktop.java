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
package org.eclipsescout.demo.widgets.client.ui.desktop;

import java.util.ArrayList;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ClientSyncJob;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.checkbox.AbstractCheckBoxMenu;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractFormToolButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.client.ui.form.outline.DefaultOutlineTableForm;
import org.eclipse.scout.rt.client.ui.form.outline.DefaultOutlineTreeForm;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.extension.client.ui.desktop.AbstractExtensibleDesktop;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ClientSession;
import org.eclipsescout.demo.widgets.client.ui.desktop.outlines.PagesSearchFormsOutline;
import org.eclipsescout.demo.widgets.client.ui.desktop.outlines.WidgetsOutline;
import org.eclipsescout.demo.widgets.client.ui.forms.ToolButton1Form;
import org.eclipsescout.demo.widgets.client.ui.forms.ToolButton2Form;
import org.eclipsescout.demo.widgets.shared.Icons;

public class Desktop extends AbstractExtensibleDesktop implements IDesktop {

  private static IScoutLogger logger = ScoutLogManager.getLogger(Desktop.class);

  public Desktop() {
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Class<? extends IOutline>[] getConfiguredOutlines() {
    ArrayList<Class> outlines = new ArrayList<Class>();
    outlines.add(WidgetsOutline.class);
    outlines.add(PagesSearchFormsOutline.class);
    return outlines.toArray(new Class[outlines.size()]);
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ScoutWidgetsDemoApp");
  }

  @Override
  protected boolean getConfiguredTrayVisible() {
    return true;
  }

  @Override
  protected void execOpened() throws ProcessingException {
    // outline tree
    DefaultOutlineTreeForm treeForm = new DefaultOutlineTreeForm();
    treeForm.setIconId(Icons.EclipseScout);
    treeForm.startView();

    //outline table
    DefaultOutlineTableForm tableForm = new DefaultOutlineTableForm();
    tableForm.setIconId(Icons.EclipseScout);
    tableForm.startView();

    if (getAvailableOutlines().length > 0) {
      setOutline(getAvailableOutlines()[0]);
    }

  }

  @Order(10.0)
  public class FileMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("FileMenu");
    }

    @Order(100.0)
    public class ExitMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ExitMenu");
      }

      @Override
      public void execAction() throws ProcessingException {
        ClientSyncJob.getCurrentSession(ClientSession.class).stopSession();
      }
    }
  }

  @Order(20.0)
  public class ToolsMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ToolsMenu");
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
        return AbstractIcons.Gears;
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
  }

  @Order(30.0)
  public class HelpMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("HelpMenu");
    }

    @Order(10.0)
    public class AboutMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("AboutMenu");
      }

      @Override
      public void execAction() throws ProcessingException {
        ScoutInfoForm form = new ScoutInfoForm();
        form.startModify();
      }
    }
  }

  @Order(10.0)
  public class TestCasesOutlineViewButton extends AbstractOutlineViewButton {

    public TestCasesOutlineViewButton() {
      super(Desktop.this, WidgetsOutline.class);
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("TestCases");
    }
  }

  @Order(20.0)
  public class PagesSearchFormsOutlineViewButton extends AbstractOutlineViewButton {

    public PagesSearchFormsOutlineViewButton() {
      super(Desktop.this, PagesSearchFormsOutline.class);
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("PagesSearchForms");
    }
  }

  @Order(10.0)
  public class ToolButton1Tool extends AbstractFormToolButton<ToolButton1Form> {

    @Override
    protected String getConfiguredIconId() {
      return Icons.StarYellow;
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ToolButton1");
    }

    @Override
    protected void execAction() throws ProcessingException {
      ToolButton1Form form = new ToolButton1Form();
      decorateForm(form);
      form.startTool();
      setForm(form);
    }
  }

  @Order(20.0)
  public class ToolButton2Tool extends AbstractFormToolButton<ToolButton2Form> {

    @Override
    protected String getConfiguredIconId() {
      return Icons.StarRed;
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ToolButton2");
    }

    @Override
    protected void execAction() throws ProcessingException {
      ToolButton2Form form = new ToolButton2Form();
      decorateForm(form);
      form.startTool();
      setForm(form);
    }
  }

  @Order(10.0)
  public class RefreshOutlineKeyStroke extends AbstractKeyStroke {

    @Override
    protected String getConfiguredKeyStroke() {
      return "f5";
    }

    @Override
    protected void execAction() throws ProcessingException {
      if (getOutline() != null) {
        IPage page = getOutline().getActivePage();
        if (page != null) {
          page.reloadPage();
        }
      }
    }
  }
}
