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

import java.util.Random;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.RandomValueBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.RandomValueBox.fromField;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.RandomValueBox.toField;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.SequenceBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.SequenceBox.OkButton;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.SequenceBox.SomeFieldsHaventAValueField;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm.MainBox.GroupBox.SequenceBox.WhichNumberWillTheComputerFindField;

public class SequenceBoxForm extends AbstractForm implements IPageForm {

  public SequenceBoxForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SequenceBox");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public RandomValueBox getRandomValueBox() {
    return getFieldByClass(RandomValueBox.class);
  }

  public SequenceBox getSequenceBox() {
    return getFieldByClass(SequenceBox.class);
  }

  public SomeFieldsHaventAValueField getSomeFieldsHaventAValueField() {
    return getFieldByClass(SomeFieldsHaventAValueField.class);
  }

  public WhichNumberWillTheComputerFindField getWhichNumberWillTheComputerFindField() {
    return getFieldByClass(WhichNumberWillTheComputerFindField.class);
  }

  public fromField getfromField() {
    return getFieldByClass(fromField.class);
  }

  public toField gettoField() {
    return getFieldByClass(toField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      public class RandomValueBox extends AbstractSequenceBox {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("RandomValue");
        }

        @Order(10.0)
        public class fromField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("from");
          }
        }

        @Order(20.0)
        public class toField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("to");
          }
        }
      }

      @Order(20.0)
      public class SequenceBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Order(10.0)
        public class WhichNumberWillTheComputerFindField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("WhichNumberWillTheComputerFind");
          }
        }

        @Order(20.0)
        public class OkButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("OkButton");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            if (getfromField().getValue() == null || gettoField().getValue() == null || getWhichNumberWillTheComputerFindField().getValue() == null) {
              getSomeFieldsHaventAValueField().setVisible(true);
            }
            else {
              getSomeFieldsHaventAValueField().setVisible(false);
              int value = new Random().nextInt(gettoField().getValue() - getfromField().getValue() + 1) + getfromField().getValue();
              if (value == getWhichNumberWillTheComputerFindField().getValue()) {
                MessageBox.showOkMessage("Correct", "Correct\nThe number is " + value, null);
              }
              else {
                MessageBox.showOkMessage("Wrong", "Wrong\nThe number is " + value, null);
              }
            }
          }
        }

        @Order(30.0)
        public class SomeFieldsHaventAValueField extends AbstractLabelField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("SomeFieldsHaventAValue");
          }

          @Override
          protected boolean getConfiguredVisible() {
            return false;
          }
        }
      }

      @Order(30.0)
      public class CloseButton extends AbstractCloseButton {
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
