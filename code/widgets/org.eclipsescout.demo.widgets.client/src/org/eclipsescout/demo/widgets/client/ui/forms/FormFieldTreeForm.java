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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.svg.client.SVGUtility;
import org.eclipse.scout.svg.client.svgfield.AbstractSvgField;
import org.eclipse.scout.svg.client.svgfield.SvgFieldEvent;
import org.eclipsescout.demo.widgets.client.Activator;
import org.eclipsescout.demo.widgets.client.ui.desktop.outlines.pages.FormPage;
import org.eclipsescout.demo.widgets.client.ui.forms.FormFieldTreeForm.MainBox.FormFieldTableField;
import org.eclipsescout.demo.widgets.client.ui.forms.FormFieldTreeForm.MainBox.FormFieldTreeField;
import org.eclipsescout.demo.widgets.client.ui.forms.FormFieldTreeForm.MainBox.ShowTableButton;
import org.w3c.dom.svg.SVGDocument;

public class FormFieldTreeForm extends AbstractForm {

  IPage m_page;

  public FormFieldTreeForm(IPage page) throws ProcessingException {
    super();
    m_page = page;
  }

  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public FormFieldTableField getFormFieldTableField() {
    return getFieldByClass(FormFieldTableField.class);
  }

  public FormFieldTreeField getFormFieldTreeField() {
    return getFieldByClass(FormFieldTreeField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ShowTableButton getShowTableButton() {
    return getFieldByClass(ShowTableButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class FormFieldTreeField extends AbstractSvgField {

      private SVGDocument m_formfieldtree;
      private SVGDocument m_valuefieldtree;
      private SVGDocument m_compositetree;

      @Override
      protected String getConfiguredBackgroundColor() {
        return "E3F4FF";
      }

      @Override
      protected int getConfiguredGridH() {
        return 10;
      }

      @Override
      protected int getConfiguredGridW() {
        return 0;
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected void execHyperlink(SvgFieldEvent e) throws ProcessingException {
        String url = e.getURL().toExternalForm();

        String url_form = url.split("/")[url.split("/").length - 1];

        for (ITreeNode node : m_page.getChildNodes()) {
          for (String fieldName : node.getCellForUpdate().getText().split(" ")) {
            if (fieldName.toLowerCase().startsWith(url_form)) {
              m_page.getTree().selectNode(node);
              return;
            }
          }
        }
        if (url_form.equals("back")) {
          setSvgDocument(m_formfieldtree);
        }
        else if (url_form.equals("valuefield")) {
          setSvgDocument(m_valuefieldtree);
        }
        else if (url_form.equals("compositefield")) {
          setSvgDocument(m_compositetree);
        }
      }

      @Override
      protected void execInitField() throws ProcessingException {
        try {
          URL url = Activator.getDefault().getBundle().getResource("/resources/svg/formfieldtree.svg");
          InputStream is = url.openStream();
          m_formfieldtree = SVGUtility.readSVGDocument(is);

          url = Activator.getDefault().getBundle().getResource("/resources/svg/valuefieldtree.svg");
          is = url.openStream();
          m_valuefieldtree = SVGUtility.readSVGDocument(is);

          url = Activator.getDefault().getBundle().getResource("/resources/svg/compositefieldtree.svg");
          is = url.openStream();
          m_compositetree = SVGUtility.readSVGDocument(is);

          setSvgDocument(m_formfieldtree);
        }
        catch (IOException e) {
          e.printStackTrace();
          throw new ProcessingException("Exception occured while reading svg file", e);
        }
      }
    }

    @Order(20.0)
    public class FormFieldTableField extends AbstractTableField {

      @Override
      protected int getConfiguredGridH() {
        return 10;
      }

      @Override
      protected int getConfiguredGridW() {
        return 0;
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }

      @Override
      protected void execReloadTableData() throws ProcessingException {
        getTable().deleteAllRows();
        Object[][] rows = new Object[m_page.getChildNodes().length][2];
        for (int index = 0; index < m_page.getChildNodes().length; index++) {
          rows[index][0] = m_page.getChildNodes()[index];
          rows[index][1] = m_page.getChildNodes()[index].getCellForUpdate().getText();
        }
        getTable().addRowsByMatrix(rows);
      }

      @Order(10.0)
      public class Table extends AbstractTable {

        @Override
        protected boolean getConfiguredAutoResizeColumns() {
          return true;
        }

        @Override
        protected String getConfiguredDefaultIconId() {
          return org.eclipsescout.demo.widgets.shared.Icons.Form;
        }

        @Override
        protected void execRowAction(ITableRow row) throws ProcessingException {
          m_page.getTree().selectNode(getTreeNodeColumn().getValue(row));
        }

        public FoldersColumn getFoldersColumn() {
          return getColumnSet().getColumnByClass(FoldersColumn.class);
        }

        public TreeNodeColumn getTreeNodeColumn() {
          return getColumnSet().getColumnByClass(TreeNodeColumn.class);
        }

        @Order(10.0)
        public class TreeNodeColumn extends AbstractColumn<ITreeNode> {

          @Override
          protected boolean getConfiguredDisplayable() {
            return false;
          }

          @Override
          protected boolean getConfiguredVisible() {
            return false;
          }
        }

        @Order(20.0)
        public class FoldersColumn extends AbstractStringColumn {

          @Override
          protected String getConfiguredHeaderText() {
            return TEXTS.get("Folders");
          }
        }

        @Order(10.0)
        public class OpenInADialogMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("OpenInADialog");
          }

          @Override
          protected void execAction() throws ProcessingException {
            getTreeNodeColumn().getSelectedValue().getMenu(FormPage.OpenInADialogMenu.class).doAction();
          }
        }

        @Order(20.0)
        public class ViewSourceOnGitHubMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ViewSourceOnGitHub");
          }

          @Override
          protected void execAction() throws ProcessingException {
            getTreeNodeColumn().getSelectedValue().getMenu(FormPage.ViewSourceOnGitHubMenu.class).doAction();
          }
        }
      }
    }

    @Order(30.0)
    public class ShowTableButton extends AbstractLinkButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ShowTable");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        if (getFormFieldTreeField().isVisible()) {
          getFormFieldTreeField().setVisible(false);
          getFormFieldTableField().setVisible(true);
          getFormFieldTableField().reloadTableData();
          setLabel(TEXTS.get("ShowHierarchy"));
        }
        else {
          getFormFieldTreeField().setVisible(true);
          getFormFieldTableField().setVisible(false);
          setLabel(TEXTS.get("ShowTable"));
        }
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
