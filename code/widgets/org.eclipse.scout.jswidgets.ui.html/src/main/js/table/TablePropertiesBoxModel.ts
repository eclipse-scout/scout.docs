/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, GroupBox, GroupBoxModel, NumberField, SmartField} from '@eclipse-scout/core';
import {CheckableStyleLookupCall, GroupingStyleLookupCall} from '../index';

export default (): GroupBoxModel => ({
  id: 'jswidgets.TablePropertiesBox',
  objectType: GroupBox,
  label: 'Table Properties',
  fields: [
    {
      id: 'AutoResizeColumnsField',
      objectType: CheckBoxField,
      label: 'Auto Resize Columns',
      tooltipText: '${textKey:AutoResizeColumnsTooltip}',
      labelVisible: false,
      gridDataHints: {
        fillHorizontal: false
      }
    },
    {
      id: 'AutoOptimizeColumnWidthsField',
      objectType: CheckBoxField,
      label: 'Auto Optimize Column Widths',
      tooltipText: '${textKey:AutoOptimizeColumnWidthsTooltip}',
      labelVisible: false,
      gridDataHints: {
        fillHorizontal: false
      }
    },
    {
      id: 'CheckableField',
      objectType: CheckBoxField,
      label: 'Checkable',
      labelVisible: false
    },
    {
      id: 'ColumnAddableField',
      objectType: CheckBoxField,
      label: 'Column Addable',
      labelVisible: false
    },
    {
      id: 'CompactField',
      objectType: CheckBoxField,
      label: 'Compact',
      labelVisible: false
    },
    {
      id: 'HeaderEnabledField',
      objectType: CheckBoxField,
      label: 'Header Enabled',
      labelVisible: false
    },
    {
      id: 'HeaderVisibleField',
      objectType: CheckBoxField,
      label: 'Header Visible',
      labelVisible: false
    },
    {
      id: 'HeaderMenusEnabledField',
      objectType: CheckBoxField,
      label: 'Header Menus Enabled',
      labelVisible: false
    },
    {
      id: 'MenuBarVisibleField',
      objectType: CheckBoxField,
      label: 'Menu Bar Visible',
      labelVisible: false
    },
    {
      id: 'MultiCheckField',
      objectType: CheckBoxField,
      label: 'Multi Check',
      labelVisible: false
    },
    {
      id: 'MultiSelectField',
      objectType: CheckBoxField,
      label: 'Multi Select',
      labelVisible: false
    },
    {
      id: 'MultilineTextField',
      objectType: CheckBoxField,
      label: 'Multiline Text',
      labelVisible: false
    },
    {
      id: 'TruncatedCellTooltipEnabledField',
      objectType: CheckBoxField,
      label: 'Truncated Cell Tooltip Enabled',
      labelVisible: false,
      triStateEnabled: true
    },
    {
      id: 'ScrollToSelectionField',
      objectType: CheckBoxField,
      label: 'Scroll To Selection',
      labelVisible: false
    },
    {
      id: 'SortEnabledField',
      objectType: CheckBoxField,
      label: 'Sort Enabled',
      labelVisible: false
    },
    {
      id: 'FooterVisibleField',
      objectType: CheckBoxField,
      label: 'Footer Visible',
      labelVisible: false
    },
    {
      id: 'RowIconVisibleField',
      objectType: CheckBoxField,
      label: 'Row Icon Visible',
      labelVisible: false
    },
    {
      id: 'RowIconColumnWidthField',
      objectType: NumberField,
      label: 'Row Icon Column Width'
    },
    {
      id: 'CheckableStyleField',
      objectType: SmartField,
      label: 'Checkable Style',
      lookupCall: CheckableStyleLookupCall
    },
    {
      id: 'GroupingStyleField',
      objectType: SmartField,
      label: 'Grouping Style',
      lookupCall: GroupingStyleLookupCall
    },
    {
      id: 'TileModeField',
      objectType: CheckBoxField,
      label: 'Tile Mode',
      labelVisible: false
    },
    {
      id: 'TextFilterEnabledField',
      objectType: CheckBoxField,
      label: 'Text Filter Enabled',
      labelVisible: false
    }
  ]
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type TablePropertiesBoxWidgetMap = {
  'AutoResizeColumnsField': CheckBoxField;
  'AutoOptimizeColumnWidthsField': CheckBoxField;
  'CheckableField': CheckBoxField;
  'ColumnAddableField': CheckBoxField;
  'CompactField': CheckBoxField;
  'HeaderEnabledField': CheckBoxField;
  'HeaderVisibleField': CheckBoxField;
  'HeaderMenusEnabledField': CheckBoxField;
  'MenuBarVisibleField': CheckBoxField;
  'MultiCheckField': CheckBoxField;
  'MultiSelectField': CheckBoxField;
  'MultilineTextField': CheckBoxField;
  'TruncatedCellTooltipEnabledField': CheckBoxField;
  'ScrollToSelectionField': CheckBoxField;
  'SortEnabledField': CheckBoxField;
  'FooterVisibleField': CheckBoxField;
  'RowIconVisibleField': CheckBoxField;
  'RowIconColumnWidthField': NumberField;
  'CheckableStyleField': SmartField<any>;
  'GroupingStyleField': SmartField<any>;
  'TileModeField': CheckBoxField;
  'TextFilterEnabledField': CheckBoxField;
};
