export default function() {
  return {
  id: 'jswidgets.StringFieldForm',
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
            id: 'StringField',
            objectType: 'StringField',
            label: '${textKey:StringField}'
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
                expandable: true,
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
                   {
                    id: 'HasActionField',
                    objectType: 'CheckBoxField',
                    label: 'Has Action',
                    labelVisible: false
                  },
                  {
                    id: 'InputMaskedField',
                    objectType: 'CheckBoxField',
                    label: 'Input Masked',
                    labelVisible: false
                  },
                  {
                    id: 'MultilineTextField',
                    objectType: 'CheckBoxField',
                    label: 'Multiline Text',
                    labelVisible: false
                  },
                  {
                    id: 'SpellCheckEnabledField',
                    objectType: 'CheckBoxField',
                    label: 'Spell Check Enabled',
                    labelVisible: false,
                    tooltipText: '${textKey:SpellCheckEnabledTooltip}',
                    gridDataHints: {
                      fillHorizontal: false
                    }
                  },
                  {
                    id: 'TrimTextField',
                    objectType: 'CheckBoxField',
                    label: 'Trim Text',
                    labelVisible: false
                  },
                  {
                    id: 'UpdateDisplayTextOnModifyField',
                    objectType: 'CheckBoxField',
                    label: 'Update Display Text On Modify',
                    labelVisible: false
                  },
                  {
                    id: 'FormatField',
                    objectType: 'SmartField',
                    lookupCall: 'jswidgets.StringFormatLookupCall',
                    label: 'Format'
                  },
                  {
                    id: 'MaxLengthField',
                    objectType: 'NumberField',
                    label: 'Max Length'
                  },
                  {
                    id: 'SelectionTrackingEnabledField',
                    objectType: 'CheckBoxField',
                    label: 'Selection Tracking Enabled',
                    labelVisible: false
                  },
                  {
                    id: 'SelectionStartField',
                    objectType: 'NumberField',
                    label: 'Selection Start'
                  },
                  {
                    id: 'SelectionEndField',
                    objectType: 'NumberField',
                    label: 'Selection End'
                  }
                ]
              },
              {
                id: 'CustomizationBox',
                objectType: 'GroupBox',
                expandable: true,
                gridColumnCount: 2,
                label: 'Customization Examples',
                fields: [
                  {
                    id: 'BlockFormatField',
                    objectType: 'CheckBoxField',
                    label: 'Make 4 character blocks',
                    labelVisible: false,
                    tooltipText: '${textKey:BlockFormatTooltip}',
                    gridUseUiWidth: true
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
