/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, scout, SmartField, Switch, SwitchDisplayStyle, WidgetField} from '@eclipse-scout/core';
import {SwitchJsFormWidgetMap} from '../../index';
import model from './SwitchJsFormModel';

export class SwitchJsForm extends Form {
  declare widgetMap: SwitchJsFormWidgetMap;

  protected override _jsonModel(): FormModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let switch_ = this.widget('Switch');

    let enabledField = this.widget('EnabledField');
    enabledField.setValue(switch_.enabled);
    enabledField.on('propertyChange:value', event => switch_.setEnabled(event.newValue));

    let activatedField = this.widget('ActivatedField');
    activatedField.setValue(switch_.activated);
    activatedField.on('propertyChange:value', event => switch_.setActivated(event.newValue));
    switch_.on('propertyChange:activated', event => activatedField.setValue(event.newValue));

    let labelVisibleField = this.widget('LabelVisibleField');
    labelVisibleField.setValue(switch_.labelVisible);
    labelVisibleField.on('propertyChange:value', event => switch_.setLabelVisible(event.newValue));

    let iconVisibleField = this.widget('IconVisibleField');
    iconVisibleField.setValue(switch_.iconVisible);
    iconVisibleField.on('propertyChange:value', event => switch_.setIconVisible(event.newValue));

    let focusButton = this.widget('FocusButton');
    focusButton.on('click', event => switch_.focus());

    let tabbableField = this.widget('TabbableField');
    tabbableField.setValue(switch_.tabbable);
    tabbableField.on('propertyChange:value', event => {
      switch_.setTabbable(event.newValue);
      focusButton.setVisible(event.newValue);
    });

    let labelField = this.widget('LabelField');
    labelField.setValue(switch_.label);
    labelField.on('propertyChange:value', event => switch_.setLabel(event.newValue));

    let labelHtmlEnabledField = this.widget('LabelHtmlEnabledField');
    labelHtmlEnabledField.setValue(switch_.labelHtmlEnabled);
    labelHtmlEnabledField.on('propertyChange:value', event => switch_.setLabelHtmlEnabled(event.newValue));

    let tooltipTextField = this.widget('TooltipTextField');
    tooltipTextField.setValue(switch_.tooltipText);
    tooltipTextField.on('propertyChange:value', event => switch_.setTooltipText(event.newValue));

    let displayStyleField = this.widget('DisplayStyleField') as SmartField<SwitchDisplayStyle>;
    displayStyleField.setValue(switch_.displayStyle);
    displayStyleField.on('propertyChange:value', event => switch_.setDisplayStyle(event.newValue));

    let preventDefaultField = this.widget('PreventDefaultField');
    switch_.on('switch', event => {
      if (preventDefaultField.value) {
        event.preventDefault();
      }
    });

    let fields = [];
    [Switch.DisplayStyle.DEFAULT, Switch.DisplayStyle.SLIDER].forEach(displayStyle => {
      [false, true].forEach(activated => {
        [false, true].forEach(iconVisible => {
          [true, false].forEach(enabled => {
            (enabled ? [false, true] : [false]).forEach(focused => {
              let dynamicSwitch = scout.create(Switch, {
                parent: this,
                focused: focused,
                enabled: enabled,
                activated: activated,
                iconVisible: iconVisible,
                displayStyle: displayStyle
              });
              dynamicSwitch.on('switch', event => event.preventDefault());
              fields.push(scout.create(WidgetField, {
                parent: this,
                labelVisible: false,
                statusVisible: false,
                scrollable: false,
                gridDataHints: {
                  useUiHeight: true,
                  fillHorizontal: false
                },
                fieldWidget: dynamicSwitch
              }));
            });
          });
        });
      });
    });
    let allCombinationsBox = this.widget('AllCombinationsBox');
    allCombinationsBox.setFields(fields);

    let allCombinationsSequenceBox = this.widget('AllCombinationsSequenceBox');
    let allCombinationsVisible = false; // set to true for development purposes
    allCombinationsSequenceBox.setVisible(allCombinationsVisible);
    let showAllCombinationsField = this.widget('ShowAllCombinationsField');
    showAllCombinationsField.setValue(allCombinationsVisible);
    showAllCombinationsField.on('propertyChange:value', event => {
      allCombinationsSequenceBox.setVisible(event.newValue);
    });
  }
}
