/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
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

    var autoCheckChildrenField = this.widget('AutoCheckChildrenField');
    autoCheckChildrenField.setValue(this.tree.autoResizeColumns);
    autoCheckChildrenField.on('propertyChange', this._onAutoCheckChildrenPropertyChange.bind(this));

    var checkableField = this.widget('CheckableField');
    checkableField.setValue(this.tree.checkable);
    checkableField.on('propertyChange', this._onCheckablePropertyChange.bind(this));

    var multiCheckField = this.widget('MultiCheckField');
    multiCheckField.setValue(this.tree.multiCheck);
    multiCheckField.on('propertyChange', this._onMultiCheckPropertyChange.bind(this));

    var checkableStyleField = this.widget('CheckableStyleField');
    checkableStyleField.setValue(this.tree.checkableStyle);
    checkableStyleField.on('propertyChange', this._onCheckableStylePropertyChange.bind(this));
  }

  _onAutoCheckChildrenPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.tree.autoCheckChildren = event.newValue;
    }
  }

  _onCheckablePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.tree.setCheckable(event.newValue);
    }
  }

  _onMultiCheckPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.tree.multiCheck = event.newValue;
    }
  }

  _onCheckableStylePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.tree.setCheckableStyle(event.newValue);
    }
  }
}
