import {SmartField} from '@eclipse-scout/core';

export default () => ({
  id: 'jswidgets.SmartFieldPropertiesBox',
  objectType: 'GroupBox',
  label: 'Smart Field Properties',
  expandable: true,
  fields: [
    {
      id: 'LookupCallField',
      objectType: 'SmartField',
      label: 'LookupCall',
      lookupCall: 'jswidgets.LookupCallLookupCall'
    },
    {
      id: 'DisplayStyleField',
      objectType: 'SmartField',
      lookupCall: 'jswidgets.SmartFieldDisplayStyleLookupCall',
      label: 'Display Style'
    },
    {
      id: 'BrowseMaxRowCountField',
      objectType: 'NumberField',
      label: 'Browse Max Row Count'
    },
    {
      id: 'SearchRequiredField',
      objectType: 'CheckBoxField',
      label: 'Search Required'
    },
    {
      id: 'ActiveFilterEnabledField',
      objectType: 'CheckBoxField',
      label: 'Active Filter Enabled'
    },
    {
      id: 'ActiveFilterField',
      objectType: 'SmartField',
      displayStyle: SmartField.DisplayStyle.DROPDOWN,
      lookupCall: 'jswidgets.SmartFieldActiveFilterLookupCall',
      label: 'Active Filter'
    }
  ]
});
