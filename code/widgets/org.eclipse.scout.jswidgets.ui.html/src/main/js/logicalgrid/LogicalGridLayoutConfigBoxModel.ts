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
  id: 'jswidgets.LogicalGridLayoutConfigBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'Layout Config',
  expandable: true,
  fields: [
    {
      id: 'HGapField',
      objectType: NumberField,
      label: 'H Gap'
    },
    {
      id: 'VGapField',
      objectType: NumberField,
      label: 'V Gap'
    },
    {
      id: 'ColumnWidthField',
      objectType: NumberField,
      label: 'Column Width'
    },
    {
      id: 'RowHeightField',
      objectType: NumberField,
      label: 'Row Height'
    },
    {
      id: 'MinWidthField',
      objectType: NumberField,
      label: 'Min Width'
    }
  ]
});

export type LogicalGridLayoutConfigBoxWidgetMap = {
  'HGapField': NumberField;
  'VGapField': NumberField;
  'ColumnWidthField': NumberField;
  'RowHeightField': NumberField;
  'MinWidthField': NumberField;
};
