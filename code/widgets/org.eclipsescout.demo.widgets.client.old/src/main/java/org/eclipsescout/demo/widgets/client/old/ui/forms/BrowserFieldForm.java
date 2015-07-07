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
package org.eclipsescout.demo.widgets.client.old.ui.forms;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.browserfield.AbstractBrowserField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.old.ui.forms.BrowserFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.old.ui.forms.BrowserFieldForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.old.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.BrowserField;
import org.eclipsescout.demo.widgets.client.old.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox;
import org.eclipsescout.demo.widgets.client.old.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.BsiSoftwareButton;
import org.eclipsescout.demo.widgets.client.old.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.EclipseScoutButton;
import org.eclipsescout.demo.widgets.client.old.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.RefreshButton;
import org.eclipsescout.demo.widgets.client.old.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.URLField;
import org.eclipsescout.demo.widgets.client.ui.forms.IPageForm;

/**
 * The following code-snippet shows what the content of an IFRAME must do to call the execPostMessage() callback.
 * This will only work, when the IFRAME is not restricted by the sandbox attribute.
 *
 * <pre>
 * &lt;script&gt;
 * function postMessage() {
 *   window.parent.postMessage('hello Scout application!', 'http://localhost:8082');
 * }
 * &lt;/script&gt;
 * &lt;button onclick="postMessage()"&gt;Post message&lt;/button&gt;
 * 
 * The second parameter (targetOrigin) of the postMessage function is important, it points to the domain where the
 * Scout application runs. When the IFRAME content is called from another domain than localhost:8082, the browser
 * will NOT execute the function. You could also use '*' as targetOrigin, when you don't care which domain exactly
 * should be allowed to receive post messages from the IFRAME content.
 * </pre>
 */
public class BrowserFieldForm extends AbstractForm implements IPageForm {
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(BrowserFieldForm.class);

  public BrowserFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("BrowserField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public BrowserField getBrowserField() {
    return getFieldByClass(BrowserField.class);
  }

  public BsiSoftwareButton getBsiagButton() {
    return getFieldByClass(BsiSoftwareButton.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public EclipseScoutButton getEclipseScoutButton() {
    return getFieldByClass(EclipseScoutButton.class);
  }

  /**
   * @return the ExamplesBox
   */
  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public LinksBox getLinksBox() {
    return getFieldByClass(LinksBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public RefreshButton getRefreshButton() {
    return getFieldByClass(RefreshButton.class);
  }

  public URLField getURLField() {
    return getFieldByClass(URLField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected void execInitField() throws ProcessingException {
      setStatusVisible(false);
    }

    @Order(10.0)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10.0)
      public class BrowserField extends AbstractBrowserField {

        @Override
        protected int getConfiguredGridH() {
          return 20;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return BrowserFieldForm.MainBox.ExamplesBox.LinksBox.URLField.class;
        }

        @Override
        protected boolean getConfiguredScrollBarEnabled() {
          return true;
        }

        @Override
        protected boolean getConfiguredSandboxEnabled() {
          return false;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
          String url = (String) newMasterValue;
          setLocation(url);
        }

        @Override
        protected void execPostMessage(String data, String origin) throws ProcessingException {
          LOG.info("Received post-message: data=" + data + " origin=" + origin);
        }
      }

      @Order(20.0)
      public class LinksBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10.0)
        public class EclipseScoutButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "www.bing.com";
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            getURLField().setValue("http://www.bing.com/search?q=Eclipse%20Scout");
          }
        }

        @Order(20.0)
        public class BsiSoftwareButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "www.bsi-software.com";
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            getURLField().setValue("http://www.bsi-software.com");
          }
        }

        @Order(40.0)
        public class URLField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("URL");
          }

          @Override
          protected int getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }
        }

        @Order(50.0)
        public class RefreshButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Refresh");
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            getBrowserField().setLocation(getURLField().getValue());
          }
        }
      }
    }

    @Order(40.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
