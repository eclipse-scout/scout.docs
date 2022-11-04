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
