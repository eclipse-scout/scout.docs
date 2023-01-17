/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, GroupBox, GroupBoxModel, SmartField, StringField, TabItem} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  id: 'jswidgets.TabBoxAddTabItemBox',
  objectType: GroupBox,
  label: 'Add Tab Item',
  expandable: true,
  gridColumnCount: 2,
  fields: [
    {
      id: 'AddTabItem.Label',
      objectType: StringField,
      label: 'Label'
    },
    {
      id: 'AddTabItem.SubLabel',
      objectType: StringField,
      label: 'Sub Label'
    },
    {
      id: 'AddTabItem.TabItemSmartField',
      objectType: SmartField,
      label: 'Before'
    },
    {
      id: 'AddTabItem.CreateButton',
      objectType: Button,
      label: 'Add',
      processButton: false,
      keyStroke: 'ctrl-insert'
    }
  ]
});

export type TabBoxAddTabItemBoxWidgetMap = {
  'AddTabItem.Label': StringField;
  'AddTabItem.SubLabel': StringField;
  'AddTabItem.TabItemSmartField': SmartField<TabItem>;
  'AddTabItem.CreateButton': Button;
};
