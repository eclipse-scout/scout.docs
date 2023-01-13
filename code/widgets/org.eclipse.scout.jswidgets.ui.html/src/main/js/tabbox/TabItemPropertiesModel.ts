/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, SmartField, TabItem, TabItemModel} from '@eclipse-scout/core';
import {FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, GroupBoxPropertiesBox, GroupBoxPropertiesBoxWidgetMap} from '../index';

export default (): TabItemModel => ({
  id: 'jswidgets.TabItemProperties',
  objectType: TabItem,
  label: 'Tab Item Properties',
  fields: [
    {
      id: 'TabItemProperties.TargetField',
      objectType: SmartField,
      label: 'Target Item'
    },
    {
      id: 'TabItemProperties.MarkedField',
      objectType: CheckBoxField,
      label: 'Marked',
      labelVisible: false
    },
    {
      id: 'TabItemProperties.GroupBoxPropertiesBox',
      objectType: GroupBoxPropertiesBox
    },
    {
      id: 'TabItemProperties.FormFieldPropertiesBox',
      objectType: FormFieldPropertiesBox
    },
    {
      id: 'TabItemProperties.GridDataBox',
      objectType: GridDataBox,
      label: 'Grid Data Hints'
    }
  ]
});

export type TabItemPropertiesWidgetMap = {
  'TabItemProperties.TargetField': SmartField<TabItem>;
  'TabItemProperties.MarkedField': CheckBoxField;
  'TabItemProperties.GroupBoxPropertiesBox': GroupBoxPropertiesBox;
  'TabItemProperties.FormFieldPropertiesBox': FormFieldPropertiesBox;
  'TabItemProperties.GridDataBox': GridDataBox;
} & GroupBoxPropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap;
