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

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.browserfield.AbstractBrowserField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ResourceBase;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.BrowserField;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.BsiSoftwareButton;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.EclipseScoutButton;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.SetFileBox.BinaryResourceField;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.SetFileBox.SetFileButton;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.SetUrlBox.SetUrlButton;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.SetUrlBox.URLField;
import org.eclipse.scout.widgets.shared.Icons;
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

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public LinksBox getLinksBox() {
    return getFieldByClass(LinksBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public URLField getURLField() {
    return getFieldByClass(URLField.class);
  }

  public BinaryResourceField getBinaryResourceField() {
    return getFieldByClass(BinaryResourceField.class);
  }

  public SetUrlButton getSetUrlButton() {
    return getFieldByClass(SetUrlButton.class);
  }

  public SetFileButton getSetFileButton() {
    return getFieldByClass(SetFileButton.class);
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
          return 10;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
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
        protected void execPostMessage(String data, String origin) {
          LOG.info("Received post-message: data={}, origin={}", data, origin);
        }
      }

      @Order(22)
      public class SetUrlBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10)
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

        @Order(20)
        public class SetUrlButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return "Set";
          }

          @Override
          protected void execClickAction() {
            getBrowserField().setLocation(getURLField().getValue());
          }
        }
      }

      @Order(23)
      public class SetFileBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10)
        public class BinaryResourceField extends AbstractFileChooserField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("File");
          }

          @Override
          protected int getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }
        }

        @Order(60)
        public class SetFileButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return "Set";
          }

          @Override
          protected void execClickAction() {
            getBrowserField().setBinaryResource(getBinaryResourceField().getValue());
          }
        }
      }

      @Order(25)
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
          protected String getConfiguredIconId() {
            return Icons.World;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            getURLField().setValue("http://www.bing.com/search?q=Eclipse%20Scout");
            getSetUrlButton().doClick();
          }
        }

        @Order(20)
        public class BsiSoftwareButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "www.bsi-software.com";
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.World;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            getURLField().setValue("http://www.bsi-software.com");
            getSetUrlButton().doClick();
          }
        }

        @Order(20)
        public class TestHtmlButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "BrowserFieldCustomHtml.html";
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.File;
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() {
            try (InputStream in = ResourceBase.class.getResourceAsStream("html/BrowserFieldCustomHtml.html")) {
              BinaryResource resource = new BinaryResource("BrowserFieldCustomHtml.html", IOUtility.readBytes(in));
              getBinaryResourceField().setValue(resource);
              getSetFileButton().doClick();
            }
            catch (IOException e) {
              throw new ProcessingException("resource", e);
            }
          }
        }

        @Order(999)
        public class ClearButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return "(null)";
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.Remove;
          }

          @Override
          protected void execClickAction() {
            getBrowserField().setLocation(null);
          }
        }
      }
    }

    @Order(30)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Order(10)
      public class VisibleField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Visible";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execChangedValue() {
          getBrowserField().setVisible(isChecked());
        }

        @Override
        protected void execInitField() {
          setChecked(getBrowserField().isVisible());
        }
      }

      @Order(20)
      public class EnabledField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Enabled";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execChangedValue() {
          getBrowserField().setEnabled(isChecked());
        }

        @Override
        protected void execInitField() {
          setChecked(getBrowserField().isEnabled());
        }
      }

      @Order(30)
      public class ScrollableField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Scrollbar enabled";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execChangedValue() {
          getBrowserField().setScrollBarEnabled(isChecked());
        }

        @Override
        protected void execInitField() {
          setChecked(getBrowserField().isScrollBarEnabled());
        }
      }

      @Order(50)
      public class ShowInExternalWindowField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Show in external window";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredEnabled() {
          return false; // changing this flag at runtime causes UI errors in 6.0
        }

        @Override
        protected void execChangedValue() {
          getBrowserField().setShowInExternalWindow(isChecked());
        }

        @Override
        protected void execInitField() {
          setChecked(getBrowserField().isShowInExternalWindow());
        }
      }

      @Order(60)
      public class AutoCloseExternalWindowField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Auto close external window";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredEnabled() {
          return false; // changing this flag at runtime causes UI errors in 6.0
        }

        @Override
        protected void execChangedValue() {
          getBrowserField().setAutoCloseExternalWindow(isChecked());
        }

        @Override
        protected void execInitField() {
          setChecked(getBrowserField().isAutoCloseExternalWindow());
        }
      }

      @Order(99)
      public class SandboxPermissionsField extends AbstractLinkButton {

        private BrowserFieldSandboxForm m_sandboxForm;

        @Override
        protected String getConfiguredLabel() {
          return "Sandbox settings...";
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() {
          if (m_sandboxForm == null) {
            m_sandboxForm = new BrowserFieldSandboxForm(getBrowserField());
            m_sandboxForm.addFormListener(new FormListener() {
              @Override
              public void formChanged(FormEvent e) {
                if (e.getType() == FormEvent.TYPE_CLOSED) {
                  m_sandboxForm = null;
                }
              }
            });
            m_sandboxForm.start();
          }
          else {
            m_sandboxForm.doClose();
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
