/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {CheckBoxField, FormModel, GroupBox, NumberField, SmartField, StringField, StringFieldFormat, TabBox, TabItem} from '@eclipse-scout/core';
import {
  EventsTab,
  EventsTabWidgetMap,
  FormFieldActionsBox,
  FormFieldActionsBoxWidgetMap,
  FormFieldPropertiesBox,
  FormFieldPropertiesBoxWidgetMap,
  GridDataBox,
  GridDataBoxWidgetMap,
  StringFormatLookupCall,
  ValueFieldPropertiesBox,
  ValueFieldPropertiesBoxWidgetMap,
  WidgetActionsBox,
  WidgetActionsBoxWidgetMap
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

export type StringFieldFormWidgetMap =
  {
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
    'FormatField': SmartField<StringFieldFormat>;
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
    'FormFieldActionsBox': FormFieldActionsBox;
    'WidgetActionsBox': WidgetActionsBox;
    'EventsTab': EventsTab;
  }
  & ValueFieldPropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap
  & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
