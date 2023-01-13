/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, FormField, GroupBox, GroupBoxModel, SmartField} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  id: 'jswidgets.GroupBoxDeleteFieldBox',
  objectType: GroupBox,
  label: 'Delete field',
  expandable: true,
  gridColumnCount: 2,
  fields: [
    {
      id: 'ToDeleteField',
      objectType: SmartField,
      label: 'Field'
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

export type GroupBoxDeleteFieldBoxWidgetMap = {
  'ToDeleteField': SmartField<FormField>;
  'DeleteButton': Button;
};
