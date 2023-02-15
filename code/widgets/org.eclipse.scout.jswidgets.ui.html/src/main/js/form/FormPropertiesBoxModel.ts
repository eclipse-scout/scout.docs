/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, DisplayHint, DisplayViewId, GroupBox, GroupBoxModel, IntegerField, SmartField, StringField} from '@eclipse-scout/core';
import {DisplayParentLookupCall, FormDisplayHintLookupCall, FormDisplayViewIdLookupCall, IconIdLookupCall} from '../index';

export default (): GroupBoxModel => ({
  id: 'jswidgets.FormPropertiesBox',
  objectType: GroupBox,
  label: 'Properties',
  labelVisible: false,
  borderVisible: false,
  fields: [
    {
      id: 'TitleField',
      objectType: StringField,
      label: 'Title'
    },
    {
      id: 'SubTitleField',
      objectType: StringField,
      label: 'Sub Title'
    },
    {
      id: 'NotificationCountField',
      objectType: IntegerField,
      label: 'Notification count'
    },
    {
      id: 'IconIdField',
      objectType: SmartField,
      lookupCall: IconIdLookupCall,
      label: 'Icon Id'
    },
    {
      id: 'DisplayHintField',
      objectType: SmartField,
      lookupCall: FormDisplayHintLookupCall,
      label: 'Display Hint'
    },
    {
      id: 'DisplayViewIdField',
      objectType: SmartField,
      lookupCall: FormDisplayViewIdLookupCall,
      label: 'Display View Id'
    },
    {
      id: 'DisplayParentField',
      objectType: SmartField,
      lookupCall: DisplayParentLookupCall,
      label: 'Display Parent'
    },
    {
      id: 'AskIfNeedSaveField',
      objectType: CheckBoxField,
      label: 'Ask If Need Save',
      labelVisible: false
    },
    {
      id: 'CacheBoundsField',
      objectType: CheckBoxField,
      label: 'Cache Bounds',
      labelVisible: false
    },
    {
      id: 'ClosableField',
      objectType: CheckBoxField,
      label: 'Closable',
      labelVisible: false
    },
    {
      id: 'MovableField',
      objectType: CheckBoxField,
      label: 'Movable',
      labelVisible: false
    },
    {
      id: 'ResizableField',
      objectType: CheckBoxField,
      label: 'Resizable',
      labelVisible: false
    },
    {
      id: 'ModalField',
      objectType: CheckBoxField,
      label: 'Modal',
      labelVisible: false
    },
    {
      id: 'HeaderVisibleField',
      objectType: CheckBoxField,
      label: 'Header Visible',
      labelVisible: false,
      triStateEnabled: true
    },
    {
      id: 'MaximizedField',
      objectType: CheckBoxField,
      label: 'Maximized',
      labelVisible: false
    }
  ]
});

export type FormPropertiesBoxWidgetMap = {
  'TitleField': StringField;
  'SubTitleField': StringField;
  'NotificationCountField': IntegerField;
  'IconIdField': SmartField<string>;
  'DisplayHintField': SmartField<DisplayHint>;
  'DisplayViewIdField': SmartField<DisplayViewId>;
  'DisplayParentField': SmartField<string>;
  'AskIfNeedSaveField': CheckBoxField;
  'CacheBoundsField': CheckBoxField;
  'ClosableField': CheckBoxField;
  'MovableField': CheckBoxField;
  'ResizableField': CheckBoxField;
  'ModalField': CheckBoxField;
  'HeaderVisibleField': CheckBoxField;
  'MaximizedField': CheckBoxField;
};
