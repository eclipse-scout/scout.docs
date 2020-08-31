export default () => ({
  id: 'jswidgets.ChartForm',
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
            id: 'ChartFormField',
            objectType: 'jswidgets.ChartField',
            labelVisible: false,
            statusVisible: false,
            scrollable: false,
            gridDataHints: {
              h: 5,
              useUiHeight: false
            }
          }
        ],
        menus: [
          {
            id: 'RandomDataMenu',
            objectType: 'scout.Menu',
            text: 'Random Data'
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
                    id: 'ChartTypeField',
                    objectType: 'SmartField',
                    label: 'Chart type',
                    lookupCall: 'jswidgets.ChartTypeLookupCall',
                    displayStyle: 'dropdown'
                  },
                  {
                    id: 'DataArrayLengthField',
                    objectType: 'NumberField',
                    label: 'Data array length',
                    maxValue: 10,
                    minValue: 2
                  },
                  {
                    id: 'DataSeriesCountField',
                    objectType: 'NumberField',
                    label: 'Data series count',
                    maxValue: 5,
                    minValue: 1
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
});
