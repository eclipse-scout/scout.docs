/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, SmartField, TabAreaStyle, TabItem, TabItemModel} from '@eclipse-scout/core';
import {
  FormFieldPropertiesBox,
  FormFieldPropertiesBoxWidgetMap,
  GridDataBox,
  GridDataBoxWidgetMap,
  TabAreaStyleLookupCall
} from '../index';

export default (): TabItemModel => ({
  id: 'jswidgets.TabBoxProperties',
  objectType: TabItem,
  label: 'Tab Box Properties',
  fields: [
    {
      id: 'TabBoxProperties.SelectedTabField',
      objectType: SmartField,
      label: 'Selected Tab Item'
    },
    {
      id: 'TabBoxProperties.TabAreaStyleField',
      objectType: SmartField,
      lookupCall: TabAreaStyleLookupCall,
      label: 'Tab Area Style'
    },
    {
      id: 'TabBoxProperties.ShowMenus',
      objectType: CheckBoxField,
      label: 'Show Menus'
    },
    {
      id: 'TabBoxProperties.FormFieldPropertiesBox',
      objectType: FormFieldPropertiesBox
    },
    {
      id: 'TabBoxProperties.GridDataBox',
      objectType: GridDataBox,
      label: 'Grid Data Hints'
    }
  ]
});

export type TabBoxPropertiesWidgetMap = {
  'TabBoxProperties.SelectedTabField': SmartField<TabItem>;
  'TabBoxProperties.TabAreaStyleField': SmartField<TabAreaStyle>;
  'TabBoxProperties.ShowMenus': CheckBoxField;
  'TabBoxProperties.FormFieldPropertiesBox': FormFieldPropertiesBox;
  'TabBoxProperties.GridDataBox': GridDataBox;
} & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap;
