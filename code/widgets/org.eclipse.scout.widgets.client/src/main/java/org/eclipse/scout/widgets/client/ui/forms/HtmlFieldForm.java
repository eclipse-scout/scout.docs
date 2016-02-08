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
package org.eclipse.scout.widgets.client.ui.forms;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ResourceBase;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.HtmlFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.HtmlFieldForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.client.ui.forms.HtmlFieldForm.MainBox.GroupBox.BlankButton;
import org.eclipse.scout.widgets.client.ui.forms.HtmlFieldForm.MainBox.GroupBox.ScoutHtmlButton;
import org.eclipse.scout.widgets.client.ui.forms.HtmlFieldForm.MainBox.HTMLField;

@Order(6000.0)
public class HtmlFieldForm extends AbstractForm implements IAdvancedExampleForm {

  public HtmlFieldForm() {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("HTMLField");
  }

  @Override
  protected void execInitForm() {
    loadFile("ScoutHtml.html", Collections.<BinaryResource> emptySet());
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public BlankButton getBlankButton() {
    return getFieldByClass(BlankButton.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public HTMLField getHTMLField() {
    return getFieldByClass(HTMLField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ScoutHtmlButton getScoutHtmlButton() {
    return getFieldByClass(ScoutHtmlButton.class);
  }

  private void loadFile(String simpleName, Collection<? extends BinaryResource> attachments) {
    try {
      String s = IOUtility.getContent(new InputStreamReader(ResourceBase.class.getResource("html/" + simpleName).openStream()));
      getHTMLField().setValue(null);
      getHTMLField().setScrollToAnchor(null);
      getHTMLField().setAttachments(attachments);
      getHTMLField().setValue(s);
    }
    catch (IOException e) {
      throw new ProcessingException("Html-Field can't load file ", e);
    }
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(5)
    public class ActionGroupBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 4;
      }

      @Order(10)
      public class EnabledCheckBox extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Enabled";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() {
          setValue(true);
        }

        @Override
        protected void execChangedValue() {
          getHTMLField().setEnabled(getValue());
        }
      }

      @Order(20)
      public class ScrollToAnchorField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("JumpToAnchor");
        }

        @Override
        protected void execChangedValue() {
          getHTMLField().setScrollToAnchor(getValue());
        }
      }

      @Order(30)
      public class ScrollToEndButton extends AbstractButton {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ScrollToEnd");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_LINK;
        }

        @Override
        protected void execClickAction() {
          getHTMLField().scrollToEnd();
        }
      }
    }

    @Order(10)
    public class HTMLField extends AbstractHtmlField {

      @Override
      protected int getConfiguredGridH() {
        return 12;
      }

      @Override
      protected int getConfiguredGridW() {
        return 2;
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
      protected void execAppLinkAction(String ref) {
        MessageBoxes.createOk().withHeader(TEXTS.get("LocalUrlClicked")).withBody(TEXTS.get("Parameters") + ":\n" + ref).show();
      }
    }

    @Order(20)
    public class GroupBox extends AbstractGroupBox {
      @Order(30)
      public class BlankButton extends AbstractLinkButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Blank");
        }

        @Override
        protected void execClickAction() {
          getHTMLField().setAttachments(null);
          getHTMLField().setValue(null);
        }
      }

      @Order(60)
      public class CustomHTMLButton extends AbstractLinkButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CustomHtml");
        }

        @Override
        protected void execClickAction() {
          URL url = ResourceBase.class.getResource("icons/eclipse_scout_logo.png");
          byte[] content;
          try {
            content = IOUtility.getContent(new BufferedInputStream(url.openStream()));
            BinaryResource file = new BinaryResource("eclipse_scout_logo.png", content);
            loadFile("HtmlFieldCustomHtml.html", Collections.<BinaryResource> singleton(file));
          }
          catch (IOException e) {
            throw new ProcessingException("", e);
          }
        }
      }

      @Order(40)
      public class ScoutHtmlButton extends AbstractLinkButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ScoutHtml");
        }

        @Override
        protected void execClickAction() {
          loadFile("ScoutHtml.html", Collections.<BinaryResource> emptySet());
        }
      }

      @Order(70)
      public class ALotOfContentHtmlButton extends AbstractLinkButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ALotOfContentHtmlWithAnchor");
        }

        @Override
        protected void execClickAction() {
          loadFile("ALotOfContent.html", Collections.<BinaryResource> emptySet());
        }
      }
    }

    @Order(50)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
