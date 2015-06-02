/**
 *
 */
package org.eclipsescout.contacts.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.shell.IShellService;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.client.ui.forms.RefreshLinkedInTokenForm.MainBox.CancelButton;
import org.eclipsescout.contacts.client.ui.forms.RefreshLinkedInTokenForm.MainBox.OkButton;
import org.eclipsescout.contacts.client.ui.forms.RefreshLinkedInTokenForm.MainBox.OpenAuthURLButton;
import org.eclipsescout.contacts.client.ui.forms.RefreshLinkedInTokenForm.MainBox.TokenBox;
import org.eclipsescout.contacts.client.ui.forms.RefreshLinkedInTokenForm.MainBox.TokenBox.SecurityCodeField;
import org.eclipsescout.contacts.shared.services.ILinkedInService;

/**
 * @author mzi
 */
public class RefreshLinkedInTokenForm extends AbstractForm {

  private Long m_refreshLinkedInTokenNr;
  private String m_token;
  private String m_secret;

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public RefreshLinkedInTokenForm() throws ProcessingException {
    super();
  }

  /**
   * @return the RefreshLinkedInTokenNr
   */
  @FormData
  public Long getRefreshLinkedInTokenNr() {
    return m_refreshLinkedInTokenNr;
  }

  /**
   * @param refreshLinkedInTokenNr
   *          the RefreshLinkedInTokenNr to set
   */
  @FormData
  public void setRefreshLinkedInTokenNr(Long refreshLinkedInTokenNr) {
    m_refreshLinkedInTokenNr = refreshLinkedInTokenNr;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("RefreshLinkedInToken");
  }

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  /**
   * @return the CancelButton
   */
  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  /**
   * @return the MainBox
   */
  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the OkButton
   */
  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  /**
   * @return the OpenAuthURLButton
   */
  public OpenAuthURLButton getOpenAuthURLButton() {
    return getFieldByClass(OpenAuthURLButton.class);
  }

  /**
   * @return the SecurityCodeField
   */
  public SecurityCodeField getSecurityCodeField() {
    return getFieldByClass(SecurityCodeField.class);
  }

  /**
   * @return the TokenBox
   */
  public TokenBox getTokenBox() {
    return getFieldByClass(TokenBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class TokenBox extends AbstractGroupBox {

      @Order(10.0)
      public class SecurityCodeField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SecurityCode");
        }
      }
    }

    @Order(20.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(30.0)
    public class CancelButton extends AbstractCancelButton {
    }

    @Order(40.0)
    public class OpenAuthURLButton extends AbstractLinkButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("OpenAuthURL");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        String authUrl = SERVICES.getService(ILinkedInService.class).getAuthUrl();
        SERVICES.getService(IShellService.class).shellOpen(authUrl);
        getSecurityCodeField().setEnabled(true);
      }
    }
  }

  public class NewHandler extends AbstractFormHandler {
  }

  /**
   * @return the Token
   */
  @FormData
  public String getToken() {
    return m_token;
  }

  /**
   * @param token
   *          the Token to set
   */
  @FormData
  public void setToken(String token) {
    m_token = token;
  }

  /**
   * @return the Secret
   */
  @FormData
  public String getSecret() {
    return m_secret;
  }

  /**
   * @param secret
   *          the Secret to set
   */
  @FormData
  public void setSecret(String secret) {
    m_secret = secret;
  }
}
