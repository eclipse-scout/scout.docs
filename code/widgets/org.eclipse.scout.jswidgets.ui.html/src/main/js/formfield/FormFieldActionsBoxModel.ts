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
import {Button, GroupBox, GroupBoxModel, Menu, SequenceBox, SmartField} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  id: 'jswidgets.FormFieldActionsBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'Form Field Actions',
  expandable: true,
  fields: [
    {
      id: 'InsertMenuButton',
      objectType: Button,
      label: 'Insert new menu',
      labelVisible: false,
      processButton: false,
      displayStyle: Button.DisplayStyle.LINK
    },
    {
      id: 'DeleteMenuBox',
      objectType: SequenceBox,
      labelVisible: false,
      fields: [
        {
          id: 'MenuToDeleteField',
          objectType: SmartField,
          labelVisible: false,
          statusVisible: false
        },
        {
          id: 'DeleteMenuButton',
          objectType: Button,
          label: 'Delete menu',
          labelVisible: false,
          processButton: false,
          displayStyle: Button.DisplayStyle.LINK
        }
      ]
    }
  ]
});

export type FormFieldActionsBoxWidgetMap = {
  'InsertMenuButton': Button;
  'DeleteMenuBox': SequenceBox;
  'MenuToDeleteField': SmartField<Menu>;
  'DeleteMenuButton': Button;
};
