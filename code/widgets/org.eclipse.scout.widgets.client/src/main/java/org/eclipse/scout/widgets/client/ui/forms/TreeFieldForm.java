/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
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
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.job.ExecutionTrigger;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.visitor.DepthFirstTreeVisitor;
import org.eclipse.scout.rt.platform.util.visitor.TreeVisitResult;
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
import org.eclipse.scout.widgets.shared.Icons;
import org.eclipse.scout.widgets.shared.services.code.IndustryICBCodeType;

@Order(4000.0)
@ClassId("a497704b-f446-4bfd-bbf8-52dc00f40f20")
public class TreeFieldForm extends AbstractForm implements IAdvancedExampleForm {

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
  @ClassId("dd03a07e-0ad0-4460-b9f7-0d59aafb255f")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("997d68ed-9391-4a9b-ac1b-ac36849a1487")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("54d0fe49-2779-423f-81a1-7de2744ad718")
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
        @ClassId("23336a89-c942-4caa-9d6d-1e174f209d22")
        public class Tree extends AbstractTree {

          @Order(10)
          @ClassId("d79dd7d3-fb1f-4cf2-95c8-814270e6a092")
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
          @ClassId("af3f442a-d999-48d9-ae84-fe2ca5ba64ea")
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
            @ClassId("6425d317-16ce-4d02-a350-88997d9e150a")
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
                ModelJobs.schedule(() -> {
                  newNode(getTree());
                }, ModelJobs.newInput(ClientRunContexts.copyCurrent())
                    .withExecutionTrigger(Jobs.newExecutionTrigger()
                        .withStartIn(2, TimeUnit.SECONDS)));

              }
            }

            @Order(15)
            @ClassId("f3629cfa-e501-44cd-bd31-83512f63b46f")
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
            @ClassId("caa4eb8f-79f9-4555-9170-19c1d4f18542")
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
                selectNode(null);
              }
            }

            @Order(40)
            @ClassId("69a08259-3fdc-476f-baa1-e9f7f254f08b")
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

            @ClassId("bda89335-ef3f-4d41-a28e-8691215d3ff1")
            public class ToggleNodeIconMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("ToggleNodeIcon");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TreeMenuType.SingleSelection);
              }

              @Override
              protected void execAction() {
                Cell cell = getTree().getSelectedNode().getCellForUpdate();
                if (cell.getIconId() != null) {
                  cell.setIconId(null);
                }
                else {
                  cell.setIconId(Icons.Clock);
                }
              }
            }

            @Order(1000)
            @ClassId("93ebc17e-4ac2-4073-9521-666ccbb6c9e1")
            public class UpdateNodeTextsMenu extends AbstractMenu {
              boolean m_reverse = false;

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("ChangeNodeTexts");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TreeMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                final IntegerHolder number = new IntegerHolder(m_reverse ? 500: 0);
                getTree().visitTree(new DepthFirstTreeVisitor<>() {
                  @Override
                  public TreeVisitResult preVisit(ITreeNode node, int level, int index) {
                    node.getCellForUpdate().setText("Dynamic Text " + number.getValue());
                    number.setValue(m_reverse ? number.getValue() - 1: number.getValue() + 1);
                    return TreeVisitResult.CONTINUE;
                  }
                });
                m_reverse = !m_reverse;
              }
            }
          }

          @Order(60)
          @ClassId("c69c19ec-eff5-4948-9fdd-cd244aeca874")
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
            @ClassId("4d35787a-4af3-4740-aa1d-7a07ba71357e")
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
            @ClassId("e62ff0e4-e79f-4dd9-860d-50d8298e16b8")
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
            @ClassId("ef473433-529b-4bfa-92b7-d2f66a47d916")
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
          @ClassId("ff949dc7-bbbc-4734-b647-1beaba000abc")
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
            @ClassId("59bcdf9c-cb30-4c6b-9a12-f9b0c267afe2")
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
            @ClassId("34e0b68d-00c9-4725-bf37-1cc1917c0fa8")
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
            @ClassId("1e2d4a41-51ec-4824-8683-e3fce7e4e37e")
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
            protected void execAction() {
              getTree().removeNode(getSelectedNode());
            }
          }

          @Order(40)
          @ClassId("aa5e8383-477f-4bba-8a08-7bed308be40e")
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
          @ClassId("334bc037-0485-4448-b129-3863c884cccf")
          public class HierarchicalMenu extends AbstractHierarchicalTreeMenu {
          }

          @Order(55)
          @ClassId("7de5f3ae-939e-4852-844d-a458001c451c")
          public class ToggleRowEnabledMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TreeMenuType.SingleSelection);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("ToggleNodeEnabledState");
            }

            @Override
            protected boolean getConfiguredInheritAccessibility() {
              return false;
            }

            @Override
            protected void execAction() {
              getSelectedNode().setEnabled(!getSelectedNode().isEnabled());
            }

            @Override
            protected byte getConfiguredHorizontalAlignment() {
              return HORIZONTAL_ALIGNMENT_RIGHT;
            }
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
              final String subjectName = getSelectedNode().getCell().toPlainText();

              Jobs.schedule(() -> {

                ModelJobs.schedule(() -> getTree().selectNode(null), ModelJobs.newInput(ClientRunContexts.copyCurrent()).withName(String.format("execute job: '%s'", subjectName)));

              }, Jobs.newInput().withRunContext(ClientRunContexts.copyCurrent())
                  .withExecutionTrigger(new ExecutionTrigger().withStartIn(3, TimeUnit.SECONDS)));
            }
          }

          private void showInfo(ITreeNode node) {
            String title = node.getTree().getTitle();
            String id = node.getCell().toPlainText();
            String children = Integer.toString(node.getChildNodeCount());
            String leaf = Boolean.toString(node.isLeaf());

            MessageBoxes.createOk().withHeader(title + " " + TEXTS.get("NodeName", id)).withBody(TEXTS.get("NodeInfo", leaf, children)).show();
          }

          @Order(60)
          @ClassId("8cccc980-b247-4697-8600-0b9a58f80d78")
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
    @ClassId("163a501f-df66-48b0-8cff-5461de2802e0")
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10)
      @ClassId("54f72b0f-8f0d-4f45-a9c6-9bcfacbb9a8e")
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
        @ClassId("1a2b7468-92d1-45c9-aa43-5a61521a94ef")
        public class Tree extends AbstractTree {

          @Order(10)
          @ClassId("a4b3548e-6b5f-4b20-ba09-e5ac3a63b18b")
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
          }

          @Order(50)
          @ClassId("0e6141f5-7da1-4eab-be46-d8ad0b515778")
          public class HierarchicalMenu extends AbstractHierarchicalTreeMenu {
          }
        }

      }

      @Order(20)
      @ClassId("b498db09-d8a7-4a3d-bf13-c691affa96e2")
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
      @ClassId("19eefd66-cd92-4775-aaec-0d6941f44ad2")
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
      @ClassId("c8272a55-3eb5-4a88-a29e-da5ad093e369")
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
      @ClassId("da0d3e69-d3b6-4bda-b7b9-9d6d50e7952d")
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
          getTreeField().setEnabled(getValue(), true, true);
        }

        @Override
        protected void execInitField() {
          setValue(getTreeField().isEnabled());
        }
      }

      @Order(50)
      @ClassId("02444f61-910a-4db3-a3e7-467cfad1138a")
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
    @ClassId("284a8b46-ac57-4d9f-8ffa-ecacbebcf432")
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
    @ClassId("035b206a-5747-4547-9591-361349275bff")
    public class CloseButton extends AbstractCloseButton {
    }

    /**
     * recursive function to convert codes (enumerations) into an abstract tree.
     */
    private void addCodesToTree(List<? extends ICode<Long>> list, ITreeNode parent, AbstractTree tree) {
      // create a tree node for each code
      for (final ICode<Long> code : list) {
        AbstractTreeNode node = new AbstractTreeNode() {};
        node.getCellForUpdate().setIconId(code.getIconId());
        node.getCellForUpdate().setText(code.getText());
        node.getCellForUpdate().setTooltipText(code.getTooltipText());

        // add the tree node to the tree
        tree.addChildNode(parent, node);

        // recursively add child nodes (if any)
        List<? extends ICode<Long>> children = code.getChildCodes();
        if (!children.isEmpty()) {
          addCodesToTree(children, node, tree);
        }
      }
    }

    /**
     * recursive function to mark tree nodes without children as leaf nodes
     */
    private void updateLeafNodes(ITreeNode node) {
      List<ITreeNode> children = node.getChildNodes();
      node.setLeaf(children.isEmpty());

      for (ITreeNode child : children) {
        updateLeafNodes(child);
      }
    }

  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
