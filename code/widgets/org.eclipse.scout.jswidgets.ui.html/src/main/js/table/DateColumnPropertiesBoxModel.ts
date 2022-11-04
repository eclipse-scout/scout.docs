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
