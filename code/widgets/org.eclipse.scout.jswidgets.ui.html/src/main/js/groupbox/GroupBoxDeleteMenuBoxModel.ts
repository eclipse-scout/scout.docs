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
