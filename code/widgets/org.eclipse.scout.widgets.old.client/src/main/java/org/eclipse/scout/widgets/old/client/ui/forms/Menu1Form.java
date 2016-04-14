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
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.old.client.ui.forms.Menu1Form.MainBox.GroupBox;
import org.eclipse.scout.widgets.old.client.ui.forms.Menu1Form.MainBox.GroupBox.FileChooserField;
import org.eclipse.scout.widgets.old.client.ui.forms.Menu1Form.MainBox.GroupBox.IntegerField;
import org.eclipse.scout.widgets.old.client.ui.forms.Menu1Form.MainBox.GroupBox.SequenceBox;
import org.eclipse.scout.widgets.old.client.ui.forms.Menu1Form.MainBox.GroupBox.StringField;

public class Menu1Form extends AbstractForm {

  public Menu1Form() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Menu1");
  }

  @Override
  public void start() {
    startInternal(new ToolHandler());
  }

  public FileChooserField getFileChooserField() {
    return getFieldByClass(FileChooserField.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public IntegerField getIntegerField() {
    return getFieldByClass(IntegerField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public SequenceBox getSequenceBox() {
    return getFieldByClass(SequenceBox.class);
  }

  public StringField getStringField() {
    return getFieldByClass(StringField.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(50)
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      public class StringField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("StringField");
        }
      }

      @Order(20)
      public class SequenceBox extends AbstractSequenceBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SequenceBox");
        }

        @Order(10)
        public class SequenceFrom extends AbstractDateField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("from");
          }
        }

        @Order(20)
        public class SequenceTo extends AbstractDateField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("to");
          }
        }
      }

      @Order(30)
      public class FileChooserField extends AbstractFileChooserField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FileChooserField");
        }
      }

      @Order(40)
      public class IntegerField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("IntegerField");
        }

        @Override
        protected int getConfiguredLabelPosition() {
          return LABEL_POSITION_ON_FIELD;
        }
      }
    }
  }

  public class ToolHandler extends AbstractFormHandler {
  }
}
