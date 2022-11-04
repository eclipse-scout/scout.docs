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
