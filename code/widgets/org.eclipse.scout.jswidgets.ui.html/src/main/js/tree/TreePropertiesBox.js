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
import TreePropertiesBoxModel from './TreePropertiesBoxModel';
import {GroupBox, models} from '@eclipse-scout/core';

export default class TreePropertiesBox extends GroupBox {

  constructor() {
    super();
    this.tree = null;
  }

  _jsonModel() {
    return models.get(TreePropertiesBoxModel);
  }

  _init(model) {
    super._init(model);

    this._setTree(this.tree);
  }

  setTree(tree) {
    this.setProperty('tree', tree);
  }

  _setTree(tree) {
    this._setProperty('tree', tree);
    if (!this.tree) {
      return;
    }

    let autoCheckChildrenField = this.widget('AutoCheckChildrenField');
    autoCheckChildrenField.setValue(this.tree.autoResizeColumns);
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
