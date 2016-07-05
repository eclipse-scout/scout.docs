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
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.treebox.AbstractTreeBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.DateLookupCall;
import org.eclipse.scout.widgets.old.client.ui.forms.Menu2Form.MainBox.GroupBox.TreeBoxField;

public class Menu2Form extends AbstractForm {

  public Menu2Form() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Menu2");
  }

  @Override
  public void start() {
    startInternal(new ToolHandler());
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public TreeBoxField getTreeBoxField() {
    return getFieldByClass(TreeBoxField.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    public class GroupBox extends AbstractGroupBox {

      @Order(10)
      public class TreeBoxField extends AbstractTreeBox<Long> {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
          return DateLookupCall.class;
        }
      }
    }
  }

  public class ToolHandler extends AbstractFormHandler {
  }
}
