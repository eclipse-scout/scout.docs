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
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.tree.AbstractTree;
import org.eclipse.scout.rt.client.ui.basic.tree.AbstractTreeNode;
import org.eclipse.scout.rt.client.ui.basic.tree.ITree;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.treefield.AbstractTreeField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.job.ExecutionTrigger;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.widgets.client.ui.desktop.menu.AbstractHierarchicalTreeMenu;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.TreeFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TreeFieldForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.TreeFieldForm.MainBox.ConfigurationBox.MenuContentField;
import org.eclipse.scout.widgets.client.ui.forms.TreeFieldForm.MainBox.ConfigurationBox.TreeEntriesField;
import org.eclipse.scout.widgets.client.ui.forms.TreeFieldForm.MainBox.ConfigurationBox.TreeField;
import org.eclipse.scout.widgets.client.ui.forms.TreeFieldForm.MainBox.ConfigurationBox.TreeField.Tree.DynamicMenu;
import org.eclipse.scout.widgets.client.ui.forms.TreeFieldForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.TreeFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractUserTreeField;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType;

@Order(4000.0)
public class TreeFieldForm extends AbstractForm implements IAdvancedExampleForm {

  public TreeFieldForm() {
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
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public TreeField getTreeField() {
    return getFieldByClass(TreeField.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MenuContentField getMenuContentField() {
    return getFieldByClass(MenuContentField.class);
  }

  public TreeEntriesField getTreeEntriesField() {
    return getFieldByClass(TreeEntriesField.class);
  }

  protected ITreeNode newNode(ITree tree) {
    ITreeNode node = new AbstractTreeNode() {
    };
    node.getCellForUpdate().setText("New Node");
    ITreeNode parent = tree.getSelectedNode();
    if (parent == null) {
      parent = tree.getRootNode();
    }
    tree.addChildNode(parent, node);
    return node;
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
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
        protected void execInitField() {
          IndustryICBCodeType icb = new IndustryICBCodeType();
          Tree tree = new Tree();
          tree.setTitle(icb.getText());
          addCodesToTree(icb.getCodes(), tree.getRootNode(), tree);
          updateLeafNodes(tree.getRootNode());
          setTree(tree, false);
        }

        @Order(10)
        public class Tree extends AbstractTree {

          @Override
          protected boolean getConfiguredMultiSelect() {
            return true;
          }

          @Order(10)
          public class NewMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("New");
            }

            @Override
            protected void execAction() {
              newNode(getTree());
            }
          }

          @Order(15)
          public class MoreMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return TEXTS.get("More");
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
            }

            @Order(10)
            public class NewDelayedMenu extends AbstractMenu {

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
              }

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("NewDelayed");
              }

              @Override
              protected void execAction() {
                ModelJobs.schedule(new IRunnable() {
                  @Override
                  public void run() throws Exception {
                    newNode(getTree());
                  }
                }, ModelJobs.newInput(ClientRunContexts.copyCurrent())
                    .withExecutionTrigger(Jobs.newExecutionTrigger()
                        .withStartIn(2, TimeUnit.SECONDS)));

              }
            }

            @Order(15)
            public class ScrollToSelection extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("ScrollToSelection");
              }

              @Override
              protected void execAction() {
                scrollToSelection();
              }
            }

            @Order(30)
            public class SelectNoneMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("SelectNone");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                deselectNodes(getSelectedNodes());
              }
            }

            @Order(40)
            public class SwitchDisplayStyle extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("SwitchDisplayStyle");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                String displayStyle = getTree().getDisplayStyle();
                if (ITree.DISPLAY_STYLE_DEFAULT.equals(displayStyle)) {
                  getTree().setDisplayStyle(ITree.DISPLAY_STYLE_BREADCRUMB);
                }
                else {
                  getTree().setDisplayStyle(ITree.DISPLAY_STYLE_DEFAULT);
                }
              }
            }
          }

          @Order(60)
          public class ExpandMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Expand";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
            }

            @Order(10)
            public class ExpandTreeMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Expand tree";
              }

              @Override
              protected String getConfiguredTooltipText() {
                return "Expands all nodes in the entire tree.";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                getTree().expandAll(getRootNode());
              }
            }

            @Order(20)
            public class ExpandThisNodeMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Expand node";
              }

              @Override
              protected String getConfiguredTooltipText() {
                return "Expands the selected node.";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace, TreeMenuType.SingleSelection);
              }

              @Override
              protected boolean getConfiguredVisible() {
                return false; // no initial selected node
              }

              @Override
              protected void execAction() {
                getSelectedNode().setExpanded(true);
              }

              @Override
              protected void execOwnerValueChanged(Object newOwnerValue) {
                setVisible(getSelectedNode() != null);
              }
            }

            @Order(30)
            public class ExpandSubtreeMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Expand subtree";
              }

              @Override
              protected String getConfiguredTooltipText() {
                return "Expands the selected node and all child nodes.";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace, TreeMenuType.SingleSelection);
              }

              @Override
              protected boolean getConfiguredVisible() {
                return false; // no initial selected node
              }

              @Override
              protected void execAction() {
                getTree().expandAll(getSelectedNode());
              }

              @Override
              protected void execOwnerValueChanged(Object newOwnerValue) {
                setVisible(getSelectedNode() != null);
              }
            }
          }

          @Order(70)
          public class CollapseMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Collapse";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
            }

            @Order(10)
            public class CollapseTreeMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Collapse tree";
              }

              @Override
              protected String getConfiguredTooltipText() {
                return "Collapses all nodes in the entire tree.";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                getTree().collapseAll(getRootNode());
              }
            }

            @Order(20)
            public class CollapseThisNodeMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Collapse node";
              }

              @Override
              protected String getConfiguredTooltipText() {
                return "Collapses the selected node.";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace, TreeMenuType.SingleSelection);
              }

              @Override
              protected boolean getConfiguredVisible() {
                return false; // no initial selected node
              }

              @Override
              protected void execAction() {
                getSelectedNode().setExpanded(false);
              }

              @Override
              protected void execOwnerValueChanged(Object newOwnerValue) {
                setVisible(getSelectedNode() != null);
              }
            }

            @Order(30)
            public class CollapseSubtreeMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Collapse subtree";
              }

              @Override
              protected String getConfiguredTooltipText() {
                return "Collapses the selected node and all child nodes.";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace, TreeMenuType.SingleSelection);
              }

              @Override
              protected boolean getConfiguredVisible() {
                return false; // no initial selected node
              }

              @Override
              protected void execAction() {
                getTree().collapseAll(getSelectedNode());
              }

              @Override
              protected void execOwnerValueChanged(Object newOwnerValue) {
                setVisible(getSelectedNode() != null);
              }
            }
          }

          @Order(22)
          @ClassId("b43eaa82-5241-4688-b8f9-4a32ddd44f83")
          public class DeleteNodeMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("Delete");
            }

            @Override
            protected String getConfiguredKeyStroke() {
              return IKeyStroke.DELETE;
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(TreeMenuType.SingleSelection, TreeMenuType.MultiSelection);
            }

            @Override
            protected void execAction() {
              getTree().removeNode(getSelectedNode());
            }
          }

          @Order(40)
          public class InfoMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("Info_");
            }

            @Override
            protected void execAction() {
              showInfo(getSelectedNode());
            }
          }

          @Order(50)
          public class HierarchicalMenu extends AbstractHierarchicalTreeMenu {
          }

          @Order(60)
          @ClassId("cebf3848-8194-4cb0-b0ee-4206b937961d")
          public class DelayedClearSelectionMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(TreeMenuType.SingleSelection);
            }

            @Override
            protected String getConfiguredText() {
              return "Clear selection after 3 sec";
            }

            @Override
            protected void execAction() {
              final String subjectName = getSelectedNode().getCell().getText();

              Jobs.schedule(new IRunnable() {

                @Override
                public void run() throws Exception {

                  ModelJobs.schedule(new IRunnable() {
                    @Override
                    public void run() throws Exception {
                      getTree().selectNode(null);
                    }
                  }, ModelJobs.newInput(ClientRunContexts.copyCurrent()).withName(String.format("execute job: '%s'", subjectName)));

                }
              }, Jobs.newInput().withRunContext(ClientRunContexts.copyCurrent())
                  .withExecutionTrigger(new ExecutionTrigger().withStartIn(3, TimeUnit.SECONDS)));
            }
          }

          private void showInfo(ITreeNode node) {
            String title = node.getTree().getTitle();
            String id = node.getCell().getText();
            String children = Integer.toString(node.getChildNodeCount());
            String leaf = Boolean.toString(node.isLeaf());

            MessageBoxes.createOk().withHeader(title + " " + TEXTS.get("NodeName", id)).withBody(TEXTS.get("NodeInfo", leaf, children)).show();
          }

          @Order(60)
          public class KeyStroke extends AbstractKeyStroke {

            @Override
            protected String getConfiguredText() {
              return getClass().getSimpleName();
            }

            @Override
            protected String getConfiguredKeyStroke() {
              return "Ctrl-Shift-Alt-h";
            }

            @Override
            protected void execAction() {
              MessageBoxes.createOk().withHeader(getConfiguredKeyStroke() + " activated").show();
            }
          }
        }
      }
    }

    @Order(20)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
      public class TreeField extends AbstractTreeField {

        @Override
        protected int getConfiguredGridH() {
          return 8;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TreeField");
        }

        @Override
        protected void execInitField() {
          setTree(new Tree(), false);
        }

        @Order(10)
        public class Tree extends AbstractTree {

          @Order(10)
          public class NewMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("New");
            }

            @Override
            protected void execAction() {
              ITreeNode node = newNode(getTree());
              node.getParentNode().setLeaf(false);
            }
          }

          @Order(15)
          @ClassId("091c930e-cd4c-4ac0-a87a-60f87b189c00")
          public class DeleteNodeMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return TEXTS.get("Delete");
            }

            @Override
            protected String getConfiguredKeyStroke() {
              return IKeyStroke.DELETE;
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(TreeMenuType.SingleSelection, TreeMenuType.MultiSelection);
            }

            @Override
            protected void execAction() {
              ITreeNode parentNode = null;
              if (getSelectedNode() != null) {
                parentNode = getSelectedNode().getParentNode();
              }
              getTree().removeNode(getSelectedNode());
              if (parentNode != null && parentNode.getChildNodeCount() == 0) {
                parentNode.setLeaf(true);
              }
            }
          }

          @Order(20)
          @ClassId("bd858b45-2f64-4b6f-8881-302f684a4ecc")
          public class DynamicMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return TEXTS.get("DynamicMenu");
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(TreeMenuType.SingleSelection, TreeMenuType.MultiSelection);
            }

          }

          @Order(50)
          public class HierarchicalMenu extends AbstractHierarchicalTreeMenu {
          }
        }

      }

      @Order(20)
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
        protected void execChangedValue() {
          ITree tree = getTreeField().getTree();
          List<Node> nodes = parseFieldValue(true);

          tree.removeAllChildNodes(tree.getRootNode());
          addNodesToTree(nodes, tree, tree.getRootNode());
        }

      }

      @Order(30)
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
        protected void execChangedValue() {
          List<Node> nodes = parseFieldValue(true);
          DynamicMenu dynamicMenu = getTreeField().getTree().getMenuByClass(DynamicMenu.class);
          dynamicMenu.removeChildActions(dynamicMenu.getChildActions());
          dynamicMenu.addChildActions(nodesToMenus(nodes));
        }
      }

      @Order(35)
      public class AutoCheckChildNodesField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AutoCheckChildNodes");
        }

        @Override
        protected void execChangedValue() {
          getTreeField().getTree().setAutoCheckChildNodes(getValue());
        }
      }

      @Order(40)
      public class IsEnabledField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("IsEnabled");
        }

        @Override
        protected void execChangedValue() {
          getTreeField().setEnabled(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTreeField().isEnabled());
        }
      }

      @Order(50)
      public class CheckableField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return "Checkable";
        }

        @Override
        protected void execChangedValue() {
          getTreeField().getTree().setCheckable(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTreeField().getTree().isCheckable());
        }
      }
    }

    @Order(30)
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        TreeEntriesField treeEntries = getTreeEntriesField();
        treeEntries.setValue(TEXTS.get("TreeUserContent"));
        getMenuContentField().setValue(TEXTS.get("MenuUserContent"));
      }
    }

    @Order(40)
    public class CloseButton extends AbstractCloseButton {
    }

    /**
     * recursive function to convert codes (enumerations) into an abstract tree.
     */
    private void addCodesToTree(List<? extends ICode<Long>> list, ITreeNode parent, AbstractTree tree) {
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

  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
