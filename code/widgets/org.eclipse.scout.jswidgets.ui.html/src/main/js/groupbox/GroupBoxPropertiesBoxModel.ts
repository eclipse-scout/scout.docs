/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, GroupBox, GroupBoxModel, NumberField, SmartField, StringField} from '@eclipse-scout/core';
import {BorderDecorationLookupCall, IconIdLookupCall, LogicalGridLookupCall, MenuBarEllipsisPositionLookupCall, MenuBarPositionLookupCall, StatusSeverityLookupCall} from '../index';

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
      lookupCall: BorderDecorationLookupCall,
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
      lookupCall: LogicalGridLookupCall,
      tooltipText: '${textKey:LogicalGridWithRefTooltip}'
    },
    {
      id: 'NotificationField',
      objectType: SmartField,
      lookupCall: StatusSeverityLookupCall,
      label: 'Notification'
    },
    {
      id: 'NotificationIconField',
      objectType: SmartField,
      lookupCall: IconIdLookupCall,
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
      lookupCall: MenuBarPositionLookupCall
    },
    {
      id: 'MenuBarEllipsisPositionField',
      objectType: SmartField,
      label: 'Menu Bar Ellipsis Position',
      lookupCall: MenuBarEllipsisPositionLookupCall
    }
  ]
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type GroupBoxPropertiesBoxWidgetMap = {
  'ScrollableField': CheckBoxField;
  'BorderVisibleField': CheckBoxField;
  'BorderDecorationField': SmartField<any>;
  'ExpandableField': CheckBoxField;
  'ExpandedField': CheckBoxField;
  'ResponsiveField': CheckBoxField;
  'SubLabelField': StringField;
  'GridColumnCountField': NumberField;
  'LogicalGridField': SmartField<any>;
  'NotificationField': SmartField<any>;
  'NotificationIconField': SmartField<any>;
  'MenuBarVisibleField': CheckBoxField;
  'MenuBarPositionField': SmartField<any>;
  'MenuBarEllipsisPositionField': SmartField<any>;
};
