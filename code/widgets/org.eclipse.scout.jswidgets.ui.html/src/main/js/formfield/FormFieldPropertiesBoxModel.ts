/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, DropType, GroupBox, GroupBoxModel, NumberField, ProposalField, SmartField, StaticLookupCall, StringField} from '@eclipse-scout/core';
import {DisabledStyleLookupCall, FieldStyleLookupCall, LabelPositionLookupCall, LabelWidthInPixelLookupCall, StatusPositionLookupCall, StatusSeverityLookupCall, TooltipAnchorLookupCall} from '../index';

export default (): GroupBoxModel => ({
  id: 'jswidgets.FormFieldPropertiesBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'Form Field Properties',
  expandable: true,
  fields: [
    {
      id: 'EnabledField',
      objectType: CheckBoxField,
      label: 'Enabled',
      labelVisible: false
    },
    {
      id: 'EnabledGrantedField',
      objectType: CheckBoxField,
      label: 'Enabled Granted',
      labelVisible: false
    },
    {
      id: 'VisibleField',
      objectType: CheckBoxField,
      label: 'Visible',
      labelVisible: false
    },
    {
      id: 'VisibleGrantedField',
      objectType: CheckBoxField,
      label: 'Visible Granted',
      labelVisible: false
    },
    {
      id: 'LabelVisibleField',
      objectType: CheckBoxField,
      label: 'Label Visible',
      labelVisible: false
    },
    {
      id: 'StatusVisibleField',
      objectType: CheckBoxField,
      label: 'Status Visible',
      labelVisible: false
    },
    {
      id: 'MandatoryField',
      objectType: CheckBoxField,
      label: 'Mandatory',
      labelVisible: false
    },
    {
      id: 'LoadingField',
      objectType: CheckBoxField,
      label: 'Loading',
      labelVisible: false
    },
    {
      id: 'LabelHtmlEnabledField',
      objectType: CheckBoxField,
      label: 'Label Html Enabled',
      labelVisible: false
    },
    {
      id: 'InheritAccessibilityField',
      objectType: CheckBoxField,
      label: 'Inherit Accessibility',
      labelVisible: false,
      tooltipText: '${textKey:InheritAccessibilityTooltip}',
      gridDataHints: {
        fillHorizontal: false
      }
    },
    {
      id: 'FieldStyleField',
      objectType: SmartField,
      lookupCall: FieldStyleLookupCall,
      label: 'Field Style',
      displayStyle: 'dropdown'
    },
    {
      id: 'DisabledStyleField',
      objectType: SmartField,
      lookupCall: DisabledStyleLookupCall,
      label: 'Disabled Style',
      displayStyle: 'dropdown'
    },
    {
      id: 'DropTypeField',
      objectType: SmartField,
      lookupCall: {
        objectType: StaticLookupCall,
        data: [
          [0, 'none'],
          [DropType.FILE_TRANSFER, 'file transfer']
        ]
      },
      label: 'Drop Type',
      displayStyle: 'dropdown'
    },
    {
      id: 'DropMaximumSizeField',
      objectType: NumberField,
      label: 'Drop Maximum Size',
      tooltipText: '${textKey:DropMaximumSizeTooltip}'
    },
    {
      id: 'LabelField',
      objectType: StringField,
      label: 'Label'
    },
    {
      id: 'LabelPositionField',
      objectType: SmartField,
      lookupCall: LabelPositionLookupCall,
      label: 'Label Position',
      displayStyle: 'dropdown'
    },
    {
      id: 'LabelWidthInPixelField',
      objectType: ProposalField,
      lookupCall: LabelWidthInPixelLookupCall,
      label: 'Label Width in Pixel'
    },
    {
      id: 'TooltipTextField',
      objectType: StringField,
      label: 'Tooltip Text'
    },
    {
      id: 'TooltipAnchorField',
      objectType: SmartField,
      lookupCall: TooltipAnchorLookupCall,
      label: 'Tooltip Anchor',
      displayStyle: 'dropdown'
    },
    {
      id: 'ErrorStatusField',
      objectType: SmartField,
      lookupCall: StatusSeverityLookupCall,
      label: 'Error Status'
    },
    {
      id: 'StatusPositionField',
      objectType: SmartField,
      lookupCall: StatusPositionLookupCall,
      label: 'Status Position',
      displayStyle: 'dropdown'
    }
  ]
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type FormFieldPropertiesBoxWidgetMap = {
  'EnabledField': CheckBoxField;
  'EnabledGrantedField': CheckBoxField;
  'VisibleField': CheckBoxField;
  'VisibleGrantedField': CheckBoxField;
  'LabelVisibleField': CheckBoxField;
  'StatusVisibleField': CheckBoxField;
  'MandatoryField': CheckBoxField;
  'LoadingField': CheckBoxField;
  'LabelHtmlEnabledField': CheckBoxField;
  'InheritAccessibilityField': CheckBoxField;
  'FieldStyleField': SmartField<any>;
  'DisabledStyleField': SmartField<any>;
  'DropTypeField': SmartField<any>;
  'DropMaximumSizeField': NumberField;
  'LabelField': StringField;
  'LabelPositionField': SmartField<any>;
  'LabelWidthInPixelField': ProposalField;
  'TooltipTextField': StringField;
  'TooltipAnchorField': SmartField<any>;
  'ErrorStatusField': SmartField<any>;
  'StatusPositionField': SmartField<any>;
};
