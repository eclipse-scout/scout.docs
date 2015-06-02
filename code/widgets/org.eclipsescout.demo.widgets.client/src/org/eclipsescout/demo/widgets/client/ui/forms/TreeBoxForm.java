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

import org.eclipse.scout.commons.LocaleThreadLocal;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNodeFilter;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.treebox.AbstractTreeBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.DateLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeBoxForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeBoxForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeBoxForm.MainBox.GroupBox.TreeBoxField;

public class TreeBoxForm extends AbstractForm implements IPageForm {

  public TreeBoxForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("TreeBox");
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

  public TreeBoxField getTreeBoxField() {
    return getFieldByClass(TreeBoxField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10.0)
      public class TreeSearchField extends AbstractStringField implements ITreeNodeFilter {

        @Override
        protected String getConfiguredLabel() {
          return "Search";
        }

        @Override
        protected boolean getConfiguredValidateOnAnyKey() {
          return true;
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          String s = StringUtility.emptyIfNull(getValue()).trim();
          if (s.length() > 0) {
            getTreeBoxField().getTree().addNodeFilter(this);
          }
          else {
            getTreeBoxField().getTree().removeNodeFilter(this);
          }
          getTreeBoxField().getTree().expandAll(getTreeBoxField().getTree().getRootNode());
        }

        /**
         * Implementation of ITreeNodeFilter
         */
        @Override
        public boolean accept(ITreeNode node, int level) {
          String text = node.getCell().getText();
          String filter = getValue();
          return text == null || filter == null || text.toLowerCase(LocaleThreadLocal.get()).contains(filter.toLowerCase(LocaleThreadLocal.get()));
        }
      }

      @Order(20.0)
      public class TreeBoxField extends AbstractTreeBox<Long> {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TreeBox");
        }

        @Override
        protected Class<? extends LookupCall> getConfiguredLookupCall() {
          return DateLookupCall.class;
        }

        @Override
        protected void execInitField() throws ProcessingException {
          super.execInitField();
          getTree().setMultiSelect(true);
        }
      }
    }

    @Order(20.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
