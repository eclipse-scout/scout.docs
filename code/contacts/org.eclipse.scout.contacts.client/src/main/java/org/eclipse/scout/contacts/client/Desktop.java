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
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.AbstractFormMenu;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.config.PlatformConfigProperties.ApplicationNameProperty;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.ISession;
import org.eclipse.scout.rt.shared.TEXTS;

//tag::DesktopInit[]
//tag::quickAccessMenu[]
public class Desktop extends AbstractDesktop {

  //end::quickAccessMenu[]
  //end::DesktopInit[]
  @Override
  protected String getConfiguredTitle() {
    return CONFIG.getPropertyValue(ApplicationNameProperty.class);
  }

  @Override
  protected String getConfiguredLogoId() {
    return "application_logo";
  }

  //tag::getConfiguredOutlines[]
  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    return CollectionUtility.<Class<? extends IOutline>> arrayList(ContactOutline.class, SearchOutline.class);
  }
  //end::getConfiguredOutlines[]

  @Override
  protected void execDefaultView() {
    setOutline(ContactOutline.class);
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

  // tag::DesktopInit[]
  // outline buttons of the application
  @Order(1)
  public class ContactOutlineViewButton extends AbstractOutlineViewButton {
    //end::DesktopInit[]

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
    //tag::DesktopInit[]
  }

  @Order(2)
  public class SearchOutlineViewButton extends AbstractOutlineViewButton {
    //end::DesktopInit[]

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
    //tag::DesktopInit[]
  }

  // top level menus for the header area of the application
  //tag::quickAccessMenu[]
  @Order(10)
  public class QuickAccessMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("QuickAccess");
    }
    //end::quickAccessMenu[]
    //end::DesktopInit[]

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F10;
    }
    //tag::quickAccessMenu[]

    @Order(10)
    public class NewPersonMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewPersonMenu");
      }

      @Override
      protected void execAction() {
        new PersonForm().startNew();
      }
    }
    //end::quickAccessMenu[]

    @Order(20)
    public class NewOrganizationMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet();
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewOrganizationMenu");
      }

      @Override
      protected void execAction() {
        new OrganizationForm().startNew();
      }
    }
    //tag::quickAccessMenu[]
    //tag::DesktopInit[]
  }
  //end::quickAccessMenu[]

  //tag::OptionsMenu[]
  @Order(20)
  public class OptionsMenu extends AbstractFormMenu<OptionsForm> { // <1>

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Options");
    }

    @Override
    protected String getConfiguredIconId() {
      return AbstractIcons.Gear;
    }
    //end::DesktopInit[]

    //end::OptionsMenu[]
    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F11;
    }

    @Override
    protected Class<OptionsForm> getConfiguredForm() {
      return OptionsForm.class;
    }
    //tag::OptionsMenu[]
    //tag::DesktopInit[]
  }
  //end::OptionsMenu[]

  @Order(30)
  public class UserMenu extends AbstractFormMenu<UserForm> { // <2>

    @Override
    protected String getConfiguredIconId() {
      return AbstractIcons.Person;
    }
    //end::DesktopInit[]

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F12;
    }

    @Override
    protected void execInitAction() {
      setText(ISession.CURRENT.get().getUserId());
    }

    @Override
    protected Class<UserForm> getConfiguredForm() {
      return UserForm.class;
    }
    //tag::DesktopInit[]
  }
  //tag::quickAccessMenu[]
}
//end::DesktopInit[]
//end::quickAccessMenu[]
