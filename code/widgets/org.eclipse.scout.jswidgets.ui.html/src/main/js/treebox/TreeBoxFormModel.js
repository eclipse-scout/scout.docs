import {FormField} from '@eclipse-scout/core';

export default function() {
  return {
  id: 'jswidgets.TreeBoxForm',
  type: 'model',
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
            id: 'TreeBox',
            objectType: 'TreeBox',
            lookupCall: 'jswidgets.WorldLookupCall',
            label: 'Tree Box',
            gridDataHints: {
              h: 6,
              weightY: 0
            }
          }
        ]
      },
      {
        id: 'ConfigurationBox',
        objectType: 'TabBox',
        labelVisible: false,
        cssClass: 'jswidgets-configuration',
        gridDataHints: {
          h: 6,
          weightY: -1
        },
        gridColumnCount: 1,
        selectedTab: 'PropertiesTab',
        tabItems: [
          {
            id: 'PropertiesTab',
            objectType: 'TabItem',
            label: 'Properties',
            fields: [
              {
                id: 'PropertiesBox',
                objectType: 'jswidgets.TreePropertiesBox',
                labelVisible: false,
                borderVisible: false
              },
              {
                id: 'ValueFieldPropertiesBox',
                objectType: 'jswidgets.ValueFieldPropertiesBox',
                expanded: false
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
                id: 'TreeBoxActionsBox',
                objectType: 'GroupBox',
                fields: [
                  {
                    id: 'InsertTreeNodesField',
                    objectType: 'SequenceBox',
                    label: 'Insert Nodes',
                    fields: [
                      {
                        id: 'NodeCountField',
                        objectType: 'NumberField',
                        label: 'Node Count',
                        labelVisible: false,
                        statusVisible: false,
                        labelPosition: FormField.LabelPosition.ON_FIELD
                      },
                      {
                        id: 'DepthField',
                        objectType: 'NumberField',
                        label: 'Depth',
                        labelVisible: false,
                        statusVisible: false,
                        labelPosition: FormField.LabelPosition.ON_FIELD
                      },
                      {
                        id: 'DefaultExpandedField',
                        objectType: 'CheckBoxField',
                        label: 'Default expanded',
                        labelVisible: false,
                        statusVisible: false
                      },
                      {
                        id: 'DefaultEnabledField',
                        objectType: 'CheckBoxField',
                        label: 'Default enabled',
                        labelVisible: false,
                        statusVisible: false
                      },
                      {
                        id: 'GenerateTreeDataButton',
                        objectType: 'Button',
                        label: 'Generate Tree Data'
                      }
                    ]
                  }
                ]
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
};
}
