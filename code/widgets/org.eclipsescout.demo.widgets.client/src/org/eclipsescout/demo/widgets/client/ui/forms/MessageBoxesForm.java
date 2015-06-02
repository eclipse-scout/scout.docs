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

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxesForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxesForm.MainBox.CustomMessageBox;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxesForm.MainBox.CustomMessageBox.DeleteConfirmationMessageButton;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxesForm.MainBox.CustomMessageBox.ProcessingExceptionButton;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxesForm.MainBox.CustomMessageBox.VetoExceptionButton;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxesForm.MainBox.StandardMessageBoxesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxesForm.MainBox.StandardMessageBoxesBox.MessageBoxWithHiddenTextButton;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxesForm.MainBox.StandardMessageBoxesBox.MessageBoxWithOkButton;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxesForm.MainBox.StandardMessageBoxesBox.MessageBoxWithYesNoCancelOptionButton;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxesForm.MainBox.StandardMessageBoxesBox.MessageBoxWithYesNoOptionButton;

public class MessageBoxesForm extends AbstractForm implements IPageForm {

  public MessageBoxesForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("MessageBoxes");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public CustomMessageBox getCustomMessageBox() {
    return getFieldByClass(CustomMessageBox.class);
  }

  public DeleteConfirmationMessageButton getDeleteConfirmationMessageButton() {
    return getFieldByClass(DeleteConfirmationMessageButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MessageBoxWithHiddenTextButton getMessageBoxWithHiddenTextButton() {
    return getFieldByClass(MessageBoxWithHiddenTextButton.class);
  }

  public MessageBoxWithOkButton getMessageBoxWithOkButton() {
    return getFieldByClass(MessageBoxWithOkButton.class);
  }

  public MessageBoxWithYesNoCancelOptionButton getMessageBoxWithYesNoCancelOptionButton() {
    return getFieldByClass(MessageBoxWithYesNoCancelOptionButton.class);
  }

  public MessageBoxWithYesNoOptionButton getMessageBoxWithYesNoOptionButton() {
    return getFieldByClass(MessageBoxWithYesNoOptionButton.class);
  }

  public ProcessingExceptionButton getProcessingExceptionButton() {
    return getFieldByClass(ProcessingExceptionButton.class);
  }

  public StandardMessageBoxesBox getStandardMessageBoxesBox() {
    return getFieldByClass(StandardMessageBoxesBox.class);
  }

  public VetoExceptionButton getVetoExceptionButton() {
    return getFieldByClass(VetoExceptionButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class StandardMessageBoxesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("StandardMessageBoxes");
      }

      @Order(10.0)
      public class MessageBoxWithOkButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MessageBoxWithOkButton");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          MessageBox.showOkMessage("Ok", null, "This is a MessageBox with an Ok-Button");
        }
      }

      @Order(20.0)
      public class MessageBoxWithYesNoOptionButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MessageBoxWithYesNoOption");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          MessageBox.showYesNoMessage("Yes-No-Option", "This is a MessageBox with a Yes-No-Option", "Press \"Yes\" or \"No\"");
        }
      }

      @Order(30.0)
      public class MessageBoxWithYesNoCancelOptionButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MessageBoxWithYesNoCancelOption");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          MessageBox.showYesNoCancelMessage("Yes-No-Cancel-Option", "This is a MessageBox with a Yes-No-Option", "Press \"Yes\", \"No\" or \"Cancel\"");
        }
      }

      @Order(40.0)
      public class MessageBoxWithHiddenTextButton extends AbstractLinkButton {

        @Override
        protected boolean getConfiguredEnabled() {
          return UserAgentUtility.isRichClient();
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MessageBoxWithHiddenText");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          MessageBox msgbox = new MessageBox("MessageBox with hidden text", "This MessageBox has a hidden text", "click on copy or press ctrl+c to get the hidden text in your clipboard", TEXTS.get("YesButton"), TEXTS.get("NoButton"), TEXTS.get("CloseButton"), TEXTS.get("Lorem"), null);
          msgbox.startMessageBox();
        }
      }
    }

    @Order(20.0)
    public class CustomMessageBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("OtherMessageBox");
      }

      @Order(10.0)
      public class DeleteConfirmationMessageButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DeleteConfirmationMessage");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          MessageBox.showDeleteConfirmationMessage("Option", new String[]{"Item1", "Item2", "Item3"});
        }
      }

      @Order(20.0)
      public class VetoExceptionButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VetoException");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          throw new VetoException("This is a VetoException");
        }
      }

      @Order(30.0)
      public class ProcessingExceptionButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ProcessingException");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          throw new ProcessingException("This is a ProcessingException");
        }
      }
    }

    @Order(100.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
