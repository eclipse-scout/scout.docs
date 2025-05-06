/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, CheckBoxField, FormModel, GroupBox, NumberField, SequenceBox, SliderField, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {
  EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, ValueFieldPropertiesBox, ValueFieldPropertiesBoxWidgetMap,
  WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
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
            id: 'SliderField',
            objectType: SliderField,
            label: 'Slider Field',
            value: 42
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
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
                  {
                    id: 'PropertiesLeftBox',
                    objectType: GroupBox,
                    borderVisible: false,
                    gridDataHints: {
                      w: 1
                    },
                    gridColumnCount: 1,
                    fields: [
                      {
                        id: 'StepField',
                        objectType: NumberField,
                        label: 'Step'
                      }
                    ]
                  },
                  {
                    id: 'PropertiesRightBox',
                    objectType: GroupBox,
                    borderVisible: false,
                    gridDataHints: {
                      w: 1
                    },
                    gridColumnCount: 1,
                    fields: [
                      {
                        id: 'SliderTabbableBox',
                        objectType: SequenceBox,
                        labelVisible: false,
                        fields: [
                          {
                            id: 'SliderTabbableField',
                            objectType: CheckBoxField,
                            label: 'Slider Tabbable',
                            labelVisible: false,
                            statusVisible: false,
                            gridDataHints: {
                              useUiWidth: true,
                              weightX: 0
                            }
                          },
                          {
                            id: 'SliderFieldFocusButton',
                            objectType: Button,
                            label: 'Focus field',
                            displayStyle: Button.DisplayStyle.LINK,
                            processButton: false,
                            visible: false,
                            gridDataHints: {
                              useUiWidth: true,
                              weightX: 0
                            }
                          },
                          {
                            id: 'SliderFocusButton',
                            objectType: Button,
                            label: 'Focus slider',
                            displayStyle: Button.DisplayStyle.LINK,
                            processButton: false,
                            visible: false,
                            gridDataHints: {
                              useUiWidth: true,
                              weightX: 0
                            }
                          }
                        ]
                      },
                      {
                        id: 'ValueEditableField',
                        objectType: CheckBoxField,
                        label: 'Value Editable',
                        labelVisible: false
                      }
                    ]
                  }
                ]
              },
              {
                id: 'NumberFieldPropertiesBox',
                objectType: GroupBox,
                label: 'Number Field Properties',
                expandable: true,
                fields: [
                  {
                    id: 'MinValueField',
                    objectType: NumberField,
                    label: 'Min Value'
                  },
                  {
                    id: 'MaxValueField',
                    objectType: NumberField,
                    label: 'Max Value'
                  },
                  {
                    id: 'FractionDigitsField',
                    objectType: NumberField,
                    label: 'Fraction Digits'
                  },
                  {
                    id: 'FormatField',
                    objectType: StringField,
                    label: 'Format'
                  },
                  {
                    id: 'MultiplierField',
                    objectType: NumberField,
                    label: 'Multiplier'
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
})
;

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type SliderFieldFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'SliderField': SliderField;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'PropertiesLeftBox': GroupBox;
  'StepField': NumberField;
  'PropertiesRightBox': GroupBox;
  'SliderTabbableBox': SequenceBox;
  'SliderTabbableField': CheckBoxField;
  'SliderFieldFocusButton': Button;
  'SliderFocusButton': Button;
  'ValueEditableField': CheckBoxField;
  'NumberFieldPropertiesBox': GroupBox;
  'MinValueField': NumberField;
  'MaxValueField': NumberField;
  'FractionDigitsField': NumberField;
  'FormatField': StringField;
  'MultiplierField': NumberField;
  'ValueFieldPropertiesBox': ValueFieldPropertiesBox;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'FormFieldActionsBox': FormFieldActionsBox;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & ValueFieldPropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
