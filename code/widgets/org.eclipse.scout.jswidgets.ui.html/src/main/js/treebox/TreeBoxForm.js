/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {Form, models, ObjectFactory} from '@eclipse-scout/core';
import TreeBoxFormModel from './TreeBoxFormModel';

export default class TreeBoxForm extends Form {

constructor() {
  super();
}


_jsonModel() {
  return models.get(TreeBoxFormModel);
}

_init(model) {
  super._init( model);

  var generateButton = this.widget('GenerateTreeDataButton');
  generateButton.on('click', this._onGenerateButtonClick.bind(this));

  this.treeBox = this.widget('TreeBox');

  this.widget('ValueFieldPropertiesBox').setField(this.treeBox);
  this.widget('FormFieldPropertiesBox').setField(this.treeBox);
  this.widget('PropertiesBox').setTree(this.treeBox.tree);
  this.widget('GridDataBox').setField(this.treeBox);
  this.widget('WidgetActionsBox').setField(this.treeBox);
  this.widget('EventsTab').setField(this.treeBox);

  this.treeBox.tree.insertNodes(this.createModelNodes(2, 1, true, true), null);
}

_onGenerateButtonClick(event) {
  var nodeCount = this.widget('NodeCountField').value;
  var depth = this.widget('DepthField').value;
  var defaultExpanded = this.widget('DefaultExpandedField').value;
  var defaultEnabled = this.widget('DefaultEnabledField').value;
  this.treeBox.tree.removeAllNodes();
  this.treeBox.tree.insertNodes(this.createModelNodes(nodeCount, depth, defaultExpanded === true, defaultEnabled === true), null);
}

createModelNode(id, text, position, enabled) {
  return {
    id: id + '' || ObjectFactory.get().createUniqueId(),
    text: text,
    childNodeIndex: position ? position : 0,
    enabled: enabled,
    checked: false
  };
}

createModelNodes(nodeCount, depth, expanded, enabled, parentNode) {
  if (!nodeCount) {
    return;
  }

  var nodes = [],
    nodeId;
  if (!depth) {
    depth = 0;
  }
  for (var i = 0; i < nodeCount; i++) {
    nodeId = i;
    if (parentNode) {
      nodeId = parentNode.id + '_' + nodeId;
    }
    nodes[i] = this.createModelNode(nodeId, 'node ' + nodeId, i, enabled);
    nodes[i].expanded = expanded;
    if (depth > 0) {
      nodes[i].childNodes = this.createModelNodes(nodeCount, depth - 1, expanded, enabled, nodes[i]);
    } else {
      nodes[i].leaf = true;
    }
  }
  return nodes;
}
}
