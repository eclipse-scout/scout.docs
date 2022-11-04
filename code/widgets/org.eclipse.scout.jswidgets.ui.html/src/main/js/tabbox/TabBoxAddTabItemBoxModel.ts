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
