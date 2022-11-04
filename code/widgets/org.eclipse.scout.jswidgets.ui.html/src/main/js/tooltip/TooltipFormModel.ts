export default () => ({
  id: 'jswidgets.TooltipForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: 'GroupBox',
    fields: [
      {
        id: 'DetailBox',
        objectType: 'GroupBox',
        gridColumnCount: 1,
        gridDataHints: {
          weightY: 1
        },
        fields: [
          {
            id: 'OpenTooltipButton',
            objectType: 'Button',
            label: '${textKey:OpenTooltip}',
            cssClass: 'open-form-button',
            processButton: false,
            gridDataHints: {
              horizontalAlignment: 0,
              verticalAlignment: 0,
              fillVertical: false,
              fillHorizontal: false,
              weightY: 1
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
                    id: 'AutoRemoveField',
                    objectType: 'CheckBoxField',
                    label: 'Auto Remove',
                    labelVisible: false
                  },
                  {
                    id: 'HtmlEnabledField',
                    objectType: 'CheckBoxField',
                    label: 'Html Enabled',
                    labelVisible: false
                  },
                  {
                    id: 'TextField',
                    objectType: 'StringField',
                    label: 'Text',
                    value: '${textKey:TooltipText}'
                  },
                  {
                    id: 'SeverityField',
                    objectType: 'SmartField',
                    lookupCall: 'jswidgets.StatusSeverityLookupCall',
                    label: 'Severity'
                  }
                ]
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
