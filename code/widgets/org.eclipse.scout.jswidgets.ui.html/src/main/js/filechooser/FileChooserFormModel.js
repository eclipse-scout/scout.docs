export default function() {
  return {
  id: 'jswidgets.FileChooserForm',
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
            label: 'Open file chooser',
            processButton: false,
            gridDataHints: {
              horizontalAlignment: 0
            }
          }
        ]
      },
      {
        id: 'ConfigurationBox',
        objectType: 'GroupBox',
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
                    id: 'AcceptTypesField',
                    objectType: 'StringField',
                    label: 'Accept Types',
                    tooltipText: '${textKey:FileChooserAcceptTypesTooltip}'
                  },
                  {
                    id: 'DisplayParentField',
                    objectType: 'SmartField',
                    lookupCall: 'jswidgets.DisplayParentLookupCall',
                    label: 'Display Parent'
                  },
                  {
                    id: 'MaximumUploadSizeField',
                    objectType: 'NumberField',
                    label: 'Max. Upload Size',
                    tooltipText: '${textKey:FileChooserMaximumUploadSizeTooltip}'
                  },
                  {
                    id: 'MultiSelectField',
                    objectType: 'CheckBoxField',
                    label: 'Multi Select',
                    labelVisible: false
                  }
                ]
              },
              {
                id: 'ChosenFilesBox',
                objectType: 'GroupBox',
                expandable: true,
                label: '${textKey:FileChooserChosenFiles}',
                fields: [
                  {
                    id: 'ChosenFilesField',
                    objectType: 'HtmlField',
                    labelVisible: false
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
};
}
