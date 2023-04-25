/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, GroupBox, GroupBoxModel, SmartField, StringField} from '@eclipse-scout/core';
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
      id: 'NotificationBadgeTextField',
      objectType: StringField,
      label: 'Notification badge text'
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
      id: 'ExclusiveKeyField',
      objectType: StringField,
      label: 'Exclusive Key'
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
      labelVisible: false,
      triStateEnabled: true
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
    },
    {
      id: 'SaveNeededVisibleField',
      objectType: CheckBoxField,
      label: 'Save Needed Visible',
      labelVisible: false
    }
  ]
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type FormPropertiesBoxWidgetMap = {
  'TitleField': StringField;
  'SubTitleField': StringField;
  'NotificationBadgeTextField': StringField;
  'IconIdField': SmartField<any>;
  'DisplayHintField': SmartField<any>;
  'DisplayViewIdField': SmartField<any>;
  'DisplayParentField': SmartField<any>;
  'ExclusiveKeyField': StringField;
  'AskIfNeedSaveField': CheckBoxField;
  'CacheBoundsField': CheckBoxField;
  'ClosableField': CheckBoxField;
  'MovableField': CheckBoxField;
  'ResizableField': CheckBoxField;
  'ModalField': CheckBoxField;
  'HeaderVisibleField': CheckBoxField;
  'MaximizedField': CheckBoxField;
  'SaveNeededVisibleField': CheckBoxField;
};
