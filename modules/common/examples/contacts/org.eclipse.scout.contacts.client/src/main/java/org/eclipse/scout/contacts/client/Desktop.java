/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.contacts.client;

import java.util.List;
import java.util.Set;

import org.eclipse.scout.contacts.client.common.SearchOutline;
import org.eclipse.scout.contacts.client.contact.ContactOutline;
import org.eclipse.scout.contacts.client.organization.OrganizationForm;
import org.eclipse.scout.contacts.client.person.PersonForm;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.AbstractFormMenu;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.config.PlatformConfigProperties.ApplicationNameProperty;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.ISession;

//tag::DesktopInit[]
//tag::quickAccessMenu[]
@ClassId("70eda4c8-5aed-4e61-85b4-6098edad8416")
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

  // tag::DesktopInit[]
  // outline buttons of the application
  @Order(1)
  @ClassId("9405937b-66e8-491a-831d-69adca724b90")
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
  @ClassId("55febc84-ad6d-4ee8-9963-d1d40169a63a")
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
  @ClassId("50df7a9d-dd3c-40a3-abc4-4619eff8d841")
  public class QuickAccessMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("QuickAccess");
    }
    //end::quickAccessMenu[]
    //end::DesktopInit[]

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F9;
    }
    //tag::quickAccessMenu[]

    @Order(10)
    @ClassId("effb3b69-f488-4aed-8923-d430a5f1fd97")
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
    @ClassId("d57304ef-aefe-4d36-a10f-66d658e0e535")
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
  @ClassId("4fce42bf-85f9-4892-96a2-2e89e18eeaee")
  public class OptionsMenu extends AbstractFormMenu<OptionsForm> { // <1>

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Options");
    }

    @Override
    protected String getConfiguredIconId() {
      return Icons.Gear;
    }

    //end::DesktopInit[]
    //end::OptionsMenu[]
    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F10;
    }
    //tag::OptionsMenu[]

    //tag::DesktopInit[]
    @Override
    protected Class<OptionsForm> getConfiguredForm() {
      return OptionsForm.class;
    }

  }
  //end::OptionsMenu[]

  @Order(30)
  @ClassId("8dbfbe9d-0382-471a-ae43-3178f7a9e720")
  public class UserMenu extends AbstractFormMenu<UserForm> { // <2>

    @Override
    protected String getConfiguredIconId() {
      return Icons.PersonSolid;
    }

    @Override
    protected String getConfiguredCssClass() {
      return "profile-menu";
    }

    //end::DesktopInit[]

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F11;
    }

    @Override
    protected void execInitAction() {
      setText(ISession.CURRENT.get().getUserId());
    }

    //tag::DesktopInit[]
    @Override
    protected Class<UserForm> getConfiguredForm() {
      return UserForm.class;
    }

  }
  //tag::quickAccessMenu[]
}
//end::DesktopInit[]
//end::quickAccessMenu[]
