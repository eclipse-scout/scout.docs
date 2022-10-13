export default () => ({
  id: 'jswidgets.BooleanColumnPropertiesBox',
  type: 'model',
  objectType: 'GroupBox',
  gridColumnCount: 2,
  label: 'Boolean Column Properties',
  expandable: true,
  fields: [
    {
      id: 'TriStateEnabledField',
      objectType: 'CheckBoxField',
      label: 'Tri State Enabled',
      labelVisible: false
    }
  ]
});
