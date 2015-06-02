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

import java.util.List;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.tree.AbstractTreeNode;
import org.eclipse.scout.rt.client.ui.basic.tree.ITree;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.treefield.AbstractTreeField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.tree.AbstractExtensibleTree;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm.MainBox.ConfigurationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm.MainBox.ConfigurationBox.MenuContentField;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm.MainBox.ConfigurationBox.TreeEntriesField;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm.MainBox.ConfigurationBox.TreeField;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipsescout.demo.widgets.client.ui.template.formfield.AbstractUserTreeField;
import org.eclipsescout.demo.widgets.shared.services.code.IndustryICBCodeType;

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

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the MenuContentField
   */
  public MenuContentField getMenuContentField() {
    return getFieldByClass(MenuContentField.class);
  }

  /**
   * @return the TreeEntriesField
   */
  public TreeEntriesField getTreeEntriesField() {
    return getFieldByClass(TreeEntriesField.class);
  }

  public TreeField getTreeField() {
    return getFieldByClass(TreeField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    /**
     * recursive function to convert codes (enumerations) into an abstract tree.
     */
    private void addCodesToTree(List<? extends ICode<Long>> list, ITreeNode parent, AbstractExtensibleTree tree) {
      // create a tree node for each code
      for (final ICode<Long> code : list) {
        AbstractTreeNode node = new AbstractTreeNode() {
          @Override
          protected void execDecorateCell(Cell cell) {
            cell.setIconId(code.getIconId());
            cell.setText(code.getText());
            cell.setTooltipText(code.getTooltipText());
          }
        };

        // add the tree node to the tree
        tree.addChildNode(parent, node);

        // recursively add child nodes (if any)
        List<? extends ICode<Long>> children = code.getChildCodes();
        if (children.size() > 0) {
          addCodesToTree(children, node, tree);
        }
      }
    }

    /**
     * recursive function to mark tree nodes without children as leaf nodes
     */
    private void updateLeafNodes(ITreeNode node) {
      List<ITreeNode> children = node.getChildNodes();
      node.setLeaf(children.size() == 0);

      for (ITreeNode child : children) {
        updateLeafNodes(child);
      }
    }

    @Order(10.0)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10.0)
      public class DefaultField extends AbstractTreeField {

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return TEXTS.get("TreeContextMenuTooltip");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          IndustryICBCodeType icb = new IndustryICBCodeType();
          Tree tree = new Tree();
          tree.setTitle(icb.getText());
          addCodesToTree(icb.getCodes(), tree.getRootNode(), tree);
          updateLeafNodes(tree.getRootNode());
          setTree(tree, false);
        }

        @Order(10.0)
        public class Tree extends AbstractExtensibleTree {

          private void showInfo(ITreeNode node) {
            String title = node.getTree().getTitle();
            String id = node.getCell().getText();
            String children = Integer.toString(node.getChildNodeCount());
            String leaf = Boolean.toString(node.isLeaf());

            MessageBox.showOkMessage(title, TEXTS.get("NodeName", id), TEXTS.get("NodeInfo", leaf, children));
          }

          @Order(10.0)
          public class ExpandNodeMenu extends AbstractExtensibleMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("ExpandNode");
            }

            @Override
            protected void execAction() throws ProcessingException {
              getSelectedNode().setExpanded(true);
            }

            @Override
            protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
              setVisible(getSelectedNode().getChildNodeCount() > 0);
              setEnabled(!getSelectedNode().isExpanded());
            }
          }

          @Order(20.0)
          public class CollapseNodeMenu extends AbstractExtensibleMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("CollapseNode");
            }

            @Override
            protected void execAction() throws ProcessingException {
              getSelectedNode().setExpanded(false);
            }

            @Override
            protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
              setVisible(getSelectedNode().getChildNodeCount() > 0);
              setEnabled(getSelectedNode().isExpanded());
            }
          }

          @Order(30.0)
          public class Info_Menu extends AbstractExtensibleMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("Info_");
            }

            @Override
            protected void execAction() throws ProcessingException {
              showInfo(getSelectedNode());
            }
          }
        }
      }
    }

    @Order(20.0)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10.0)
      public class TreeField extends AbstractTreeField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TreeField");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setTree(new Tree(), false);
        }

        @Order(10.0)
        public class Tree extends AbstractExtensibleTree {
        }
      }

      @Order(20.0)
      public class TreeEntriesField extends AbstractUserTreeField {

        @Override
        protected int getConfiguredGridH() {
          return 3;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TreeContent");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          ITree tree = getTreeField().getTree();
          List<Node> nodes = parseFieldValue(true);

          tree.removeAllChildNodes(tree.getRootNode());
          addNodesToTree(nodes, tree, tree.getRootNode());
        }
      }

      @Order(30.0)
      public class MenuContentField extends AbstractUserTreeField {

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MenuContent");
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          List<Node> nodes = parseFieldValue(true);
          getTreeField().getTree().getContextMenu().addChildActions(nodesToMenus(nodes));
        }
      }
    }

    @Order(30.0)
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        TreeEntriesField treeEntries = getTreeEntriesField();
        treeEntries.setValue(TEXTS.get("TreeUserContent"));
        getMenuContentField().setValue(TEXTS.get("MenuUserContent"));
      }
    }

    @Order(40.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
