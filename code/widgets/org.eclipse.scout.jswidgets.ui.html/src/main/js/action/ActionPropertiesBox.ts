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
import {GroupBox, MenuBarBox, models} from '@eclipse-scout/core';
import ActionPropertiesBoxModel from './ActionPropertiesBoxModel';

export default class ActionPropertiesBox extends GroupBox {

  constructor() {
    super();
    this.field = null;
  }

  _jsonModel() {
    return models.get(ActionPropertiesBoxModel);
  }

  _init(model) {
    super._init(model);

    this._setField(this.field);
  }

  setField(field) {
    this.setProperty('field', field);
  }

  // noinspection DuplicatedCode
  _setField(field) {
    this._setProperty('field', field);
    this.setEnabled(this.field);
    if (!this.field) {
      return;
    }
    let enabledField = this.widget('EnabledField');
    enabledField.setValue(this.field.enabled);
    enabledField.on('propertyChange:value', event => this.field.setEnabled(event.newValue));

    let visibleField = this.widget('VisibleField');
    visibleField.setValue(this.field.visible);
    visibleField.on('propertyChange:value', event => this.field.setVisible(event.newValue));

    let toggleActionField = this.widget('ToggleActionField');
    toggleActionField.setValue(this.field.toggleAction);
    toggleActionField.on('propertyChange:value', event => this.field.setToggleAction(event.newValue));

    let selectedField = this.widget('SelectedField');
    selectedField.setValue(this.field.selected);
    selectedField.on('propertyChange:value', event => this.field.setSelected(event.newValue));

    let preventDoubleClickField = this.widget('PreventDoubleClickField');
    preventDoubleClickField.setValue(this.field.preventDoubleClick);
    preventDoubleClickField.on('propertyChange:value', event => this.field.setPreventDoubleClick(event.newValue));

    let inheritAccessibilityField = this.widget('InheritAccessibilityField');
    inheritAccessibilityField.setValue(this.field.inheritAccessibility);
    inheritAccessibilityField.on('propertyChange:value', event => this.field.setInheritAccessibility(event.newValue));

    let iconIdField = this.widget('IconIdField');
    iconIdField.setValue(this.field.iconId);
    iconIdField.on('propertyChange:value', event => this.field.setIconId(event.newValue));

    let keyStrokeField = this.widget('KeyStrokeField');
    keyStrokeField.setValue(this.field.keyStroke);
    keyStrokeField.on('propertyChange:value', event => this.field.setKeyStroke(event.newValue));

    let textField = this.widget('TextField');
    textField.setValue(this.field.text);
    textField.on('propertyChange:value', event => this.field.setText(event.newValue));

    let textPositionField = this.widget('TextPositionField');
    textPositionField.setValue(this.field.textPosition);
    textPositionField.on('propertyChange:value', event => this.field.setTextPosition(event.newValue));

    let tooltipTextField = this.widget('TooltipTextField');
    tooltipTextField.setValue(this.field.tooltipText);
    tooltipTextField.on('propertyChange:value', event => this.field.setTooltipText(event.newValue));

    let horizontalAlignmentField = this.widget('HorizontalAlignmentField');
    horizontalAlignmentField.setValue(this.field.horizontalAlignment);
    horizontalAlignmentField.on('propertyChange:value', event => this.field.setHorizontalAlignment(event.newValue));

    let actionStyleField = this.widget('ActionStyleField');
    actionStyleField.setValue(this.field.actionStyle);
    actionStyleField.on('propertyChange:value', event => {
      // ActionStyle may not be changed during run time officially, use this little hack to work around by rerendering the whole menu bar
      this.field.actionStyle = event.newValue;
      if (this.field.parent instanceof MenuBarBox) {
        let menuItems = this.field.parent.menuItems;
        this.field.parent.setMenuItems([]);
        this.field.parent.setMenuItems(menuItems);
      }
    });
  }
}
