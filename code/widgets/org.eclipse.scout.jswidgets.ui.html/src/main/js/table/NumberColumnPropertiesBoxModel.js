export default () => ({
  id: 'jswidgets.NumberColumnPropertiesBox',
  type: 'model',
  objectType: 'GroupBox',
  gridColumnCount: 2,
  label: 'NumberColumn Properties',
  expandable: true,
  fields: [
    {
      id: 'MinValueField',
      objectType: 'NumberField',
      label: 'Min Value'
    },
    {
      id: 'MaxValueField',
      objectType: 'NumberField',
      label: 'Max Value'
    },
    {
      id: 'MultiplierField',
      objectType: 'NumberField',
      label: 'Multiplier'
    },
    {
      id: 'FormatField',
      objectType: 'StringField',
      label: 'Format'
    },
    {
      id: 'BackgroundEffectField',
      objectType: 'SmartField',
      lookupCall: 'jswidgets.BackgroundEffectLookupCall',
      label: 'Background Effect',
      displayStyle: 'default'
    }
  ]
});
