export default () => ({
  id: 'jswidgets.TableForm',
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
            id: 'TableField',
            objectType: 'TableField',
            labelVisible: false,
            gridDataHints: {
              h: 10
            },
            table: {
              id: 'Table',
              objectType: 'Table',
              columns: [
                {
                  id: 'StringColumn',
                  objectType: 'Column',
                  text: 'String Column',
                  width: 120
                },
                {
                  id: 'DateColumn',
                  objectType: 'DateColumn',
                  text: 'Date Column',
                  width: 120
                },
                {
                  id: 'NumberColumn',
                  objectType: 'NumberColumn',
                  text: 'Number Column',
                  width: 120
                },
                {
                  id: 'SmartColumn',
                  objectType: 'SmartColumn',
                  text: 'Smart Column',
                  lookupCall: 'jswidgets.LocaleLookupCall',
                  width: 120
                },
                {
                  id: 'BooleanColumn',
                  objectType: 'BooleanColumn',
                  text: 'Boolean Column',
                  width: 130
                },
                {
                  id: 'IconColumn',
                  objectType: 'IconColumn',
                  text: 'Icon Column',
                  width: 110
                },
                {
                  id: 'HtmlColumn',
                  objectType: 'Column',
                  text: 'Html Column',
                  htmlEnabled: true,
                  width: 110
                }
              ],
              tableControls: [
                {
                  id: 'AggregateTableControl',
                  objectType: 'AggregateTableControl'
                }
              ],
              menus: [
                {
                  id: 'AddRowMenu',
                  objectType: 'Menu',
                  text: '${textKey:AddRow}',
                  menuTypes: [
                    'Table.EmptySpace'
                  ],
                  keyStroke: 'insert'
                },
                {
                  id: 'MoveMenu',
                  objectType: 'Menu',
                  text: '${textKey:Move}',
                  menuTypes: [
                    'Table.SingleSelection'
                  ],
                  childActions: [
                    {
                      id: 'MoveToTopMenu',
                      objectType: 'Menu',
                      text: '${textKey:MoveToTop}',
                      menuTypes: [
                        'Table.SingleSelection'
                      ]
                    },
                    {
                      id: 'MoveUpMenu',
                      objectType: 'Menu',
                      text: '${textKey:MoveUp}',
                      menuTypes: [
                        'Table.SingleSelection'
                      ]
                    },
                    {
                      id: 'MoveDownMenu',
                      objectType: 'Menu',
                      text: '${textKey:MoveDown}',
                      menuTypes: [
                        'Table.SingleSelection'
                      ]
                    },
                    {
                      id: 'MoveToBottomMenu',
                      objectType: 'Menu',
                      text: '${textKey:MoveToBottom}',
                      menuTypes: [
                        'Table.SingleSelection'
                      ]
                    }
                  ]
                },
                {
                  id: 'DeleteRowMenu',
                  objectType: 'Menu',
                  text: '${textKey:DeleteRow}',
                  menuTypes: [
                    'Table.SingleSelection',
                    'Table.MultiSelection'
                  ],
                  keyStroke: 'delete'
                }
              ]
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
                objectType: 'jswidgets.TablePropertiesBox',
                label: 'Properties',
                labelVisible: false,
                borderVisible: false
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
            id: 'ColumnProperties',
            objectType: 'TabItem',
            label: 'Column Properties',
            fields: [
              {
                id: 'Column.TargetField',
                objectType: 'SmartField',
                label: 'Target'
              },
              {
                id: 'Column.PropertiesBox',
                objectType: 'jswidgets.ColumnPropertiesBox',
                labelVisible: true
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
