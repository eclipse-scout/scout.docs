/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
