import {dragAndDrop} from '@eclipse-scout/core';

export default () => ({
  id: 'jswidgets.FormFieldPropertiesBox',
  objectType: 'GroupBox',
  gridColumnCount: 2,
  label: 'Form Field Properties',
  expandable: true,
  fields: [
    {
      id: 'EnabledField',
      objectType: 'CheckBoxField',
      label: 'Enabled',
      labelVisible: false
    },
    {
      id: 'VisibleField',
      objectType: 'CheckBoxField',
      label: 'Visible',
      labelVisible: false
    },
    {
      id: 'LabelVisibleField',
      objectType: 'CheckBoxField',
      label: 'Label Visible',
      labelVisible: false
    },
    {
      id: 'StatusVisibleField',
      objectType: 'CheckBoxField',
      label: 'Status Visible',
      labelVisible: false
    },
    {
      id: 'MandatoryField',
      objectType: 'CheckBoxField',
      label: 'Mandatory',
      labelVisible: false
    },
    {
      id: 'LoadingField',
      objectType: 'CheckBoxField',
      label: 'Loading',
      labelVisible: false
    },
    {
      id: 'LabelHtmlEnabledField',
      objectType: 'CheckBoxField',
      label: 'Label Html Enabled',
      labelVisible: false
    },
    {
      id: 'InheritAccessibilityField',
      objectType: 'CheckBoxField',
      label: 'Inherit Accessibility',
      labelVisible: false,
      tooltipText: '${textKey:InheritAccessibilityTooltip}',
      gridDataHints: {
        fillHorizontal: false
      }
    },
    {
      id: 'FieldStyleField',
      objectType: 'SmartField',
      lookupCall: 'jswidgets.FieldStyleLookupCall',
      label: 'Field Style',
      displayStyle: 'dropdown'
    },
    {
      id: 'DisabledStyleField',
      objectType: 'SmartField',
      lookupCall: 'jswidgets.DisabledStyleLookupCall',
      label: 'Disabled Style',
      displayStyle: 'dropdown'
    },
    {
      id: 'DropTypeField',
      objectType: 'SmartField',
      lookupCall: {
        objectType: 'StaticLookupCall',
        data: [
          [0, 'none'],
          [dragAndDrop.SCOUT_TYPES.FILE_TRANSFER, 'file transfer']
        ]
      },
      label: 'Drop Type',
      displayStyle: 'dropdown'
    },
    {
      id: 'DropMaximumSizeField',
      objectType: 'NumberField',
      label: 'Drop Maximum Size',
      tooltipText: '${textKey:DropMaximumSizeTooltip}'
    },
    {
      id: 'LabelField',
      objectType: 'StringField',
      label: 'Label'
    },
    {
      id: 'LabelPositionField',
      objectType: 'SmartField',
      lookupCall: 'jswidgets.LabelPositionLookupCall',
      label: 'Label Position',
      displayStyle: 'dropdown'
    },
    {
      id: 'LabelWidthInPixelField',
      objectType: 'ProposalField',
      lookupCall: 'jswidgets.LabelWidthInPixelLookupCall',
      label: 'Label Width in Pixel'
    },
    {
      id: 'TooltipTextField',
      objectType: 'StringField',
      label: 'Tooltip Text'
    },
    {
      id: 'TooltipAnchorField',
      objectType: 'SmartField',
      lookupCall: 'jswidgets.TooltipAnchorLookupCall',
      label: 'Tooltip Anchor',
      displayStyle: 'dropdown'
    },
    {
      id: 'ErrorStatusField',
      objectType: 'SmartField',
      lookupCall: 'jswidgets.StatusSeverityLookupCall',
      label: 'Error Status'
    },
    {
      id: 'StatusPositionField',
      objectType: 'SmartField',
      lookupCall: 'jswidgets.StatusPositionLookupCall',
      label: 'Status Position',
      displayStyle: 'dropdown'
    }
  ]
});
