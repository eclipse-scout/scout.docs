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
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.splitbox.AbstractSplitBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.GroupBox.SplitField;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm.MainBox.GroupBox.String2Field;

public class SplitBoxForm extends AbstractForm implements IPageForm {

  public SplitBoxForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SplitBox");
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

  public SplitField getSplitField() {
    return getFieldByClass(SplitField.class);
  }

  public String2Field getString2Field() {
    return getFieldByClass(String2Field.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridW() {
      return 3;
    }

    @Order(30.0)
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      public class SplitField extends AbstractSplitBox {

        @Order(10.0)
        public class StringField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("StringField");
          }
        }

        @Order(20.0)
        public class IntegerField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IntegerField");
          }
        }
      }

      @Order(20.0)
      public class String2Field extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("StringField");
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
