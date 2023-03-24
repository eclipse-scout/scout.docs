/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, CheckBoxField, GroupBox, GroupBoxModel, Menu, SequenceBox, SmartField} from '@eclipse-scout/core';

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
    },
    {
      id: 'MarkAsSavedButton',
      objectType: Button,
      label: '${textKey:MarkAsSaved}',
      labelVisible: false,
      processButton: false,
      displayStyle: Button.DisplayStyle.LINK
    },
    {
      id: 'TouchButton',
      objectType: Button,
      label: '${textKey:Touch}',
      labelVisible: false,
      processButton: false,
      displayStyle: Button.DisplayStyle.LINK
    },
    {
      id: 'SaveNeededField',
      objectType: CheckBoxField,
      labelVisible: false,
      label: 'Save Needed',
      enabled: false
    },
    {
      id: 'TouchedField',
      objectType: CheckBoxField,
      labelVisible: false,
      label: 'Touched',
      enabled: false
    }
  ]
});

export type FormFieldActionsBoxWidgetMap = {
  'InsertMenuButton': Button;
  'DeleteMenuBox': SequenceBox;
  'MenuToDeleteField': SmartField<Menu>;
  'MarkAsSavedButton': Button;
  'DeleteMenuButton': Button;
  'SaveNeededField': CheckBoxField;
  'TouchedField': CheckBoxField;
  'TouchButton': Button;
};
