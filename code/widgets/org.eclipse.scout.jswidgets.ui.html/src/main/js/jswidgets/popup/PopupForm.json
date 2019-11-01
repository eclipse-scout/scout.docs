export default function() {
  return {
  id: 'jswidgets.PopupForm',
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
            id: 'OpenPopupButton',
            objectType: 'Button',
            label: '${textKey:OpenPopup}',
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
                    id: 'UseButtonAsAnchorField',
                    objectType: 'CheckBoxField',
                    label: 'Use button as anchor',
                    labelVisible: false
                  },
                  {
                    id: 'AnchorBoundsField',
                    objectType: 'StringField',
                    label: 'Anchor Bounds',
                    tooltipText: '${textKey:AnchorBoundsTooltip}'
                  },
                  {
                    id: 'CloseOnAnchorMouseDownField',
                    objectType: 'CheckBoxField',
                    label: 'Close on Anchor Mouse Down',
                    labelVisible: false
                  },
                  {
                    id: 'CloseOnMouseDownOutsideField',
                    objectType: 'CheckBoxField',
                    label: 'Close on Mouse Down Outside',
                    labelVisible: false
                  },
                  {
                    id: 'CloseOnOtherPopupOpenField',
                    objectType: 'CheckBoxField',
                    label: 'Close on Other Popup Open',
                    labelVisible: false
                  },
                  {
                    id: 'HorizontalSwitchField',
                    objectType: 'CheckBoxField',
                    label: 'Horizontal Switch',
                    labelVisible: false
                  },
                  {
                    id: 'VerticalSwitchField',
                    objectType: 'CheckBoxField',
                    label: 'Vertical Switch',
                    labelVisible: false
                  },
                  {
                    id: 'TrimWidthField',
                    objectType: 'CheckBoxField',
                    label: 'Trim Width',
                    labelVisible: false
                  },
                  {
                    id: 'TrimHeightField',
                    objectType: 'CheckBoxField',
                    label: 'Trim Height',
                    labelVisible: false
                  },
                  {
                    id: 'WithArrowField',
                    objectType: 'CheckBoxField',
                    label: 'With Arrow',
                    labelVisible: false
                  },
                  {
                    id: 'HorizontalAlignmentField',
                    objectType: 'SmartField',
                    lookupCall: 'jswidgets.PopupHorizontalAlignLookupCall',
                    label: 'Horizontal Alignment'
                  },
                  {
                    id: 'VerticalAlignmentField',
                    objectType: 'SmartField',
                    lookupCall: 'jswidgets.PopupVerticalAlignLookupCall',
                    label: 'Vertical Alignment'
                  }
                ]
              },
              {
                id: 'WidgetPopupPropertiesBox',
                objectType: 'jswidgets.WidgetPopupPropertiesBox'
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
