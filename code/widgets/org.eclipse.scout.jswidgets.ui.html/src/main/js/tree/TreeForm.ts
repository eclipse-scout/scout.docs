/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {Form, FormModel, icons, InitModelOf, models, Tree, TreeNodeModel} from '@eclipse-scout/core';
import TreeFormModel from './TreeFormModel';
import {TreeFormWidgetMap} from '../index';

export class TreeForm extends Form {
  declare widgetMap: TreeFormWidgetMap;

  tree: Tree;
  nodeNo: number;

  constructor() {
    super();

    this.nodeNo = 0;
  }

  protected override _jsonModel(): FormModel {
    return models.get(TreeFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.tree = this.widget('Tree');
    this.widget('AddNodeMenu').on('action', this._onAddNodeMenuAction.bind(this));
    this.widget('AddChildNodeMenu').on('action', this._onAddChildNodeMenuAction.bind(this));
    this.widget('DeleteNodeMenu').on('action', this._onDeleteNodeMenuAction.bind(this));
    this.widget('DeleteAllNodesMenu').on('action', this._onDeleteAllNodesMenuAction.bind(this));
    this.widget('DeleteAllChildNodesMenu').on('action', this._onDeleteAllChildNodesMenuAction.bind(this));

    this.widget('PropertiesBox').setTree(this.tree);
    let treeField = this.widget('TreeField');
    this.widget('FormFieldPropertiesBox').setField(treeField);
    this.widget('GridDataBox').setField(treeField);
    this.widget('WidgetActionsBox').setField(treeField);
    this.widget('FormFieldActionsBox').setField(treeField);
    this.widget('EventsTab').setField(this.tree);

    this.tree.insertNodes([this._createNodeWithChildren(), this._createNodeWithChildren(), this._createNodeWithChildren()]);
  }

  protected _createNode(): TreeNodeModel {
    let iconList = [icons.STAR, icons.CLOCK, icons.FOLDER];

    let icon = iconList[this.nodeNo % iconList.length];
    let text = 'Node #' + this.nodeNo;
    this.nodeNo++;

    return {
      iconId: icon,
      text: text
    };
  }

  protected _createNodeWithChildren(): TreeNodeModel {
    let node = this._createNode();
    node.childNodes = [this._createNode(), this._createNode(), this._createNode()];
    return node;
  }

  protected _onAddNodeMenuAction() {
    this.tree.insertNode(this._createNode());
  }

  protected _onAddChildNodeMenuAction() {
    this.tree.insertNode(this._createNode(), this.tree.selectedNodes[0]);
  }

  protected _onDeleteNodeMenuAction() {
    this.tree.deleteNodes(this.tree.selectedNodes);
  }

  protected _onDeleteAllNodesMenuAction() {
    this.tree.deleteAllNodes();
  }

  protected _onDeleteAllChildNodesMenuAction() {
    this.tree.deleteAllChildNodes(this.tree.selectedNodes[0]);
  }
}
