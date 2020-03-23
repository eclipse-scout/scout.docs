export default function() {
  return {
    id: 'jswidgets.HierarchicalTableForm',
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
                    text: '${textKey:Name}',
                    id: 'Name',
                    width: 200,
                    objectType: 'Column'
                  },
                  {
                    text: '${textKey:Detail}',
                    id: 'Detail',
                    width: 175,
                    objectType: 'Column'
                  },
                  {
                    text: 'Date',
                    id: 'Date',
                    width: 200,
                    objectType: 'Column'
                  },
                  {
                    text: 'Active',
                    id: 'Active',
                    width: 100,
                    objectType: 'BooleanColumn'
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
                    id: 'ContentMenu',
                    objectType: 'Menu',
                    text: 'Content',
                    menuTypes: ['Table.EmptySpace'],
                    childActions: [
                      {
                        id: 'RemoveAll',
                        objectType: 'Menu',
                        text: 'Remove all rows',
                        menuTypes: ['Table.EmptySpace']
                      },
                      {
                        id: 'InsertFew',
                        objectType: 'Menu',
                        text: 'Insert few',
                        menuTypes: ['Table.EmptySpace']
                      },
                      {
                        id: 'InsertMany',
                        objectType: 'Menu',
                        text: 'Insert many',
                        menuTypes: ['Table.EmptySpace']
                      }
                    ]
                  },
                  {
                    id: 'AddRowMenu',
                    objectType: 'Menu',
                    text: '${textKey:AddRow}',
                    menuTypes: ['Table.EmptySpace'],
                    keyStroke: 'insert'
                  },
                  {
                    id: 'DeleteRowMenu',
                    objectType: 'Menu',
                    text: '${textKey:DeleteRow}',
                    menuTypes: ['Table.SingleSelection', 'Table.MultiSelection'],
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
                  objectType: 'jswidgets.HierarchicalTablePropertiesBox',
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
                  labelVisible: false
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
  };
}
