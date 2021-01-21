export default () => ({
  id: 'jswidgets.ImageForm',
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
            id: 'WidgetField',
            objectType: 'WidgetField',
            labelVisible: false,
            statusVisible: false,
            gridDataHints: {
              h: 4,
              weightY: 0
            },
            fieldWidget: {
              id: 'Image',
              objectType: 'Image',
              imageUrl: 'img/eclipse_scout_logo.png'
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
                    id: 'AutoFitField',
                    objectType: 'CheckBoxField',
                    label: 'Auto Fit',
                    labelVisible: false
                  },
                  {
                    id: 'ImageUrlField',
                    objectType: 'ProposalField',
                    label: 'Image Url',
                    lookupCall: 'jswidgets.ImageLookupCall'
                  }
                ]
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
});
