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
package org.eclipse.scout.widgets.old.client.ui.forms;

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
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.BrowserField;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.BsiSoftwareButton;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.EclipseScoutButton;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.RefreshButton;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.URLField;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The following code-snippet shows what the content of an IFRAME must do to call the execPostMessage() callback. This
 * will only work, when the IFRAME is not restricted by the sandbox attribute.
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
  private static final Logger LOG = LoggerFactory.getLogger(BrowserFieldForm.class);

  public BrowserFieldForm() {
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
  public void startPageForm() {
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

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected void execInitField() {
      setStatusVisible(false);
    }

    @Order(10)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
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
        protected void execChangedMasterValue(Object newMasterValue) {
          String url = (String) newMasterValue;
          setLocation(url);
        }

        @Override
        protected void execPostMessage(String data, String origin) {
          LOG.info("Received post-message: data={}, origin={}", data, origin);
        }
      }

      @Order(20)
      public class LinksBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10)
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
          protected void execClickAction() {
            getURLField().setValue("http://www.bing.com/search?q=Eclipse%20Scout");
          }
        }

        @Order(20)
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
          protected void execClickAction() {
            getURLField().setValue("http://www.bsi-software.com");
          }
        }

        @Order(40)
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

        @Order(50)
        public class RefreshButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Refresh");
          }

          @Override
          protected void execClickAction() {
            getBrowserField().setLocation(getURLField().getValue());
          }
        }
      }
    }

    @Order(40)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
