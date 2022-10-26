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
import {Form, models} from '@eclipse-scout/core';
import TreeBoxFormModel from './TreeBoxFormModel';

export default class TreeBoxForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(TreeBoxFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    this.treeBox = this.widget('TreeBox');

    this.lookupCallField = this.widget('LookupCallField');
    this.lookupCallField.setValue(this.treeBox.lookupCall);
    this.lookupCallField.on('propertyChange:value', event => this.treeBox.setLookupCall(event.newValue));
    this.treeBox.on('propertyChange:lookupCall', event => this.lookupCallField.setValue(event.newValue));

    this.widget('ValueFieldPropertiesBox').setField(this.treeBox);
    this.widget('FormFieldPropertiesBox').setField(this.treeBox);
    this.widget('PropertiesBox').setTree(this.treeBox.tree);
    this.widget('GridDataBox').setField(this.treeBox);
    this.widget('WidgetActionsBox').setField(this.treeBox);
    this.widget('FormFieldActionsBox').setField(this.treeBox);
    this.widget('EventsTab').setField(this.treeBox);
  }
}
