export default function() {
  return {
  id: 'jswidgets.DesktopForm',
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
            objectType: 'WidgetField',
            labelVisible: false,
            statusVisible: false,
            gridDataHints: {
              useUiHeight: true
            },
            fieldWidget: {
              id: 'Label',
              objectType: 'Label',
              value: '${textKey:DesktopDescription}'
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
                id: 'PropertiesBox',
                objectType: 'GroupBox',
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
                  {
                    id: 'NavigationVisibleField',
                    objectType: 'CheckBoxField',
                    label: 'Navigation Visible',
                    labelVisible: false
                  },
                  {
                    id: 'HeaderVisibleField',
                    objectType: 'CheckBoxField',
                    label: 'Header Visible',
                    labelVisible: false
                  },
                  {
                    id: 'DenseField',
                    objectType: 'CheckBoxField',
                    label: 'Dense',
                    labelVisible: false
                  }
                ]
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
};
}
