/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, CheckBoxField, ExtensionModel, NumberField, SequenceBox, SliderField} from '@eclipse-scout/core';
import {NumberFieldFormWidgetMap} from '../index';

export default (): ExtensionModel => ({
  type: 'extension',
  id: 'SliderFieldForm',
  extensions: [
    {
      operation: 'appendTo',
      target: {id: 'NumberField'},
      extension: {
        objectType: SliderField,
        label: 'Slider Field'
      }
    },
    {
      operation: 'appendTo',
      target: {id: 'CalculatorField'},
      extension: {
        objectType: SliderField
      }
    },
    {
      operation: 'insert',
      target: {
        id: 'PropertiesBox',
        property: 'fields'
      },
      extension: {
        id: 'StepField',
        objectType: NumberField,
        label: 'Step'
      }
    },
    {
      operation: 'insert',
      target: {
        id: 'PropertiesBox',
        property: 'fields'
      },
      extension: {
        id: 'ValueEditableField',
        objectType: CheckBoxField,
        labelVisible: false,
        label: 'Value Editable'
      }
    },
    {
      operation: 'insert',
      target: {
        id: 'PropertiesBox',
        property: 'fields'
      },
      extension: {
        id: 'SliderTabbableBox',
        objectType: SequenceBox,
        labelVisible: false,
        fields: [
          {
            id: 'SliderTabbableField',
            objectType: CheckBoxField,
            label: 'Slider Tabbable',
            labelVisible: false,
            statusVisible: false,
            gridDataHints: {
              useUiWidth: true,
              weightX: 0
            }
          },
          {
            id: 'FocusButton',
            objectType: Button,
            label: 'Focus',
            displayStyle: Button.DisplayStyle.LINK,
            processButton: false,
            visible: false
          }
        ]
      }
    }
  ]
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type SliderFieldFormWidgetMap = {
  'StepField': NumberField;
  'ValueEditableField': CheckBoxField;
  'SliderTabbableBox': SequenceBox;
  'SliderTabbableField': CheckBoxField;
  'FocusButton': Button;
} & NumberFieldFormWidgetMap;
