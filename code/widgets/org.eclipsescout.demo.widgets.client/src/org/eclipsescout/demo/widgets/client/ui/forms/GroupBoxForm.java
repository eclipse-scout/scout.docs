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
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm.MainBox.DefaultBox;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm.MainBox.DefaultBox.Default0Field;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm.MainBox.DefaultBox.DefaultField;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm.MainBox.EmptyBox;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm.MainBox.EmptyBox.Empty0Field;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm.MainBox.EmptyBox.EmptyField;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm.MainBox.LineBox;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm.MainBox.LineBox.Line0Field;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm.MainBox.LineBox.LineField;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm.MainBox.SectionBox;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm.MainBox.SectionBox.Section0Field;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm.MainBox.SectionBox.SectionField;

public class GroupBoxForm extends AbstractForm implements IPageForm {

  public GroupBoxForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("GroupBox");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public Default0Field getDefault0Field() {
    return getFieldByClass(Default0Field.class);
  }

  public DefaultBox getDefaultBox() {
    return getFieldByClass(DefaultBox.class);
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public Empty0Field getEmpty0Field() {
    return getFieldByClass(Empty0Field.class);
  }

  public EmptyBox getEmptyBox() {
    return getFieldByClass(EmptyBox.class);
  }

  public EmptyField getEmptyField() {
    return getFieldByClass(EmptyField.class);
  }

  public Line0Field getLine0Field() {
    return getFieldByClass(Line0Field.class);
  }

  public LineBox getLineBox() {
    return getFieldByClass(LineBox.class);
  }

  public LineField getLineField() {
    return getFieldByClass(LineField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public Section0Field getSection0Field() {
    return getFieldByClass(Section0Field.class);
  }

  public SectionBox getSectionBox() {
    return getFieldByClass(SectionBox.class);
  }

  public SectionField getSectionField() {
    return getFieldByClass(SectionField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class DefaultBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Default");
      }

      @Order(10.0)
      public class DefaultField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }
      }

      @Order(20.0)
      public class Default0Field extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }
      }
    }

    @Order(20.0)
    public class EmptyBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredBorderDecoration() {
        return "empty";
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Empty");
      }

      @Order(10.0)
      public class EmptyField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Empty");
        }
      }

      @Order(20.0)
      public class Empty0Field extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Empty");
        }
      }
    }

    @Order(30.0)
    public class LineBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredBorderDecoration() {
        return "line";
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Line");
      }

      @Order(10.0)
      public class LineField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Line");
        }
      }

      @Order(20.0)
      public class Line0Field extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Line");
        }
      }
    }

    @Order(40.0)
    public class SectionBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredBorderDecoration() {
        return "section";
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Section");
      }

      @Order(10.0)
      public class SectionField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Section");
        }
      }

      @Order(20.0)
      public class Section0Field extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Section");
        }
      }
    }

    @Order(50.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
