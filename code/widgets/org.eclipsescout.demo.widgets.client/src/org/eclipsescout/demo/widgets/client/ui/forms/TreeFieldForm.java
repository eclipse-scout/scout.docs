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

import java.util.regex.Pattern;

import org.eclipse.scout.commons.LocaleThreadLocal;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.tree.AbstractTree;
import org.eclipse.scout.rt.client.ui.basic.tree.AbstractTreeNode;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNodeFilter;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.treefield.AbstractTreeField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm.MainBox.GroupBox.SecondTreeField;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm.MainBox.GroupBox.SecondTreeSearchField;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm.MainBox.GroupBox.TreeField;

public class TreeFieldForm extends AbstractForm implements IPageForm {

  public TreeFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("TreeField");
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

  public SecondTreeField getSecondTreeField() {
    return getFieldByClass(SecondTreeField.class);
  }

  public SecondTreeSearchField getSecondTreeSearchField() {
    return getFieldByClass(SecondTreeSearchField.class);
  }

  public TreeField getTreeField() {
    return getFieldByClass(TreeField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      public class SecondTreeSearchField extends AbstractStringField implements ITreeNodeFilter {

        private Pattern m_lowercaseFilterPattern;

        @Override
        protected int getConfiguredGridX() {
          return 1;
        }

        @Override
        protected int getConfiguredGridY() {
          return 0;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Search";//TODO
        }

        @Override
        public int getConfiguredLabelPosition() {
          return IFormField.LABEL_POSITION_ON_FIELD;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredValidateOnAnyKey() {
          return true;
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          String s = StringUtility.emptyIfNull(getValue()).trim();
          if (s.length() > 0) {
            if (!s.endsWith("*")) {
              s = s + "*";
            }
            if (!s.startsWith("*")) {
              s = "*" + s;
            }
            m_lowercaseFilterPattern = Pattern.compile(StringUtility.toRegExPattern(s.toLowerCase(LocaleThreadLocal.get())));
            getSecondTreeField().getTree().addNodeFilter(this);
          }
          else {
            getSecondTreeField().getTree().removeNodeFilter(this);
          }
        }

        /**
         * Implementation of ITreeNodeFilter
         */
        @Override
        public boolean accept(ITreeNode node, int level) {
          String text = node.getCell().getText();
          return text == null || m_lowercaseFilterPattern == null || m_lowercaseFilterPattern.matcher(text.toLowerCase(LocaleThreadLocal.get())).matches();
        }
      }

      @Order(10.0)
      public class TreeField extends AbstractTreeField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected int getConfiguredGridX() {
          return 0;
        }

        @Override
        protected int getConfiguredGridY() {
          return 0;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TreeField");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          Tree exampleTree = new Tree();
          AbstractTreeNode node1 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node 1");
            }
          };
          AbstractTreeNode node2 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node 2");
            }
          };
          AbstractTreeNode node11 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node 1.1");
            }
          };
          AbstractTreeNode node12 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node 1.2");
            }
          };
          AbstractTreeNode node13 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node 1.3");
            }
          };
          AbstractTreeNode node21 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node 2.1");
            }
          };
          AbstractTreeNode node211 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node 2.1.1");
            }
          };
          AbstractTreeNode node2111 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node 2.1.1.1");
            }
          };
          exampleTree.addChildNode(exampleTree.getRootNode(), node1);
          exampleTree.addChildNode(exampleTree.getRootNode(), node2);
          exampleTree.addChildNode(node1, node11);
          exampleTree.addChildNode(node1, node12);
          exampleTree.addChildNode(node1, node13);
          exampleTree.addChildNode(node2, node21);
          exampleTree.addChildNode(node21, node211);
          exampleTree.addChildNode(node211, node2111);
          setTree(exampleTree, false);
        }

        @Order(10.0)
        public class Tree extends AbstractTree {
        }
      }

      @Order(30.0)
      public class SecondTreeField extends AbstractTreeField {

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected int getConfiguredGridX() {
          return 1;
        }

        @Override
        protected int getConfiguredGridY() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TreeField");
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() throws ProcessingException {
          Tree exampleTree = new Tree();
          AbstractTreeNode node1 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("USA");
            }
          };
          AbstractTreeNode node2 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Germany");
            }
          };
          AbstractTreeNode node11 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Anchorage");
            }
          };
          AbstractTreeNode node12 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("New York");
            }
          };
          AbstractTreeNode node13 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Los Angeles");
            }
          };
          AbstractTreeNode node21 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Berlin");
            }
          };
          AbstractTreeNode node22 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Frankfurt");
            }
          };
          AbstractTreeNode node23 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Hamburg");
            }
          };
          exampleTree.addChildNode(exampleTree.getRootNode(), node1);
          exampleTree.addChildNode(exampleTree.getRootNode(), node2);
          exampleTree.addChildNode(node1, node11);
          exampleTree.addChildNode(node1, node12);
          exampleTree.addChildNode(node1, node13);
          exampleTree.addChildNode(node2, node21);
          exampleTree.addChildNode(node2, node22);
          exampleTree.addChildNode(node2, node23);
          setTree(exampleTree, false);
          getTree().expandAll(getTree().getRootNode());
        }

        @Order(10.0)
        public class Tree extends AbstractTree {

          @Override
          protected boolean getConfiguredMultiSelect() {
            return true;
          }
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
