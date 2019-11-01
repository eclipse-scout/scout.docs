export default function() {
  return {
    id: 'jswidgets.DateFieldForm',
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
              id: 'DateField',
              objectType: 'DateField',
              label: '${textKey:DateField}'
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
                  gridColumnCount: 2,
                  label: 'Properties',
                  labelVisible: false,
                  borderVisible: false,
                  fields: [
                    {
                      id: 'HasTimeField',
                      objectType: 'CheckBoxField',
                      label: 'Has Time',
                      labelVisible: false
                    },
                    {
                      id: 'HasDateField',
                      objectType: 'CheckBoxField',
                      label: 'Has Date',
                      labelVisible: false
                    },
                    {
                      id: 'AutoDateField',
                      objectType: 'DateField',
                      label: 'Auto Date',
                      tooltipText: '${textKey:AutoDateTooltip}',
                      hasTime: true
                    },
                    {
                      id: 'DateFormatField',
                      objectType: 'StringField',
                      label: 'Date Format'
                    },
                    {
                      id: 'TimeFormatField',
                      objectType: 'StringField',
                      label: 'Time Format'
                    },
                    {
                      id: 'TimePickerResolutionField',
                      objectType: 'NumberField',
                      label: 'Time Picker Resolution'
                    }
                  ]
                },
                {
                  id: 'CustomizationBox',
                  objectType: 'GroupBox',
                  gridColumnCount: 2,
                  label: 'Customization Examples',
                  fields: [
                    {
                      id: 'DontAllowCurrentDateField',
                      objectType: 'CheckBoxField',
                      label: 'Don\'t allow current date',
                      labelVisible: false
                    }
                  ]
                },
                {
                  id: 'ValueFieldPropertiesBox',
                  objectType: 'jswidgets.ValueFieldPropertiesBox'
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
