export default () => ({
  id: 'jswidgets.GroupBoxPropertiesBox',
  objectType: 'GroupBox',
  gridColumnCount: 2,
  label: 'Properties',
  expandable: true,
  fields: [
    {
      id: 'ScrollableField',
      objectType: 'CheckBoxField',
      label: 'Scrollable',
      labelVisible: false,
      tooltipText: '${textKey:GroupBoxScrollableTooltip}',
      gridDataHints: {
        fillHorizontal: false
      }
    },
    {
      id: 'BorderVisibleField',
      objectType: 'CheckBoxField',
      label: 'Border Visible',
      labelVisible: false
    },
    {
      id: 'BorderDecorationField',
      objectType: 'SmartField',
      label: 'Border Decoration',
      lookupCall: 'jswidgets.BorderDecorationLookupCall',
      visible: false // Temporarily made invisible because it is confusing what the property really does
    },
    {
      id: 'ExpandableField',
      objectType: 'CheckBoxField',
      label: 'Expandable',
      labelVisible: false
    },
    {
      id: 'ExpandedField',
      objectType: 'CheckBoxField',
      label: 'Expanded',
      labelVisible: false
    },
    {
      id: 'ResponsiveField',
      objectType: 'CheckBoxField',
      label: 'Responsive',
      labelVisible: false
    },
    {
      id: 'SubLabelField',
      objectType: 'StringField',
      label: 'Sub Label'
    },
    {
      id: 'GridColumnCountField',
      objectType: 'NumberField',
      label: 'Grid Column Count'
    },
    {
      id: 'LogicalGridField',
      objectType: 'SmartField',
      label: 'Logical Grid',
      lookupCall: 'jswidgets.LogicalGridLookupCall',
      tooltipText: '${textKey:LogicalGridWithRefTooltip}'
    },
    {
      id: 'NotificationField',
      objectType: 'SmartField',
      lookupCall: 'jswidgets.StatusSeverityLookupCall',
      label: 'Notification'
    },
    {
      id: 'NotificationIconField',
      objectType: 'SmartField',
      lookupCall: 'jswidgets.IconIdLookupCall',
      label: 'Notification Icon'
    },
    {
      id: 'MenuBarVisibleField',
      objectType: 'CheckBoxField',
      label: 'Menu Bar Visible',
      labelVisible: false
    },
    {
      id: 'MenuBarPositionField',
      objectType: 'SmartField',
      label: 'Menu Bar Position',
      lookupCall: 'jswidgets.MenuBarPositionLookupCall'
    },
    {
      id: 'MenuBarEllipsisPositionField',
      objectType: 'SmartField',
      label: 'Menu Bar Ellipsis Position',
      lookupCall: 'jswidgets.MenuBarEllipsisPositionLookupCall'
    }
  ]
});
