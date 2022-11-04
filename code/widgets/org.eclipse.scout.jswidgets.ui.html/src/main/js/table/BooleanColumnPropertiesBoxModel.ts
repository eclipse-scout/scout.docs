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
