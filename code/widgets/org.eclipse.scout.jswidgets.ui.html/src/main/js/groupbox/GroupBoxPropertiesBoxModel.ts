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
import {CheckBoxField, GroupBox, GroupBoxBorderDecoration, GroupBoxMenuBarPosition, GroupBoxModel, MenuBarEllipsisPosition, NumberField, SmartField, StatusSeverity, StringField} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  id: 'jswidgets.GroupBoxPropertiesBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'Properties',
  expandable: true,
  fields: [
    {
      id: 'ScrollableField',
      objectType: CheckBoxField,
      label: 'Scrollable',
      labelVisible: false,
      tooltipText: '${textKey:GroupBoxScrollableTooltip}',
      gridDataHints: {
        fillHorizontal: false
      }
    },
    {
      id: 'BorderVisibleField',
      objectType: CheckBoxField,
      label: 'Border Visible',
      labelVisible: false
    },
    {
      id: 'BorderDecorationField',
      objectType: SmartField,
      label: 'Border Decoration',
      lookupCall: 'jswidgets.BorderDecorationLookupCall',
      visible: false // Temporarily made invisible because it is confusing what the property really does
    },
    {
      id: 'ExpandableField',
      objectType: CheckBoxField,
      label: 'Expandable',
      labelVisible: false
    },
    {
      id: 'ExpandedField',
      objectType: CheckBoxField,
      label: 'Expanded',
      labelVisible: false
    },
    {
      id: 'ResponsiveField',
      objectType: CheckBoxField,
      label: 'Responsive',
      labelVisible: false
    },
    {
      id: 'SubLabelField',
      objectType: StringField,
      label: 'Sub Label'
    },
    {
      id: 'GridColumnCountField',
      objectType: NumberField,
      label: 'Grid Column Count'
    },
    {
      id: 'LogicalGridField',
      objectType: SmartField,
      label: 'Logical Grid',
      lookupCall: 'jswidgets.LogicalGridLookupCall',
      tooltipText: '${textKey:LogicalGridWithRefTooltip}'
    },
    {
      id: 'NotificationField',
      objectType: SmartField,
      lookupCall: 'jswidgets.StatusSeverityLookupCall',
      label: 'Notification'
    },
    {
      id: 'NotificationIconField',
      objectType: SmartField,
      lookupCall: 'jswidgets.IconIdLookupCall',
      label: 'Notification Icon'
    },
    {
      id: 'MenuBarVisibleField',
      objectType: CheckBoxField,
      label: 'Menu Bar Visible',
      labelVisible: false
    },
    {
      id: 'MenuBarPositionField',
      objectType: SmartField,
      label: 'Menu Bar Position',
      lookupCall: 'jswidgets.MenuBarPositionLookupCall'
    },
    {
      id: 'MenuBarEllipsisPositionField',
      objectType: SmartField,
      label: 'Menu Bar Ellipsis Position',
      lookupCall: 'jswidgets.MenuBarEllipsisPositionLookupCall'
    }
  ]
});

export type GroupBoxPropertiesBoxWidgetMap = {
  'ScrollableField': CheckBoxField;
  'BorderVisibleField': CheckBoxField;
  'BorderDecorationField': SmartField<GroupBoxBorderDecoration>;
  'ExpandableField': CheckBoxField;
  'ExpandedField': CheckBoxField;
  'ResponsiveField': CheckBoxField;
  'SubLabelField': StringField;
  'GridColumnCountField': NumberField;
  'LogicalGridField': SmartField<string>;
  'NotificationField': SmartField<StatusSeverity>;
  'NotificationIconField': SmartField<string>;
  'MenuBarVisibleField': CheckBoxField;
  'MenuBarPositionField': SmartField<GroupBoxMenuBarPosition>;
  'MenuBarEllipsisPositionField': SmartField<MenuBarEllipsisPosition>;
};
