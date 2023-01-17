/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, GroupBox, GroupBoxModel, StringField} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  id: 'jswidgets.DateColumnPropertiesBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'DateColumn Properties',
  expandable: true,
  fields: [
    {
      id: 'FormatField',
      objectType: StringField,
      label: 'Format'
    },
    {
      id: 'GroupFormatField',
      objectType: StringField,
      label: 'Group Format'
    },
    {
      id: 'HasDateField',
      objectType: CheckBoxField,
      label: 'Has Date',
      labelVisible: false
    },
    {
      id: 'HasTimeField',
      objectType: CheckBoxField,
      label: 'Has Time',
      labelVisible: false
    }
  ]
});

export type DateColumnPropertiesBoxWidgetMap = {
  'FormatField': StringField;
  'GroupFormatField': StringField;
  'HasDateField': CheckBoxField;
  'HasTimeField': CheckBoxField;
};
