/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, GroupBox, GroupBoxModel, LookupCall, NumberField, SmartField, SmartFieldActiveFilter, SmartFieldDisplayStyle} from '@eclipse-scout/core';
import {LookupCallLookupCall, SmartFieldActiveFilterLookupCall, SmartFieldDisplayStyleLookupCall} from '../index';

export default (): GroupBoxModel => ({
  id: 'jswidgets.SmartFieldPropertiesBox',
  objectType: GroupBox,
  label: 'Smart Field Properties',
  expandable: true,
  fields: [
    {
      id: 'LookupCallField',
      objectType: SmartField<LookupCall<any>>,
      label: 'LookupCall',
      lookupCall: LookupCallLookupCall
    },
    {
      id: 'DisplayStyleField',
      objectType: SmartField<SmartFieldDisplayStyle>,
      lookupCall: SmartFieldDisplayStyleLookupCall,
      label: 'Display Style'
    },
    {
      id: 'BrowseMaxRowCountField',
      objectType: NumberField,
      label: 'Browse Max Row Count'
    },
    {
      id: 'SearchRequiredField',
      objectType: CheckBoxField,
      label: 'Search Required'
    },
    {
      id: 'ActiveFilterEnabledField',
      objectType: CheckBoxField,
      label: 'Active Filter Enabled'
    },
    {
      id: 'ActiveFilterField',
      objectType: SmartField<SmartFieldActiveFilter>,
      displayStyle: SmartField.DisplayStyle.DROPDOWN,
      lookupCall: SmartFieldActiveFilterLookupCall,
      label: 'Active Filter'
    }
  ]
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type SmartFieldPropertiesBoxWidgetMap = {
  'LookupCallField': SmartField<LookupCall<any>>;
  'DisplayStyleField': SmartField<SmartFieldDisplayStyle>;
  'BrowseMaxRowCountField': NumberField;
  'SearchRequiredField': CheckBoxField;
  'ActiveFilterEnabledField': CheckBoxField;
  'ActiveFilterField': SmartField<SmartFieldActiveFilter>;
};
