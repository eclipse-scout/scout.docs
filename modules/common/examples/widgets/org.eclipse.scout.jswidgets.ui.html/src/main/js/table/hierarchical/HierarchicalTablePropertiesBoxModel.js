export default () => ({
  id: 'jswidgets.HierarchicalTablePropertiesBox',
  type: 'extension',
  extensions: [
    {
      operation: 'insert',
      target: {
        id: 'jswidgets.TablePropertiesBox',
        property: 'fields',
        index: 100
      },
      extension: [
        {
          id: 'HierarchicalStyleField',
          objectType: 'SmartField',
          label: 'Hierarchical Style',
          lookupCall: 'jswidgets.HierarchicalStyleLookupCall'
        },
        {
          id: 'ExtendedHierarchyPaddingField',
          objectType: 'CheckBoxField',
          label: 'Toggle extended hierarchy padding',
          labelVisible: false
        }
      ]
    }
  ]
});
