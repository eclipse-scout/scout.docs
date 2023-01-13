/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, GroupBox, GroupBoxModel, NumberField} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  id: 'jswidgets.GridDataBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'Grid Data',
  expandable: true,
  fields: [
    {
      id: 'WField',
      objectType: NumberField,
      label: 'W'
    },
    {
      id: 'HField',
      objectType: NumberField,
      label: 'H'
    },
    {
      id: 'XField',
      objectType: NumberField,
      label: 'X',
      enabled: false
    },
    {
      id: 'YField',
      objectType: NumberField,
      label: 'Y',
      enabled: false
    },
    {
      id: 'WeightXField',
      objectType: NumberField,
      label: 'Weight X',
      decimalFormat: '###0.0'
    },
    {
      id: 'WeightYField',
      objectType: NumberField,
      label: 'Weight Y',
      decimalFormat: '###0.0'
    },
    {
      id: 'HorizontalAlignmentField',
      objectType: NumberField,
      label: 'Horizontal Alignment'
    },
    {
      id: 'VerticalAlignmentField',
      objectType: NumberField,
      label: 'Vertical Alignment'
    },
    {
      id: 'WidthInPixelField',
      objectType: NumberField,
      label: 'Width In Pixel'
    },
    {
      id: 'HeightInPixelField',
      objectType: NumberField,
      label: 'Height In Pixel'
    },
    {
      id: 'FillHorizontalField',
      objectType: CheckBoxField,
      label: 'Fill Horizontal',
      labelVisible: false
    },
    {
      id: 'FillVerticalField',
      objectType: CheckBoxField,
      label: 'Fill Vertical',
      labelVisible: false
    },
    {
      id: 'UseUiWidthField',
      objectType: CheckBoxField,
      label: 'Use UI Width',
      labelVisible: false
    },
    {
      id: 'UseUiHeightField',
      objectType: CheckBoxField,
      label: 'Use Ui Height',
      labelVisible: false
    }
  ]
});

export type GridDataBoxWidgetMap = {
  'WField': NumberField;
  'HField': NumberField;
  'XField': NumberField;
  'YField': NumberField;
  'WeightXField': NumberField;
  'WeightYField': NumberField;
  'HorizontalAlignmentField': NumberField;
  'VerticalAlignmentField': NumberField;
  'WidthInPixelField': NumberField;
  'HeightInPixelField': NumberField;
  'FillHorizontalField': CheckBoxField;
  'FillVerticalField': CheckBoxField;
  'UseUiWidthField': CheckBoxField;
  'UseUiHeightField': CheckBoxField;
};
