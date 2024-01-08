/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, LookupCall, models, SmartField, TreeBox} from '@eclipse-scout/core';
import TreeBoxFormModel from './TreeBoxFormModel';
import {TreeBoxFormWidgetMap} from '../index';

export class TreeBoxForm extends Form {
  declare widgetMap: TreeBoxFormWidgetMap;

  treeBox: TreeBox<any>;
  lookupCallField: SmartField<LookupCall<any>>;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(TreeBoxFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.treeBox = this.widget('TreeBox');

    this.lookupCallField = this.widget('LookupCallField');
    this.lookupCallField.setValue(this.treeBox.lookupCall);
    this.lookupCallField.on('propertyChange:value', event => this.treeBox.setLookupCall(event.newValue));
    this.treeBox.on('propertyChange:lookupCall', event => this.lookupCallField.setValue(event.newValue));

    this.widget('ValueField').setEnabled(true);
    this.widget('ValueFieldPropertiesBox').setField(this.treeBox);
    this.widget('FormFieldPropertiesBox').setField(this.treeBox);
    this.widget('PropertiesBox').setTree(this.treeBox.tree);
    this.widget('GridDataBox').setField(this.treeBox);
    this.widget('WidgetActionsBox').setField(this.treeBox);
    this.widget('FormFieldActionsBox').setField(this.treeBox);
    this.widget('EventsTab').setField(this.treeBox);
  }
}
