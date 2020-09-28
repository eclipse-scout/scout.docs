export default () => ({
  id: 'jswidgets.BrowserFieldForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: 'GroupBox',
    fields: [
      {
        id: 'DetailBox',
        objectType: 'GroupBox',
        gridColumnCount: 2,
        fields: [
          {
            id: 'BrowserField',
            objectType: 'BrowserField',
            labelVisible: false,
            location: 'http://www.bing.com/search?q=Eclipse%20Scout',
            sandboxEnabled: false,
            gridDataHints: {
              h: 6,
              w: 2
            }
          }
        ]
      },
      {
        id: 'ConfigurationBox',
        objectType: 'jswidgets.ConfigurationBox',
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
                    id: 'ScrollBarEnabledField',
                    objectType: 'CheckBoxField',
                    label: 'Scroll Bar Enabled',
                    labelVisible: false
                  },
                  {
                    id: 'SandboxEnabledField',
                    objectType: 'CheckBoxField',
                    label: 'Sandbox Enabled',
                    labelVisible: false
                  },
                  {
                    id: 'SandboxPermissionsField',
                    objectType: 'StringField',
                    label: 'Sandbox Permissions'
                  },
                  {
                    id: 'LocationField',
                    objectType: 'StringField',
                    label: 'Location'
                  },
                  {
                    id: 'TrackLocationField',
                    objectType: 'CheckBoxField',
                    label: 'Track Location',
                    labelVisible: false,
                    tooltipText: '${textKey:TrackLocationTooltipText}',
                    gridDataHints: {
                      fillHorizontal: false
                    }
                  },
                  {
                    id: 'ShowInExternalWindowField',
                    objectType: 'CheckBoxField',
                    label: 'Show External Window',
                    labelVisible: false
                  },
                  {
                    id: 'AutoCloseExternalWindowField',
                    objectType: 'CheckBoxField',
                    label: 'Auto Close External Window',
                    labelVisible: false
                  },
                  {
                    id: 'ExternalWindowButtonTextField',
                    objectType: 'StringField',
                    label: 'External Window Button Text'
                  },
                  {
                    id: 'ExternalWindowFieldTextField',
                    objectType: 'StringField',
                    label: 'External Window Field Text'
                  }
                ]
              },
              {
                id: 'FormFieldPropertiesBox',
                objectType: 'jswidgets.FormFieldPropertiesBox',
                expanded: false
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
                id: 'FormFieldActionsBox',
                objectType: 'jswidgets.FormFieldActionsBox'
              },
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
