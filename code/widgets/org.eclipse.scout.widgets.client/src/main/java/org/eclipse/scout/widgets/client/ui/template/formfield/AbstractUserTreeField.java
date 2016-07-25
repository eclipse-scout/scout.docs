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
package org.eclipse.scout.widgets.client.ui.template.formfield;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.tree.AbstractTreeNode;
import org.eclipse.scout.rt.client.ui.basic.tree.ITree;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.basic.FontSpec;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * @author mzi
 */
public abstract class AbstractUserTreeField extends AbstractStringField {

  @Override
  protected String getConfiguredLabelFont() {
    return "ITALIC";
  }

  @Override
  protected boolean getConfiguredMultilineText() {
    return true;
  }

  protected List<Node> parseFieldValue(boolean isTree) {
    Map<String, Node> parents = new HashMap<String, Node>();
    Node root = new Node();

    clearErrorStatus();

    for (String line : getValue().split("\n")) {
      line = line.trim();

      if (line.length() > 0 && !line.startsWith("#")) {
        Node node = parseLine(line, isTree);

        if (node.isInvalid()) {
          addErrorStatus(node.invalidMessage() + ". Line: '" + line + "'");
          break;
        }

        parents.put(node.getKey(), node);
        Node parent = parents.get(node.getParentKey());

        if (parent == null) {
          root.addChild(node);
        }
        else {
          parent.addChild(node);
        }
      }
    }

    return root.getChildren();
  }

  private Node parseLine(String line, boolean isTree) {
    String[] t = line.split(";");
    int tlen = t.length;
    Node node = new Node();

    if (isTree && t.length != 8) {
      node.setInvalid(true);
      node.setInvalidMessage("Wrong number of tokens. Expected: 8 found: " + t.length);
      return node;
    }

    if (!isTree && t.length != 7) {
      node.setInvalid(true);
      node.setInvalidMessage("Wrong number of tokens. Expected: 7 found: " + t.length);
      return node;
    }

    // set key
    if (!StringUtility.isNullOrEmpty(t[0])) {
      node.setKey(t[0]);
    }
    else {
      node.setInvalid(true);
      node.setInvalidMessage("Key must not be empty");
      return node;
    }

    if (isTree) {
      node.setParentKey(t[1]);
    }

    node.setText(t[tlen - 6]);
    node.setIconId(t[tlen - 5]);
    node.setToolTip(t[tlen - 4]);
    node.setFont(FontSpec.parse(t[tlen - 3]));
    node.setEnabled(parseBoolean(t[tlen - 2]));
    node.setActive(parseBoolean(t[tlen - 1]));

    return node;
  }

  private boolean parseBoolean(String bool) {
    return !(!StringUtility.isNullOrEmpty(bool) && bool.equals("false"));
  }

  protected void printNodes(List<Node> nodes, String ident) {
    for (Node node : nodes) {
      System.out.println(ident + node);
      printNodes(node.getChildren(), ident + "  ");
    }
  }

  protected void addNodesToTree(List<Node> nodes, ITree tree, ITreeNode root) {
    for (Node node : nodes) {
      ITreeNode treeNode = nodeToTreeNode(node);
      tree.addChildNode(root, treeNode);

      if (!node.isLeaf()) {
        addNodesToTree(node.getChildren(), tree, treeNode);
      }
    }
  }

  private ITreeNode nodeToTreeNode(final Node node) {
    AbstractTreeNode treeNode = new AbstractTreeNode() {
      @Override
      protected void execDecorateCell(Cell cell) {
        cell.setIconId(node.getIconId());
        cell.setText(node.getText());
        cell.setTooltipText(node.getToolTip());
      }
    };

    treeNode.setLeaf(node.isLeaf());

    return treeNode;
  }

  protected void addNodesToLookupRows(List<Node> nodes, List<LookupRow<String>> rows) {
    for (Node node : nodes) {
      rows.add(nodeToLookupRow(node));

      if (!node.isLeaf()) {
        addNodesToLookupRows(node.getChildren(), rows);
      }
    }
  }

  private LookupRow<String> nodeToLookupRow(Node node) {
    LookupRow<String> row = new LookupRow<String>(node.getKey(), node.getText());

    row.withIconId(node.getIconId());
    row.withParentKey(node.getParentKey());
    row.withTooltipText(node.getToolTip());
    row.withFont(node.getFont());
    row.withEnabled(node.isEnabled());
    row.withActive(node.isActive());

    return row;
  }

  protected List<IMenu> nodesToMenus(List<Node> nodes) {
    ArrayList<IMenu> menus = new ArrayList<IMenu>();

    for (final Node node : nodes) {
      AbstractMenu menu = new AbstractMenu() {

        @Override
        public void execAction() {
          MessageBoxes.createOk().withHeader(TEXTS.get("TableMenuHeader")).withBody(getText()).show();
        }

      };

      menu.setText(node.getText());
      menu.setTooltipText(node.getToolTip());
      menu.setIconId(node.getIconId());
      menu.setEnabled(node.isEnabled());
      menu.setChildActions(nodesToMenus(node.getChildren()));

      menus.add(menu);
    }

    return menus;
  }

  public class Node {
    private String m_key;
    private String m_parentKey;
    private Node m_parent;
    private List<Node> m_children;

    private String m_text;
    private String m_icon;
    private String m_toolTip;
    private FontSpec m_font;
    private boolean m_enabled;
    private boolean m_active;

    private boolean m_invalid;
    private String m_invalidMessage;

    public Node() {
      m_parent = null;
      m_children = new ArrayList<Node>();
    }

    @Override
    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append(getKey() + "[" + getParentKey());
      buf.append("," + getText());
      buf.append("," + getIconId());
      buf.append("]");
      return buf.toString();
    }

    public void addChild(Node child) {
      child.m_parent = this;
      m_children.add(child);
    }

    public List<Node> getChildren() {
      return m_children;
    }

    public boolean isLeaf() {
      return m_children.size() == 0;
    }

    public boolean isRoot() {
      return m_parent == null;
    }

    String getText() {
      return m_text;
    }

    void setText(String text) {
      m_text = text;
    }

    String getToolTip() {
      return m_toolTip;
    }

    void setToolTip(String toolTip) {
      m_toolTip = toolTip;
    }

    FontSpec getFont() {
      return m_font;
    }

    void setFont(FontSpec font) {
      m_font = font;
    }

    boolean isEnabled() {
      return m_enabled;
    }

    void setEnabled(boolean enabled) {
      m_enabled = enabled;
    }

    boolean isActive() {
      return m_active;
    }

    void setActive(boolean active) {
      m_active = active;
    }

    void setInvalid(boolean invalid) {
      m_invalid = invalid;
    }

    boolean isInvalid() {
      return m_invalid;
    }

    void setInvalidMessage(String message) {
      m_invalidMessage = message;
    }

    String invalidMessage() {
      return m_invalidMessage;
    }

    private String getParentKey() {
      return m_parentKey;
    }

    private void setParentKey(String parentKey) {
      m_parentKey = parentKey;
    }

    private String getKey() {
      return m_key;
    }

    private void setKey(String key) {
      m_key = key;
    }

    private String getIconId() {
      return m_icon;
    }

    private void setIconId(String icon) {
      m_icon = icon;
    }
  }

}
