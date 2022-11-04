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
import {GroupBox, GroupBoxModel, SmartField, StringField, ValueFieldClearable} from '@eclipse-scout/core';

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
      lookupCall: 'jswidgets.ClearableStyleLookupCall',
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
