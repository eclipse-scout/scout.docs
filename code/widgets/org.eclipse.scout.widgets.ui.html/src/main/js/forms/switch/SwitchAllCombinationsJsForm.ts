/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, scout, Switch, WidgetField} from '@eclipse-scout/core';
import model from './SwitchAllCombinationsJsFormModel';
import {SwitchAllCombinationsJsFormWidgetMap} from '../../index';

export class SwitchAllCombinationsJsForm extends Form {
  declare widgetMap: SwitchAllCombinationsJsFormWidgetMap;

  protected override _jsonModel(): FormModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

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
  }
}
