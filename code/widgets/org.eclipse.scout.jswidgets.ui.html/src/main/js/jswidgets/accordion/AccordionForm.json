export default function() {
  return {
  id: 'jswidgets.AccordionForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: 'GroupBox',
    fields: [
      {
        id: 'DetailBox',
        objectType: 'GroupBox',
        gridColumnCount: 2,
        menus: [
          {
            id: 'InsertMenu',
            objectType: 'Menu',
            text: 'Insert group',
            keyStroke: 'insert'
          },
          {
            id: 'DeleteFirstMenu',
            objectType: 'Menu',
            text: 'Delete first group',
            keyStroke: 'delete'
          },
          {
            id: 'CollapseExpandFirstMenu',
            objectType: 'Menu',
            text: 'Collapse/Expand first group'
          },
          {
            id: 'CollapseAllMenu',
            objectType: 'Menu',
            text: 'Collapse all groups'
          },
          {
            id: 'SortMenu',
            objectType: 'Menu',
            text: 'Sort',
            childActions: [
              {
                id: 'SortAscMenu',
                objectType: 'Menu',
                text: 'Ascending'
              },
              {
                id: 'SortDescMenu',
                objectType: 'Menu',
                text: 'Descending'
              }
            ]
          }
        ],
        fields: [
          {
            id: 'AccordionField',
            objectType: 'AccordionField',
            labelVisible: false,
            gridDataHints: {
              h: 4,
              w: 2
            },
            accordion: {
              id: 'Accordion',
              objectType: 'Accordion',
              scrollable: true,
              groups: [
                {
                  objectType: 'Group',
                  title: 'Group with Text',
                  body: {
                    objectType: 'LabelField',
                    labelVisible: false,
                    wrapText: true,
                    value: '${textKey:AccordionTextGroup}'
                  }
                },
                {
                  objectType: 'Group',
                  title: 'Group with an Image',
                  body: {
                    objectType: 'ImageField',
                    labelVisible: false,
                    statusVisible: false,
                    imageUrl: 'img/eclipse_scout_logo.png'
                  }
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
                objectType: 'GroupBox',
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
                  {
                    id: 'ExclusiveExpandField',
                    objectType: 'CheckBoxField',
                    label: 'Exclusive Expand',
                    labelVisible: false,
                    tooltipText: '${textKey:ExclusiveExpandTooltip}',
                    gridDataHints: {
                      fillHorizontal: false
                    }
                  },
                  {
                    id: 'ScrollableField',
                    objectType: 'CheckBoxField',
                    label: 'Scrollable',
                    labelVisible: false
                  },
                  {
                    id: 'CollapseStyleField',
                    objectType: 'SmartField',
                    label: 'Collapse Style',
                    lookupCall: 'jswidgets.CollapseStyleLookupCall'
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
