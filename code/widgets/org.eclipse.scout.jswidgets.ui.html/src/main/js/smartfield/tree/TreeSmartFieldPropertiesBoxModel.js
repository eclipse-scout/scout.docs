export default function() {
  return {
    id: 'jswidgets.TreeSmartFieldPropertiesBox',
    type: 'extension',
    extensions: [
      {
        operation: 'insert',
        target: {
          id: 'jswidgets.SmartFieldPropertiesBox',
          property: 'fields',
          before: 'ActiveFilterEnabledField'
        },
        extension: [
          {
            id: 'BrowseAutoExpandAllField',
            objectType: 'CheckBoxField',
            label: 'Browse Auto Expand All'
          },
          {
            id: 'BrowseLoadIncrementalField',
            objectType: 'CheckBoxField',
            label: 'Browse Load Incremental'
          }
        ]
      }
    ]
  };
}
