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
import {GroupBox, GroupBoxModel, NumberField} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  id: 'jswidgets.SmartColumnPropertiesBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'SmartColumn Properties',
  expandable: true,
  fields: [
    {
      id: 'BrowseMaxRowCountField',
      objectType: NumberField,
      label: 'Brows Max Row Count'
    }
  ]
});

export type SmartColumnPropertiesBoxWidgetMap = {
  'BrowseMaxRowCountField': NumberField;
};
