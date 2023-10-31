/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, CheckBoxField, FormField, FormModel, GroupBox, NumberField, PlaceholderField, SequenceBox, SmartField, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {
  EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, StringFormatLookupCall, ValueFieldPropertiesBox,
  ValueFieldPropertiesBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.StringFieldForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    fields: [
      {
        id: 'DetailBox',
        objectType: GroupBox,
        gridColumnCount: 1,
        fields: [
          {
            id: 'StringField',
            objectType: StringField,
            label: '${textKey:StringField}'
          }
        ]
      },
      {
        id: 'ConfigurationBox',
        objectType: TabBox,
        cssClass: 'jswidgets-configuration',
        selectedTab: 'PropertiesTab',
        tabItems: [
          {
            id: 'PropertiesTab',
            objectType: TabItem,
            label: 'Properties',
            fields: [
              {
                id: 'PropertiesBox',
                objectType: GroupBox,
                expandable: true,
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
                  {
                    id: 'HasActionField',
                    objectType: CheckBoxField,
                    label: 'Has Action',
                    labelVisible: false
                  },
                  {
                    id: 'InputMaskedField',
                    objectType: CheckBoxField,
                    label: 'Input Masked',
                    labelVisible: false
                  },
                  {
                    id: 'MultilineTextField',
                    objectType: CheckBoxField,
                    label: 'Multiline Text',
                    labelVisible: false
                  },
                  {
                    id: 'SpellCheckEnabledField',
                    objectType: CheckBoxField,
                    label: 'Spell Check Enabled',
                    labelVisible: false,
                    tooltipText: '${textKey:SpellCheckEnabledTooltip}',
                    gridDataHints: {
                      fillHorizontal: false
                    }
                  },
                  {
                    id: 'TrimTextField',
                    objectType: CheckBoxField,
                    label: 'Trim Text',
                    labelVisible: false
                  },
                  {
                    id: 'UpdateDisplayTextOnModifyField',
                    objectType: CheckBoxField,
                    label: 'Update Display Text On Modify',
                    labelVisible: false
                  },
                  {
                    id: 'FormatField',
                    objectType: SmartField,
                    lookupCall: StringFormatLookupCall,
                    label: 'Format'
                  },
                  {
                    id: 'MaxLengthField',
                    objectType: NumberField,
                    label: 'Max Length'
                  },
                  {
                    id: 'SelectionTrackingEnabledField',
                    objectType: CheckBoxField,
                    label: 'Selection Tracking Enabled',
                    labelVisible: false
                  },
                  {
                    id: 'SelectionStartField',
                    objectType: NumberField,
                    label: 'Selection Start'
                  },
                  {
                    id: 'SelectionEndField',
                    objectType: NumberField,
                    label: 'Selection End'
                  }
                ]
              },
              {
                id: 'CustomizationBox',
                objectType: GroupBox,
                expandable: true,
                gridColumnCount: 2,
                label: 'Customization Examples',
                fields: [
                  {
                    id: 'BlockFormatField',
                    objectType: CheckBoxField,
                    label: 'Make 4 character blocks',
                    labelVisible: false,
                    tooltipText: '${textKey:BlockFormatTooltip}',
                    gridUseUiWidth: true
                  }
                ]
              },
              {
                id: 'ValueFieldPropertiesBox',
                objectType: ValueFieldPropertiesBox
              },
              {
                id: 'FormFieldPropertiesBox',
                objectType: FormFieldPropertiesBox
              },
              {
                id: 'GridDataBox',
                objectType: GridDataBox,
                label: 'Grid Data Hints'
              }
            ]
          },
          {
            id: 'ActionsTab',
            objectType: TabItem,
            label: 'Actions',
            fields: [
              {
                id: 'ActionsBox',
                objectType: GroupBox,
                borderVisible: false,
                fields: [{
                  id: 'InsertTextBox',
                  objectType: SequenceBox,
                  labelVisible: false,
                  fields: [
                    {
                      id: 'InsertTextField',
                      objectType: StringField,
                      labelVisible: false,
                      statusVisible: false,
                      label: 'Text to insert',
                      labelPosition: FormField.LabelPosition.ON_FIELD
                    },
                    {
                      id: 'InsertTextButton',
                      objectType: Button,
                      label: 'Insert',
                      labelVisible: false,
                      processButton: false,
                      displayStyle: Button.DisplayStyle.LINK
                    }
                  ]
                }, {
                  objectType: PlaceholderField
                }]
              },
              {
                id: 'FormFieldActionsBox',
                objectType: FormFieldActionsBox
              },
              {
                id: 'WidgetActionsBox',
                objectType: WidgetActionsBox
              }
            ]
          },
          {
            id: 'EventsTab',
            objectType: EventsTab
          }
        ]
      }
    ]
  }
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type StringFieldFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'StringField': StringField;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'HasActionField': CheckBoxField;
  'InputMaskedField': CheckBoxField;
  'MultilineTextField': CheckBoxField;
  'SpellCheckEnabledField': CheckBoxField;
  'TrimTextField': CheckBoxField;
  'UpdateDisplayTextOnModifyField': CheckBoxField;
  'FormatField': SmartField<any>;
  'MaxLengthField': NumberField;
  'SelectionTrackingEnabledField': CheckBoxField;
  'SelectionStartField': NumberField;
  'SelectionEndField': NumberField;
  'CustomizationBox': GroupBox;
  'BlockFormatField': CheckBoxField;
  'ValueFieldPropertiesBox': ValueFieldPropertiesBox;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'ActionsBox': GroupBox;
  'InsertTextBox': SequenceBox;
  'InsertTextField': StringField;
  'InsertTextButton': Button;
  'FormFieldActionsBox': FormFieldActionsBox;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & ValueFieldPropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
