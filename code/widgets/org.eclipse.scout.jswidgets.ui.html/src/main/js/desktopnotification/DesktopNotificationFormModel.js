export default () => ({
  id: 'jswidgets.DesktopNotificationForm',
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
            label: 'Show Notification',
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
                    id: 'MessageField',
                    objectType: 'StringField',
                    label: 'Message'
                  },
                  {
                    id: 'StatusSeverityField',
                    objectType: 'SmartField',
                    lookupCall: 'jswidgets.StatusSeverityLookupCall',
                    label: 'Severity'
                  },
                  {
                    id: 'DurationField',
                    objectType: 'NumberField',
                    label: 'Duration',
                    tooltipText: '${textKey:DurationTooltip}'
                  },
                  {
                    id: 'ClosableField',
                    objectType: 'CheckBoxField',
                    label: 'Closable',
                    labelVisible: false
                  },
                  {
                    id: 'NativeOnlyField',
                    objectType: 'CheckBoxField',
                    label: 'Native Only',
                    labelVisible: false,
                    tooltipText: '${textKey:NativeOnlyTooltip}'
                  },
                  {
                    id: 'NativeNotificationVisibilityField',
                    objectType: 'SmartField',
                    lookupCall: 'jswidgets.NativeNotificationVisibilityLookupCall',
                    label: 'Native Notification',
                    tooltipText: '${textKey:NativeNotificationVisibilityTooltip}'
                  },
                  {
                    id: 'DelayField',
                    objectType: 'NumberField',
                    label: 'Delay',
                    tooltipText: '${textKey:DelayTooltip}'
                  }
                ]
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
