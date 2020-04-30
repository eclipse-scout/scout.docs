export default () => ({
  id: 'jswidgets.ValueFieldPropertiesBox',
  objectType: 'GroupBox',
  gridColumnCount: 2,
  label: 'Value Field Properties',
  expandable: true,
  fields: [
    {
      id: 'ValueField',
      objectType: 'StringField',
      label: 'Value',
      enabled: false
    },
    {
      id: 'DisplayTextField',
      objectType: 'StringField',
      label: 'DisplayText',
      enabled: false
    },
    {
      id: 'ClearableField',
      objectType: 'SmartField',
      lookupCall: 'jswidgets.ClearableStyleLookupCall',
      label: 'Clearable',
      displayStyle: 'dropdown'
    }
  ]
});
