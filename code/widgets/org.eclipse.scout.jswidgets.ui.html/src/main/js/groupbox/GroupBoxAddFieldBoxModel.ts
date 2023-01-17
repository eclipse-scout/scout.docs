/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, FormField, GroupBox, GroupBoxModel, SmartField, StringField} from '@eclipse-scout/core';
import {FormFieldTypeLookupCall} from '../index';

export default (): GroupBoxModel => ({
  id: 'jswidgets.GroupBoxAddFieldBox',
  objectType: GroupBox,
  label: 'Add field',
  expandable: true,
  gridColumnCount: 2,
  fields: [
    {
      id: 'BeforeField',
      objectType: SmartField,
      label: 'Before'
    },
    {
      id: 'LabelField',
      objectType: StringField,
      label: 'Label'
    },
    {
      id: 'LabelType',
      objectType: SmartField,
      lookupCall: FormFieldTypeLookupCall,
      label: 'Type'
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

export type GroupBoxAddFieldBoxWidgetMap = {
  'BeforeField': SmartField<FormField>;
  'LabelField': StringField;
  'LabelType': SmartField<string>;
  'CreateButton': Button;
};
