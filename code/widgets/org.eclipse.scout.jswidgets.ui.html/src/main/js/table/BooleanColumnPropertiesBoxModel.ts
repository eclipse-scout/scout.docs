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
  id: 'jswidgets.BooleanColumnPropertiesBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'Boolean Column Properties',
  expandable: true,
  fields: [
    {
      id: 'TriStateEnabledField',
      objectType: CheckBoxField,
      label: 'Tri State Enabled',
      labelVisible: false
    }
  ]
});

export type BooleanColumnPropertiesBoxWidgetMap = {
  'TriStateEnabledField': CheckBoxField;
};
