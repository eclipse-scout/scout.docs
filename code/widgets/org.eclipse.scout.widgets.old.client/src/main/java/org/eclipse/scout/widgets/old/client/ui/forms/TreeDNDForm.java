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

import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.tree.AbstractTree;
import org.eclipse.scout.rt.client.ui.basic.tree.AbstractTreeNode;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.dnd.TextTransferObject;
import org.eclipse.scout.rt.client.ui.dnd.TransferObject;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.form.fields.treefield.AbstractTreeField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.old.client.ui.forms.TreeDNDForm.MainBox.ClearButton;
import org.eclipse.scout.widgets.old.client.ui.forms.TreeDNDForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.old.client.ui.forms.TreeDNDForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.old.client.ui.forms.TreeDNDForm.MainBox.GroupBox.DNDStatusField;
import org.eclipse.scout.widgets.old.client.ui.forms.TreeDNDForm.MainBox.GroupBox.Tree2Field;
import org.eclipse.scout.widgets.old.client.ui.forms.TreeDNDForm.MainBox.GroupBox.TreeDNDDescriptionField;
import org.eclipse.scout.widgets.old.client.ui.forms.TreeDNDForm.MainBox.GroupBox.TreeField;

public class TreeDNDForm extends AbstractForm implements IPageForm {

  public TreeDNDForm() {
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
  public void startPageForm() {
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

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected int getConfiguredGridH() {
        return 10;
      }

      @Order(10)
      public class TreeDNDDescriptionField extends AbstractLabelField {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TreeDNDDescription");
        }

        @Override
        protected void execInitField() {
          setValue(TEXTS.get("TreeDNDDescription"));
        }
      }

      @Order(20)
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
        protected void execInitField() {
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

        @Order(10)
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
          protected TransferObject execDrag(ITreeNode node) {
            getDNDStatusField().addLine("Drag was started on " + node.getCell().getText());
            return new TextTransferObject(node.getCell().getText());
          }

          @Override
          protected void execDrop(ITreeNode node, TransferObject t) {
            super.execDrop(node, t);
            if (t instanceof TextTransferObject) {
              getDNDStatusField().addLine(((TextTransferObject) t).getPlainText() + " was dropped on " + node.getCell().getText());
            }
          }

          @Override
          protected void execDropTargetChanged(ITreeNode node) {
            getDNDStatusField().addLine("Dragged node is currently over " + node.getCell().getText());
          }
        }
      }

      @Order(30)
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
        protected void execInitField() {
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

        @Order(10)
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
          protected TransferObject execDrag(ITreeNode node) {
            getDNDStatusField().addLine("Drag was started on " + node.getCell().getText());
            return new TextTransferObject(node.getCell().getText());
          }

          @Override
          protected void execDrop(ITreeNode node, TransferObject t) {
            super.execDrop(node, t);
            if (t instanceof TextTransferObject) {
              getDNDStatusField().addLine(((TextTransferObject) t).getPlainText() + " was dropped on " + node.getCell().getText());
            }
          }

          @Override
          protected void execDropTargetChanged(ITreeNode node) {
            getDNDStatusField().addLine("Dragged node is currently over " + node.getCell().getText());
          }
        }
      }

      @Order(40)
      public class DNDStatusField extends AbstractTableField<DNDStatusField.Table> {

        public void addLine(String text) {
          ITableRow row = getTable().addRow(getTable().createRow());
          getTable().getIdColumn().setValue(row, getTable().getRowCount());
          getTable().getActionColumn().setValue(row, text);
          getTable().selectLastRow();
          getTable().scrollToSelection();
        }

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

        public class Table extends AbstractTable {

          public IdColumn getIdColumn() {
            return getColumnSet().getColumnByClass(IdColumn.class);
          }

          public ActionColumn getActionColumn() {
            return getColumnSet().getColumnByClass(ActionColumn.class);
          }

          @Order(20)
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

          @Order(10)
          public class IdColumn extends AbstractIntegerColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Id");
            }
          }
        }
      }
    }

    @Order(50)
    public class ClearButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Clear");
      }

      @Override
      protected void execClickAction() {
        getDNDStatusField().getTable().deleteAllRows();
      }
    }

    @Order(30)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
