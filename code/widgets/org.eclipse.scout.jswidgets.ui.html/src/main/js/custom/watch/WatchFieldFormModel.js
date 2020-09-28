export default () => ({
  id: 'jswidgets.WatchFieldForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: 'GroupBox',
    fields: [
      {
        id: 'DetailBox',
        objectType: 'GroupBox',
        gridColumnCount: 1,
        fields: [
          {
            id: 'WatchField',
            objectType: 'jswidgets.WatchField',
            label: 'Watch',
            gridDataHints: {
              h: 5
            }
          }
        ]
      },
      {
        id: 'ConfigurationBox',
        objectType: 'TabBox',
        cssClass: 'jswidgets-configuration',
        selectedTab: 'PropertiesTab',
        tabItems: [
          {
            id: 'PropertiesTab',
            objectType: 'TabItem',
            label: 'Properties',
            fields: [
              {
                id: 'FormFieldPropertiesBox',
                objectType: 'jswidgets.FormFieldPropertiesBox'
              },
              {
                id: 'GridDataBox',
                objectType: 'jswidgets.GridDataBox',
                label: 'Grid Data Hints',
                expanded: false
              }
            ]
          },
          {
            id: 'ActionsTab',
            objectType: 'TabItem',
            label: 'Actions',
            fields: [
              {
                id: 'WidgetActionsBox',
                objectType: 'jswidgets.WidgetActionsBox'
              }
            ]
          },
          {
            id: 'EventsTab',
            objectType: 'jswidgets.EventsTab'
          }
        ]
      }
    ]
  }
});
