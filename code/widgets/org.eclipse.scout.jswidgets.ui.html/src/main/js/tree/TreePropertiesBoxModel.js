export default () => ({
  id: 'jswidgets.TreePropertiesBox',
  type: 'model',
  objectType: 'GroupBox',
  label: 'Tree Properties',
  fields: [
    {
      id: 'AutoCheckChildrenField',
      objectType: 'CheckBoxField',
      label: 'Auto Check Children',
      labelVisible: false,
      gridDataHints: {
        fillHorizontal: false
      }
    },
    {
      id: 'CheckableField',
      objectType: 'CheckBoxField',
      label: 'Checkable',
      labelVisible: false
    },
    {
      id: 'MultiCheckField',
      objectType: 'CheckBoxField',
      label: 'Multi Check',
      labelVisible: false
    },
    {
      id: 'CheckableStyleField',
      objectType: 'SmartField',
      label: 'Checkable Style',
      lookupCall: 'jswidgets.CheckableTreeStyleLookupCall'
    }
  ]
});
