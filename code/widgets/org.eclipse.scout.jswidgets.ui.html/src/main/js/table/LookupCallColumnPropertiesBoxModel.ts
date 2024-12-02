/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, GroupBox, GroupBoxModel, NumberField, SmartField} from '@eclipse-scout/core';
import {LookupCallLookupCall} from '../lookup/LookupCallLookupCall';

export default (): GroupBoxModel => ({
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'LookupCallColumn Properties',
  expandable: true,
  fields: [
    {
      id: 'LookupCallField',
      objectType: SmartField,
      label: 'LookupCall',
      lookupCall: LookupCallLookupCall
    },
    {
      id: 'BrowseMaxRowCountField',
      objectType: NumberField,
      label: 'Brows Max Row Count'
    },
    {
      id: 'BrowseHierarchyField',
      objectType: CheckBoxField,
      label: 'Browse Hierarchy',
      labelVisible: false
    }
  ]
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type LookupCallColumnPropertiesBoxWidgetMap = {
  'LookupCallField': SmartField<any>;
  'BrowseMaxRowCountField': NumberField;
  'BrowseHierarchyField': CheckBoxField;
};
