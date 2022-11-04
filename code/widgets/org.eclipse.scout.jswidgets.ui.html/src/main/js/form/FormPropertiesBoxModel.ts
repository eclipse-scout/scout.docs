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
import {CheckBoxField, DisplayHint, DisplayViewId, GroupBox, GroupBoxModel, SmartField, StringField} from '@eclipse-scout/core';

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
      id: 'IconIdField',
      objectType: SmartField,
      lookupCall: 'jswidgets.IconIdLookupCall',
      label: 'Icon Id'
    },
    {
      id: 'DisplayHintField',
      objectType: SmartField,
      lookupCall: 'jswidgets.FormDisplayHintLookupCall',
      label: 'Display Hint'
    },
    {
      id: 'DisplayViewIdField',
      objectType: SmartField,
      lookupCall: 'jswidgets.FormDisplayViewIdLookupCall',
      label: 'Display View Id'
    },
    {
      id: 'DisplayParentField',
      objectType: SmartField,
      lookupCall: 'jswidgets.DisplayParentLookupCall',
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
