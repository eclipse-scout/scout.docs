export default function() {
  return {
    id: 'jswidgets.ButtonForm',
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
              id: 'Button',
              objectType: 'Button',
              label: 'Button',
              processButton: false,
              gridDataHints: {
                horizontalAlignment: 0
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
                      id: 'DefaultButtonField',
                      objectType: 'CheckBoxField',
                      label: 'Default Button',
                      labelVisible: false
                    },
                    {
                      id: 'ProcessButtonField',
                      objectType: 'CheckBoxField',
                      label: 'Process Button',
                      labelVisible: false,
                      tooltipText: '${textKey:ProcessButtonTooltip}',
                      gridDataHints: {
                        fillHorizontal: false
                      }
                    },
                    {
                      id: 'SelectedField',
                      objectType: 'CheckBoxField',
                      label: 'Selected',
                      labelVisible: false
                    },
                    {
                      id: 'HtmlEnabledField',
                      objectType: 'CheckBoxField',
                      label: 'Html Enabled',
                      labelVisible: false
                    },
                    {
                      id: 'DisplayStyleField',
                      objectType: 'SmartField',
                      lookupCall: 'jswidgets.ButtonDisplayStyleLookupCall',
                      label: 'Display Style'
                    },
                    {
                      id: 'IconIdField',
                      objectType: 'SmartField',
                      lookupCall: 'jswidgets.IconIdLookupCall',
                      label: 'Icon Id'
                    },
                    {
                      id: 'KeyStrokeField',
                      objectType: 'StringField',
                      label: 'Key Stroke'
                    }
                  ]
                },
                {
                  id: 'FormFieldPropertiesBox',
                  objectType: 'jswidgets.FormFieldPropertiesBox'
                },
                {
                  id: 'GridDataBox',
                  objectType: 'jswidgets.GridDataBox',
                  label: 'Grid Data Hints'
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
