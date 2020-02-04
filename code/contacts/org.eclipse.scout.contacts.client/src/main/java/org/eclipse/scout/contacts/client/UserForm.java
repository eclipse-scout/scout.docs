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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.eclipse.scout.contacts.client.ConfigProperties.UserDomainProperty;
import org.eclipse.scout.contacts.client.UserForm.MainBox.GroupBox.HtmlField;
import org.eclipse.scout.contacts.shared.common.IResetDataStoreService;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlElement;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.HexUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.ISession;
import org.eclipse.scout.rt.shared.http.DefaultHttpTransportManager;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;

@ClassId("3981a3a4-3e39-454b-b24c-e316768f9d2f")
public class UserForm extends AbstractForm {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("User");
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public HtmlField getHtmlField() {
    return getFieldByClass(HtmlField.class);
  }

  @Order(10)
  @ClassId("b8c19ddd-5bf8-43b3-85e5-cc7ffed0c82d")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Override
    protected int getConfiguredWidthInPixel() {
      return 340;
    }

    @Order(10)
    @ClassId("bedd6514-196e-4537-b868-e8199877aff3")
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      @ClassId("8dddee1d-1ba5-4f08-9e27-b2adf9c6b122")
      public class HtmlField extends AbstractHtmlField {

        @Override
        protected void execInitField() {
          setValue(createHtmlContent());
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredScrollBarEnabled() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @Override
        protected boolean getConfiguredGridUseUiHeight() {
          return false;
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
          return false;
        }

        @Override
        protected String getConfiguredCssClass() {
          return "contacts-user-form";
        }

        @Override
        protected int getConfiguredGridH() {
          return 3;
        }

        @Override
        protected void execAppLinkAction(String ref) {
          switch (ref) {
            case "application-info":
              ScoutInfoForm form = new ScoutInfoForm();
              form.startModify();
              break;
            case "reset-data":
              BEANS.get(IResetDataStoreService.class).resetDataStore();
              break;
            case "logout":
              ClientSessionProvider.currentSession(ClientSession.class).stop();
              break;
          }
        }
      }
    }
  }

  private String createHtmlContent() {
    return HTML.div(
        HTML.div(getGravatarImage()).cssClass("contacts-user-form-image"),
        HTML.div(
            HTML.div(HTML.appLink("application-info", TEXTS.get("ApplicationInformation"))).cssClass("contacts-user-form-link-row"),
            HTML.div(HTML.appLink("reset-data", TEXTS.get("ResetData"))).cssClass("contacts-user-form-link-row"),
            HTML.div(HTML.appLink("logout", TEXTS.get("Logout"))).cssClass("contacts-user-form-link-row"))
            .cssClass("contacts-user-form-link-box"))
        .toHtml();
  }

  private IHtmlElement getGravatarImage() {
    String userDomain = CONFIG.getPropertyValue(UserDomainProperty.class);
    if (userDomain == null) {
      return HTML.imgByIconId(Icons.User);
    }

    try {
      // Get the email address of the user
      String emailAddress = StringUtility.trim(String.format("%s@%s", ISession.CURRENT.get().getUserId(), userDomain)).toLowerCase();

      // Calculate MD5 Hash of email address
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.reset();
      String emailMd5Hash = HexUtility.encode(messageDigest.digest(emailAddress.getBytes(StandardCharsets.UTF_8)));

      // Get the Gravatar image
      GenericUrl url = new GenericUrl("http://www.gravatar.com/avatar/" + emailMd5Hash + "?d=404");
      HttpRequest req = BEANS.get(DefaultHttpTransportManager.class).getHttpRequestFactory().buildHeadRequest(url);
      HttpResponse resp = req.execute();
      try {
        if (resp.getStatusCode() == 200) {
          return HTML.div(HTML.img(url.toString()));
        }
      }
      finally {
        resp.disconnect();
      }
    }
    catch (IOException | NoSuchAlgorithmException e) {
      // NOOP
    }
    return HTML.imgByIconId(Icons.User);
  }
}
