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
package org.eclipse.scout.contacts.client;

import java.util.List;
import java.util.Set;

import org.eclipse.scout.contacts.client.common.SearchOutline;
import org.eclipse.scout.contacts.client.contact.ContactOutline;
import org.eclipse.scout.contacts.client.organization.OrganizationForm;
import org.eclipse.scout.contacts.client.person.PersonForm;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractFormToolButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.config.PlatformConfigProperties.ApplicationNameProperty;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.ISession;
import org.eclipse.scout.rt.shared.TEXTS;

public class Desktop extends AbstractDesktop {

  @Override
  protected String getConfiguredTitle() {
    return CONFIG.getPropertyValue(ApplicationNameProperty.class);
  }

  @Override
  protected String getConfiguredApplicationLogoId() {
    return "application_logo";
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

  @Order(1)
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

  @Order(2)
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

  @Order(1)
  public class QuickAccessMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("QuickAccess");
    }

    @Order(10)
    public class PersonNewMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet();
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("CreateNewPersonMenu");
      }

      @Override
      protected void execAction() {
        new PersonForm().startNew();
      }
    }

    @Order(20)
    public class OrganizationNewMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet();
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("CreateNewOrganizationMenu");
      }

      @Override
      protected void execAction() {
        new OrganizationForm().startNew();
      }
    }
  }

  @Order(2)
  public class OptionsToolButton extends AbstractFormToolButton<OptionsForm> {

    @Override
    protected String getConfiguredIconId() {
      return AbstractIcons.Gear;
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F11;
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Options");
    }

    @Override
    protected Class<OptionsForm> getConfiguredForm() {
      return OptionsForm.class;
    }
  }

  @Order(3)
  public class UserMenuToolButton extends AbstractFormToolButton<UserForm> {

    @Override
    protected String getConfiguredIconId() {
      return AbstractIcons.Person;
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F12;
    }

    @Override
    protected String getConfiguredText() {
      return "";
    }

    @Override
    protected void execInitAction() {
      setText(ISession.CURRENT.get().getUserId());
    }

    @Override
    protected Class<UserForm> getConfiguredForm() {
      return UserForm.class;
    }
  }
}
