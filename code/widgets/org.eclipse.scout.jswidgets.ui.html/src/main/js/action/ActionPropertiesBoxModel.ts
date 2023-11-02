/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, GroupBox, GroupBoxModel, NumberField, SmartField, StringField} from '@eclipse-scout/core';
import {ActionStyleLookupCall, IconIdLookupCall, TextPositionLookupCall} from '../index';

export default (): GroupBoxModel => ({
  id: 'jswidgets.ActionPropertiesBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'Action Properties',
  expandable: true,
  fields: [
    {
      id: 'EnabledField',
      objectType: CheckBoxField,
      label: 'Enabled',
      labelVisible: false
    },
    {
      id: 'VisibleField',
      objectType: CheckBoxField,
      label: 'Visible',
      labelVisible: false
    },
    {
      id: 'ToggleActionField',
      objectType: CheckBoxField,
      label: 'Toggle Action',
      labelVisible: false
    },
    {
      id: 'SelectedField',
      objectType: CheckBoxField,
      label: 'Selected',
      labelVisible: false
    },
    {
      id: 'PreventDoubleClickField',
      objectType: CheckBoxField,
      label: 'Prevent Double Click',
      labelVisible: false
    },
    {
      id: 'InheritAccessibilityField',
      objectType: CheckBoxField,
      label: 'Inherit Accessibility',
      labelVisible: false
    },
    {
      id: 'LoadingField',
      objectType: CheckBoxField,
      label: 'Loading',
      labelVisible: false
    },
    {
      id: 'IconIdField',
      objectType: SmartField,
      lookupCall: IconIdLookupCall,
      label: 'Icon Id'
    },
    {
      id: 'KeyStrokeField',
      objectType: StringField,
      label: 'Key Stroke'
    },
    {
      id: 'TextField',
      objectType: StringField,
      label: 'Text'
    },
    {
      id: 'TextPositionField',
      objectType: SmartField,
      lookupCall: TextPositionLookupCall,
      displayStyle: 'dropdown',
      label: 'Text Position'
    },
    {
      id: 'TooltipTextField',
      objectType: StringField,
      label: 'Tooltip Text'
    },
    {
      id: 'HorizontalAlignmentField',
      objectType: NumberField,
      label: 'Horizontal Alignment'
    },
    {
      id: 'ActionStyleField',
      objectType: SmartField,
      lookupCall: ActionStyleLookupCall,
      label: 'Action Style',
      displayStyle: 'dropdown'
    }
  ]
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type ActionPropertiesBoxWidgetMap = {
  'EnabledField': CheckBoxField;
  'VisibleField': CheckBoxField;
  'ToggleActionField': CheckBoxField;
  'SelectedField': CheckBoxField;
  'PreventDoubleClickField': CheckBoxField;
  'InheritAccessibilityField': CheckBoxField;
  'LoadingField': CheckBoxField;
  'IconIdField': SmartField<any>;
  'KeyStrokeField': StringField;
  'TextField': StringField;
  'TextPositionField': SmartField<any>;
  'TooltipTextField': StringField;
  'HorizontalAlignmentField': NumberField;
  'ActionStyleField': SmartField<any>;
};
