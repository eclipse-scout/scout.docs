/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, GroupBox, GroupBoxModel, Menu, SmartField} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  id: 'jswidgets.GroupBoxDeleteMenuBox',
  objectType: GroupBox,
  label: 'Delete menu',
  expandable: true,
  gridColumnCount: 2,
  fields: [
    {
      id: 'MenuToDeleteField',
      objectType: SmartField,
      label: 'Menubar Item'
    },
    {
      id: 'DeleteButton',
      objectType: Button,
      label: 'Delete',
      enabled: false,
      processButton: false,
      keyStroke: 'ctrl-delete'
    }
  ]
});

export type GroupBoxDeleteMenuBoxWidgetMap = {
  'MenuToDeleteField': SmartField<Menu>;
  'DeleteButton': Button;
};
