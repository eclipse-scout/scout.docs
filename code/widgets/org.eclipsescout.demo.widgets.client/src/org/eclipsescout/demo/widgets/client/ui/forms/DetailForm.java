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
import org.eclipse.scout.rt.client.ui.form.fields.doublefield.AbstractDoubleField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.DetailForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.DetailForm.MainBox.GroupBox.ValueHighField;
import org.eclipsescout.demo.widgets.client.ui.forms.DetailForm.MainBox.GroupBox.ValueLastField;
import org.eclipsescout.demo.widgets.client.ui.forms.DetailForm.MainBox.GroupBox.ValueLowField;
import org.eclipsescout.demo.widgets.client.ui.forms.DetailForm.MainBox.GroupBox.ValueOpenField;

public class DetailForm extends AbstractForm {

  public DetailForm() throws ProcessingException {
    super();
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ValueHighField getValueHighField() {
    return getFieldByClass(ValueHighField.class);
  }

  public ValueLastField getValueLastField() {
    return getFieldByClass(ValueLastField.class);
  }

  public ValueLowField getValueLowField() {
    return getFieldByClass(ValueLowField.class);
  }

  public ValueOpenField getValueOpenField() {
    return getFieldByClass(ValueOpenField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected boolean getConfiguredGridUseUiHeight() {
        return false;
      }

      @Override
      protected int getConfiguredHeightInPixel() {
        return 80;
      }

      @Order(10.0)
      public class ValueLastField extends AbstractDoubleField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ValueLast");
        }
      }

      @Order(20.0)
      public class ValueOpenField extends AbstractDoubleField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ValueOpen");
        }
      }

      @Order(30.0)
      public class ValueLowField extends AbstractDoubleField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ValueLow");
        }
      }

      @Order(40.0)
      public class ValueHighField extends AbstractDoubleField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ValueHigh");
        }
      }
    }
  }

  public class NewHandler extends AbstractFormHandler {
  }
}
