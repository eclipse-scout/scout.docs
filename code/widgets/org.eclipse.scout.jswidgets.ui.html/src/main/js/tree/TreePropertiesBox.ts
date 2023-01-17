/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import TreePropertiesBoxModel from './TreePropertiesBoxModel';
import {TreePropertiesBoxWidgetMap} from '../index';
import {GroupBox, GroupBoxModel, InitModelOf, models, Tree} from '@eclipse-scout/core';

export class TreePropertiesBox extends GroupBox {
  declare widgetMap: TreePropertiesBoxWidgetMap;

  tree: Tree;

  constructor() {
    super();
    this.tree = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(TreePropertiesBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setTree(this.tree);
  }

  setTree(tree: Tree) {
    this.setProperty('tree', tree);
  }

  protected _setTree(tree: Tree) {
    this._setProperty('tree', tree);
    if (!this.tree) {
      return;
    }

    let autoCheckChildrenField = this.widget('AutoCheckChildrenField');
    autoCheckChildrenField.setValue(this.tree.autoCheckChildren);
    autoCheckChildrenField.on('propertyChange:value', event => {
      this.tree.autoCheckChildren = event.newValue;
    });

    let checkableField = this.widget('CheckableField');
    checkableField.setValue(this.tree.checkable);
    checkableField.on('propertyChange:value', event => this.tree.setCheckable(event.newValue));

    let multiCheckField = this.widget('MultiCheckField');
    multiCheckField.setValue(this.tree.multiCheck);
    multiCheckField.on('propertyChange:value', event => {
      this.tree.multiCheck = event.newValue;
    });

    let textFilterEnabledField = this.widget('TextFilterEnabledField');
    textFilterEnabledField.setValue(this.tree.textFilterEnabled);
    textFilterEnabledField.on('propertyChange:value', event => this.tree.setTextFilterEnabled(event.newValue));

    let checkableStyleField = this.widget('CheckableStyleField');
    checkableStyleField.setValue(this.tree.checkableStyle);
    checkableStyleField.on('propertyChange:value', event => this.tree.setCheckableStyle(event.newValue));
  }
}
