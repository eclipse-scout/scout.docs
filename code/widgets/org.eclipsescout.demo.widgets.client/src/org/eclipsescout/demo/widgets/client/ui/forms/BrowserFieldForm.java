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
package org.eclipsescout.demo.widgets.client.ui.forms;

import org.eclipse.scout.commons.StringUtility;
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
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.BrowserField;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.BsiagButton;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.EclipseScoutButton;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.RefreshButton;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm.MainBox.ExamplesBox.LinksBox.URLField;

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

  public BsiagButton getBsiagButton() {
    return getFieldByClass(BsiagButton.class);
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
        protected boolean getConfiguredAutoDisplayText() {
          return false;
        }

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
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
          String url = (String) newMasterValue;

          setValue(null);
          if (!StringUtility.isNullOrEmpty(url)) {
            setLocation(url);
          }
        }

        @Override
        protected void execLocationChanged(String location, String path, boolean local) throws ProcessingException {
          LOG.info("Location changed to " + location);
        }
      }

      @Order(20.0)
      public class LinksBox extends AbstractSequenceBox {

        @Order(10.0)
        public class EclipseScoutButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Www.eclipse.orgscout");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            getURLField().setValue(TEXTS.get("Www.eclipse.orgscout"));
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setVisible(UserAgentUtility.isRichClient());
          }
        }

        @Order(20.0)
        public class BsiagButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Www.bsi-software.com");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            getURLField().setValue(TEXTS.get("Www.bsi-software.com"));
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

          @Override
          protected void execChangedValue() throws ProcessingException {
            getRefreshButton().doClick();
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
            getBrowserField().setValue(null);
            getBrowserField().setLocation(getURLField().getValue());
          }
        }
      }
    }

    @Order(30.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
