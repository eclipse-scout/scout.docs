/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, CheckBoxField, GroupBox, GroupBoxModel, NumberField, SmartField, StringField} from '@eclipse-scout/core';
import {IconIdLookupCall, MenuBarItemTypeLookupCall} from '../index';

export default (): GroupBoxModel => ({
  id: 'jswidgets.GroupBoxAddMenuBox',
  objectType: GroupBox,
  label: 'Add menu',
  expandable: true,
  gridColumnCount: 2,
  fields: [
    {
      id: 'LabelField',
      objectType: StringField,
      label: 'Label'
    },
    {
      id: 'MenuBarItemType',
      objectType: SmartField,
      lookupCall: MenuBarItemTypeLookupCall,
      label: 'Type'
    },
    {
      id: 'IconIdField',
      objectType: SmartField,
      lookupCall: IconIdLookupCall,
      label: 'Icon Id'
    },
    {
      id: 'HorizontalAlignmentField',
      objectType: NumberField,
      label: 'Horizontal Alignment'
    },
    {
      id: 'StackableField',
      objectType: CheckBoxField,
      label: 'Stackable',
      labelVisible: false
    },
    {
      id: 'ShrinkableField',
      objectType: CheckBoxField,
      label: 'Shrinkable',
      labelVisible: false
    },
    {
      id: 'CreateButton',
      objectType: Button,
      label: 'Add',
      processButton: false,
      keyStroke: 'ctrl-insert'
    }
  ]
});

export type GroupBoxAddMenuBoxWidgetMap = {
  'LabelField': StringField;
  'MenuBarItemType': SmartField<'Button' | 'Menu'>;
  'IconIdField': SmartField<string>;
  'HorizontalAlignmentField': NumberField;
  'StackableField': CheckBoxField;
  'ShrinkableField': CheckBoxField;
  'CreateButton': Button;
};
