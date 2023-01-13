/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, GroupBox, GroupBoxModel} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  id: 'jswidgets.WidgetPopupPropertiesBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'Widget Popup Properties',
  expandable: true,
  fields: [
    {
      id: 'ClosableField',
      objectType: CheckBoxField,
      label: 'Closable',
      labelVisible: false
    },
    {
      id: 'MovableField',
      objectType: CheckBoxField,
      label: 'Movable',
      labelVisible: false
    },
    {
      id: 'ResizableField',
      objectType: CheckBoxField,
      label: 'Resizable',
      labelVisible: false
    }
  ]
});

export type WidgetPopupPropertiesBoxWidgetMap = {
  'ClosableField': CheckBoxField;
  'MovableField': CheckBoxField;
  'ResizableField': CheckBoxField;
};
