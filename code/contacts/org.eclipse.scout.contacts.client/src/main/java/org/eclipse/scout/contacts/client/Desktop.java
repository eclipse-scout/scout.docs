/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.client;

import java.util.List;
import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.contacts.client.common.SearchOutline;
import org.eclipse.scout.contacts.client.contact.ContactOutline;
import org.eclipse.scout.contacts.client.organization.OrganizationForm;
import org.eclipse.scout.contacts.client.person.PersonForm;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.bookmark.menu.AbstractBookmarkMenu;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.config.PlatformConfigProperties.ApplicationNameProperty;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;

public class Desktop extends AbstractDesktop {

  @Override
  protected String getConfiguredTitle() {
    return CONFIG.getPropertyValue(ApplicationNameProperty.class);
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    return CollectionUtility.<Class<? extends IOutline>> arrayList(ContactOutline.class, SearchOutline.class);
  }

  @Override
  protected void execGuiAttached() {
    super.execGuiAttached();
    setOutline(ContactOutline.class);
  }

  @Override
  protected void setDesktopStyle(DesktopStyle desktopStyle) {
    super.setDesktopStyle(desktopStyle);
  }

  @Order(1_000)
  public class FileMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("File");
    }

    @Order(1000.0)
    public class NewMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("New");
      }

      @Order(1000.0)
      public class PersonMenu extends AbstractMenu {

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet();
        }

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Person");
        }

        @Override
        protected void execAction() {
          new PersonForm().startNew();
        }
      }

      @Order(2000.0)
      public class OrganizationMenu extends AbstractMenu {

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet();
        }

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Organization");
        }

        @Override
        protected void execAction() {
          new OrganizationForm().startNew();
        }
      }
    }

    @Order(2000.0)
    public class ExitMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Exit");
      }

      @Override
      protected void execAction() {
        ClientSessionProvider.currentSession(ClientSession.class).stop();
      }
    }
  }

  @Order(2_000)
  public class BookmarkMenu extends AbstractBookmarkMenu {
    public BookmarkMenu() {
      super(Desktop.this);
    }
  }

  @Order(3_000)
  public class HelpMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Help");
    }

    @Order(500.0)
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

    @Order(1000)
    public class AboutMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("About");
      }

      @Override
      protected void execAction() {
        ScoutInfoForm form = new ScoutInfoForm();
        form.startModify();
      }
    }
  }

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

  @Order(1_000.0)
  public class ContactOutlineViewButton extends AbstractOutlineViewButton {

    public ContactOutlineViewButton() {
      this(ContactOutline.class);
    }

    protected ContactOutlineViewButton(Class<? extends ContactOutline> outlineClass) {
      super(Desktop.this, outlineClass);
    }

    @Override
    protected DisplayStyle getConfiguredDisplayStyle() {
      return DisplayStyle.MENU;
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return "ctrl-shift-c";
    }
  }

  @Order(2_000.0)
  public class SearchOutlineViewButton extends AbstractOutlineViewButton {

    public SearchOutlineViewButton() {
      this(SearchOutline.class);
    }

    protected SearchOutlineViewButton(Class<? extends SearchOutline> outlineClass) {
      super(Desktop.this, outlineClass);
    }

    @Override
    protected DisplayStyle getConfiguredDisplayStyle() {
      return DisplayStyle.TAB;
    }

    @Override
    protected String getConfiguredIconId() {
      return AbstractIcons.Search;
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F3;
    }
  }
}
