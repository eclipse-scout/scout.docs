export default function() {
  return {
    id: 'jswidgets.LabelForm',
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
                value: '${textKey:LabelValue}'
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
                      id: 'HtmlEnabledField',
                      objectType: 'CheckBoxField',
                      label: 'Html Enabled',
                      labelVisible: false
                    },
                    {
                      id: 'ScrollableField',
                      objectType: 'CheckBoxField',
                      label: 'Scrollable',
                      labelVisible: false
                    },
                    {
                      id: 'ValueField',
                      objectType: 'StringField',
                      label: 'Value',
                      multilineText: true,
                      gridDataHints: {
                        h: 3,
                        weightY: 0
                      }
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
