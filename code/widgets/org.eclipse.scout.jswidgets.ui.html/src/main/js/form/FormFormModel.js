export default function() {
  return {
  id: 'jswidgets.FormForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: 'GroupBox',
    'menus' : [      {
        id: 'CloseMenu',
         objectType: 'CloseMenu',
         tooltipText: '${textKey:CloseMenuTooltip}',
         'visible' : false
      }
    ],
    fields: [
      {
        id: 'DetailBox',
        objectType: 'GroupBox',
        gridColumnCount: 1,
        fields: [
          {
            id: 'OpenFormButton',
            objectType: 'Button',
            label: '${textKey:OpenForm}',
            cssClass: 'open-form-button',
            processButton: false,
            gridDataHints: {
              horizontalAlignment: 0
            }
          },
          {
            id: 'OpenLifecycleFormButton',
            objectType: 'Button',
            label: '${textKey:OpenLifecycleForm}',
            processButton: false,
            gridDataHints: {
              horizontalAlignment: 0
            }
          },
          {
            id: 'LifecycleDataField',
            objectType: 'LabelField',
            label: '${textKey:LifecycleData}',
            visible: false,
            gridDataHints: {
              h: 3,
              weightY: 0
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
                objectType: 'jswidgets.FormPropertiesBox'
              }
            ]
          },
          {
            id: 'CurrentFormPropertiesTab',
            objectType: 'TabItem',
            label: 'Properties current form',
            fields: [
              {
                id: 'CurrentFormPropertiesBox',
                objectType: 'jswidgets.FormPropertiesBox'
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
