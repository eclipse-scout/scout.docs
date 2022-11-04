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
import {Button, GroupBox, GroupBoxModel, SmartField, TabItem} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  id: 'jswidgets.TabBoxDeleteTabItemBox',
  objectType: GroupBox,
  label: 'Delete Tab Item',
  expandable: true,
  gridColumnCount: 2,
  fields: [
    {
      id: 'DeleteTabItem.TabItem',
      objectType: SmartField,
      label: 'Tab Item'
    },
    {
      id: 'DeleteTabItem.DeleteButton',
      objectType: Button,
      label: 'Delete',
      enabled: false,
      processButton: false,
      keyStroke: 'ctrl-delete'
    }
  ]
});

export type TabBoxDeleteTabItemBoxWidgetMap = {
  'DeleteTabItem.TabItem': SmartField<TabItem>;
  'DeleteTabItem.DeleteButton': Button;
};
