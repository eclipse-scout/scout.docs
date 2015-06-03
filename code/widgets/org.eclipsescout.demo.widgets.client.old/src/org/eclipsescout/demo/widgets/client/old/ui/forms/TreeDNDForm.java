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
package org.eclipsescout.demo.widgets.client.old.ui.forms;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.dnd.TextTransferObject;
import org.eclipse.scout.commons.dnd.TransferObject;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.tree.AbstractTree;
import org.eclipse.scout.rt.client.ui.basic.tree.AbstractTreeNode;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractArrayTableField;
import org.eclipse.scout.rt.client.ui.form.fields.treefield.AbstractTreeField;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.extension.client.ui.basic.tree.AbstractExtensibleTree;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.old.ui.forms.TreeDNDForm.MainBox.ClearButton;
import org.eclipsescout.demo.widgets.client.old.ui.forms.TreeDNDForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.old.ui.forms.TreeDNDForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.old.ui.forms.TreeDNDForm.MainBox.GroupBox.DNDStatusField;
import org.eclipsescout.demo.widgets.client.old.ui.forms.TreeDNDForm.MainBox.GroupBox.Tree2Field;
import org.eclipsescout.demo.widgets.client.old.ui.forms.TreeDNDForm.MainBox.GroupBox.TreeDNDDescriptionField;
import org.eclipsescout.demo.widgets.client.old.ui.forms.TreeDNDForm.MainBox.GroupBox.TreeField;
import org.eclipsescout.demo.widgets.client.ui.forms.IPageForm;

public class TreeDNDForm extends AbstractForm implements IPageForm {

  public TreeDNDForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("TreeDND");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public ClearButton getClearButton() {
    return getFieldByClass(ClearButton.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DNDStatusField getDNDStatusField() {
    return getFieldByClass(DNDStatusField.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public Tree2Field getTree2Field() {
    return getFieldByClass(Tree2Field.class);
  }

  public TreeDNDDescriptionField getTreeDNDDescriptionField() {
    return getFieldByClass(TreeDNDDescriptionField.class);
  }

  public TreeField getTreeField() {
    return getFieldByClass(TreeField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected int getConfiguredGridH() {
        return 10;
      }

      @Order(10.0)
      public class TreeDNDDescriptionField extends AbstractLabelField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TreeDNDDescription");
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(TEXTS.get("TreeDNDDescription"));
        }
      }

      @Order(20.0)
      public class TreeField extends AbstractTreeField {

        @Override
        protected boolean getConfiguredAutoExpandAll() {
          return true;
        }

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Tree1");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          Tree exampleTree = new Tree();
          AbstractTreeNode node1 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node A-1");
            }
          };
          AbstractTreeNode node2 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node A-2");
            }
          };
          AbstractTreeNode node11 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node A-1.1");
            }
          };
          AbstractTreeNode node12 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node A-1.2");
            }
          };
          AbstractTreeNode node13 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node A-1.3");
            }
          };
          AbstractTreeNode node21 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node A-2.1");
            }
          };
          AbstractTreeNode node211 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node A-2.1.1");
            }
          };
          AbstractTreeNode node2111 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node A-2.1.1.1");
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

          @Override
          protected boolean getConfiguredDragEnabled() {
            return true;
          }

          @Override
          protected int getConfiguredDragType() {
            return TYPE_TEXT_TRANSFER;
          }

          @Override
          protected int getConfiguredDropType() {
            return TYPE_TEXT_TRANSFER;
          }

          @Override
          protected TransferObject execDrag(ITreeNode node) throws ProcessingException {
            getDNDStatusField().addLine("Drag was started on " + node.getCell().getText());
            return new TextTransferObject(node.getCell().getText());
          }

          @Override
          protected void execDrop(ITreeNode node, TransferObject t) throws ProcessingException {
            super.execDrop(node, t);
            if (t instanceof TextTransferObject) {
              getDNDStatusField().addLine(((TextTransferObject) t).getPlainText() + " was dropped on " + node.getCell().getText());
            }
          }

          @Override
          protected void execDropTargetChanged(ITreeNode node) throws ProcessingException {
            getDNDStatusField().addLine("Dragged node is currently over " + node.getCell().getText());
          }
        }
      }

      @Order(30.0)
      public class Tree2Field extends AbstractTreeField {

        @Override
        protected boolean getConfiguredAutoExpandAll() {
          return true;
        }

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Tree2");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          Tree exampleTree = new Tree();
          AbstractTreeNode node1 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node B-1");
            }
          };
          AbstractTreeNode node2 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node B-2");
            }
          };
          AbstractTreeNode node11 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node B-1.1");
            }
          };
          AbstractTreeNode node12 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node B-1.2");
            }
          };
          AbstractTreeNode node13 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node B-1.3");
            }
          };
          AbstractTreeNode node21 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node B-2.1");
            }
          };
          AbstractTreeNode node211 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node B-2.1.1");
            }
          };
          AbstractTreeNode node2111 = new AbstractTreeNode() {
            @Override
            protected void execDecorateCell(Cell cell) {
              cell.setText("Node B-2.1.1.1");
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
        public class Tree extends AbstractExtensibleTree {

          @Override
          protected boolean getConfiguredDragEnabled() {
            return true;
          }

          @Override
          protected int getConfiguredDragType() {
            return TYPE_TEXT_TRANSFER;
          }

          @Override
          protected int getConfiguredDropType() {
            return TYPE_TEXT_TRANSFER;
          }

          @Override
          protected TransferObject execDrag(ITreeNode node) throws ProcessingException {
            getDNDStatusField().addLine("Drag was started on " + node.getCell().getText());
            return new TextTransferObject(node.getCell().getText());
          }

          @Override
          protected void execDrop(ITreeNode node, TransferObject t) throws ProcessingException {
            super.execDrop(node, t);
            if (t instanceof TextTransferObject) {
              getDNDStatusField().addLine(((TextTransferObject) t).getPlainText() + " was dropped on " + node.getCell().getText());
            }
          }

          @Override
          protected void execDropTargetChanged(ITreeNode node) throws ProcessingException {
            getDNDStatusField().addLine("Dragged node is currently over " + node.getCell().getText());
          }
        }
      }

      @Order(40.0)
      public class DNDStatusField extends AbstractArrayTableField<DNDStatusField.Table> {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        public void addLine(String text) throws ProcessingException {
          ITableRow row = getTable().addRow(getTable().createRow());
          getTable().getIdColumn().setValue(row, getTable().getRowCount());
          getTable().getActionColumn().setValue(row, text);
          getTable().selectLastRow();
          getTable().scrollToSelection();
        }

        @Order(10.0)
        public class Table extends AbstractExtensibleTable {

          public ActionColumn getActionColumn() {
            return getColumnSet().getColumnByClass(ActionColumn.class);
          }

          public IdColumn getIdColumn() {
            return getColumnSet().getColumnByClass(IdColumn.class);
          }

          @Order(10.0)
          public class IdColumn extends AbstractIntegerColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Id");
            }
          }

          @Order(20.0)
          public class ActionColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Action");
            }

            @Override
            protected int getConfiguredWidth() {
              return 300;
            }
          }
        }
      }
    }

    @Order(30.0)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(50.0)
    public class ClearButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Clear");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getDNDStatusField().getTable().deleteAllRows();
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
