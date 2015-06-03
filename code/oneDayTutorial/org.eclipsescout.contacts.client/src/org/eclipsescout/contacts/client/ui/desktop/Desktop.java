package org.eclipsescout.contacts.client.ui.desktop;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ClientSyncJob;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.bookmark.menu.AbstractBookmarkMenu;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.client.ui.form.outline.DefaultOutlineTableForm;
import org.eclipse.scout.rt.client.ui.form.outline.DefaultOutlineTreeForm;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.desktop.AbstractExtensibleDesktop;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.client.ClientSession;
import org.eclipsescout.contacts.client.ui.desktop.outlines.StandardOutline;
import org.eclipsescout.contacts.client.ui.forms.RefreshLinkedInTokenForm;
import org.eclipsescout.contacts.shared.Icons;
import org.eclipsescout.contacts.shared.services.ILinkedInService;
import java.io.File;
import org.eclipse.scout.rt.docx4j.client.ScoutXlsxSpreadsheetAdapter;
import org.eclipse.scout.rt.shared.services.common.shell.IShellService;

public class Desktop extends AbstractExtensibleDesktop implements IDesktop {
  private static IScoutLogger logger = ScoutLogManager.getLogger(Desktop.class);

  public Desktop() {
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    List<Class<? extends IOutline>> outlines = new ArrayList<Class<? extends IOutline>>();
    outlines.add(StandardOutline.class);
    return outlines;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("MyContacts");
  }

  @Override
  protected void execOpened() throws ProcessingException {
    //If it is a mobile or tablet device, the DesktopExtension in the mobile plugin takes care of starting the correct forms.
    if (!UserAgentUtility.isDesktopDevice()) {
      return;
    }

    // outline tree
    DefaultOutlineTreeForm treeForm = new DefaultOutlineTreeForm();
    treeForm.setIconId(Icons.EclipseScout);
    treeForm.startView();

    //outline table
    DefaultOutlineTableForm tableForm = new DefaultOutlineTableForm();
    tableForm.setIconId(Icons.EclipseScout);
    tableForm.startView();

    IOutline firstOutline = CollectionUtility.firstElement(getAvailableOutlines());
    if (firstOutline != null) {
      setOutline(firstOutline);
    }

  }

  @Order(10.0)
  public class FileMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("FileMenu");
    }

    @Order(10.0)
    public class RefreshLinkedInToken_Menu extends AbstractExtensibleMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet();
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("RefreshLinkedInToken0");
      }

      @Override
      protected void execAction() throws ProcessingException {
        RefreshLinkedInTokenForm form = new RefreshLinkedInTokenForm();
        form.startNew();
        form.waitFor();

        if (form.isFormStored()) {
          String securityCode = form.getSecurityCodeField().getValue();
          SERVICES.getService(ILinkedInService.class)
              .refreshToken(securityCode);
        }
      }
    }

    @Order(20.0)
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
    public class UpdateLinkedInContactsMenu extends AbstractExtensibleMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("UpdateLinkedInContacts");
      }

      @Override
      protected void execAction() throws ProcessingException {
        Desktop.this.setStatusText(TEXTS.get("ImportingLinkedIn"));
        SERVICES.getService(ILinkedInService.class).updateContacts();
        Desktop.this.setStatusText(null);
      }
    }

    @Order(20.0)
    public class ExportToExcelMenu extends AbstractExtensibleMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ExportToExcelMenu");
      }

      @Override
      protected void execAction() throws ProcessingException {
        if (getOutline() != null && getOutline().getActivePage() != null) {
          ScoutXlsxSpreadsheetAdapter s = new ScoutXlsxSpreadsheetAdapter();
          File xlsx = s.exportPage(null, 0, 0, getOutline().getActivePage());
          SERVICES.getService(IShellService.class).shellOpen(xlsx.getAbsolutePath());
        }
      }
    }
  }

  @Order(25)
  public class BookmarkMenu extends AbstractBookmarkMenu {
    public BookmarkMenu() {
      super(Desktop.this);
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

  @Order(10.0)
  public class StandardOutlineViewButton extends AbstractOutlineViewButton {

    /**
     *
     */
    public StandardOutlineViewButton() {
      super(Desktop.this, StandardOutline.class);
    }
  }
}
