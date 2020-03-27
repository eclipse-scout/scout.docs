/*
 * Copyright (c) 2017 BSI Business Systems Integration AG. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0 which accompanies this
 * distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors: BSI Business Systems Integration AG - initial API and
 * implementation
 */
import {Form, icons, models} from '@eclipse-scout/core';
import TreeFormModel from './TreeFormModel';

export default class TreeForm extends Form {

  constructor() {
    super();

    this.nodeNo = 0;
  }

  _jsonModel() {
    return models.get(TreeFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    this.tree = this.widget('Tree');
    this.widget('AddNodeMenu').on('action', this._onAddNodeMenuAction.bind(this));
    this.widget('AddChildNodeMenu').on('action', this._onAddChildNodeMenuAction.bind(this));
    this.widget('DeleteNodeMenu').on('action', this._onDeleteNodeMenuAction.bind(this));
    this.widget('DeleteAllNodesMenu').on('action', this._onDeleteAllNodesMenuAction.bind(this));
    this.widget('DeleteAllChildNodesMenu').on('action', this._onDeleteAllChildNodesMenuAction.bind(this));

    this.widget('PropertiesBox').setTree(this.tree);
    var treeField = this.widget('TreeField');
    this.widget('FormFieldPropertiesBox').setField(treeField);
    this.widget('GridDataBox').setField(treeField);
    this.widget('WidgetActionsBox').setField(treeField);
    this.widget('FormFieldActionsBox').setField(treeField);
    this.widget('EventsTab').setField(this.tree);

    this.tree.insertNodes([this._createNodeWithChildren(), this._createNodeWithChildren(), this._createNodeWithChildren()]);
  }

  _createNode() {
    var iconList = [icons.STAR_BOLD, icons.PERSON_SOLID, icons.FOLDER_BOLD];

    var icon = iconList[this.nodeNo % iconList.length];
    var text = 'Node #' + this.nodeNo;
    this.nodeNo++;

    return {
      iconId: icon,
      text: text
    };
  }

  _createNodeWithChildren() {
    var node = this._createNode();
    node.childNodes = [this._createNode(), this._createNode(), this._createNode()];
    return node;
  }

  _onAddNodeMenuAction() {
    this.tree.insertNode(this._createNode());
  }

  _onAddChildNodeMenuAction() {
    this.tree.insertNode(this._createNode(), this.tree.selectedNodes[0]);
  }

  _onDeleteNodeMenuAction() {
    this.tree.deleteNodes(this.tree.selectedNodes);
  }

  _onDeleteAllNodesMenuAction() {
    this.tree.deleteAllNodes();
  }

  _onDeleteAllChildNodesMenuAction() {
    this.tree.deleteAllChildNodes(this.tree.selectedNodes[0]);
  }
}
