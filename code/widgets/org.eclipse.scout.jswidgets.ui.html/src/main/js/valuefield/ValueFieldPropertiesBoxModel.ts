/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {GroupBox, GroupBoxModel, SmartField, StringField, ValueFieldClearable} from '@eclipse-scout/core';
import {ClearableStyleLookupCall} from '../index';

export default (): GroupBoxModel => ({
  id: 'jswidgets.ValueFieldPropertiesBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'Value Field Properties',
  expandable: true,
  fields: [
    {
      id: 'ValueField',
      objectType: StringField,
      label: 'Value',
      enabled: false
    },
    {
      id: 'DisplayTextField',
      objectType: StringField,
      label: 'DisplayText',
      enabled: false
    },
    {
      id: 'ClearableField',
      objectType: SmartField,
      lookupCall: ClearableStyleLookupCall,
      label: 'Clearable',
      displayStyle: 'dropdown'
    }
  ]
});

export type ValueFieldPropertiesBoxWidgetMap = {
  'ValueField': StringField;
  'DisplayTextField': StringField;
  'ClearableField': SmartField<ValueFieldClearable>;
};
