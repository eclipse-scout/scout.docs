/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, FormModel, GroupBox, NumberField, RadioButton, RadioButtonGroup, SmartField, StaticLookupCall, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {
  EmptyLookupCall, ErroneousLookupCall, EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, IconIdLookupCall,
  LogicalGridLayoutConfigBox, LogicalGridLayoutConfigBoxWidgetMap, SalutationLookupCall, ValueFieldPropertiesBox, ValueFieldPropertiesBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap, WorldLookupCall
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.RadioButtonGroupForm',
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
            id: 'RadioButtonGroup',
            objectType: RadioButtonGroup,
            label: 'Radio Button Group',
            gridDataHints: {
              h: 3,
              weightY: 0
            },
            fields: [
              {
                id: 'RadioButton1',
                objectType: RadioButton,
                label: 'Radio Button 1',
                radioValue: 1
              },
              {
                id: 'RadioButton2',
                objectType: RadioButton,
                label: 'Radio Button 2',
                radioValue: 2
              },
              {
                id: 'RadioButton3',
                objectType: RadioButton,
                label: 'Radio Button 3',
                radioValue: 3
              }
            ]
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
            label: 'Group Properties',
            fields: [
              {
                id: 'PropertiesBox',
                objectType: GroupBox,
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
                  {
                    id: 'LookupCallField',
                    objectType: SmartField,
                    label: 'Lookup Call',
                    tooltipText: '${textKey:RadioButtonGroupLookupCallTooltip}',
                    lookupCall: {
                      objectType: StaticLookupCall,
                      data: [
                        [SalutationLookupCall, 'SalutationLookupCall'],
                        [WorldLookupCall, 'WorldLookupCall'],
                        [EmptyLookupCall, 'EmptyLookupCall'],
                        [ErroneousLookupCall, 'ErroneousLookupCall']
                      ]
                    }
                  },
                  {
                    id: 'SelectedButtonField',
                    objectType: SmartField,
                    label: 'Selected Button'
                  },
                  {
                    id: 'GridColumnCountField',
                    objectType: NumberField,
                    label: 'Grid Column Count'
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
              },
              {
                id: 'LayoutConfigBox',
                objectType: LogicalGridLayoutConfigBox,
                expanded: false
              }
            ]
          },
          {
            id: 'RadioButtonPropertiesTab',
            objectType: TabItem,
            label: 'Button Properties',
            fields: [
              {
                id: 'Button.TargetField',
                objectType: SmartField,
                label: 'Target'
              },
              {
                id: 'Button.PropertiesBox',
                objectType: GroupBox,
                label: 'Properties',
                fields: [
                  {
                    id: 'Button.SelectedField',
                    objectType: CheckBoxField,
                    label: 'Selected',
                    labelVisible: false
                  },
                  {
                    id: 'Button.WrapTextField',
                    objectType: CheckBoxField,
                    label: 'Wrap Text',
                    labelVisible: false
                  },
                  {
                    id: 'Button.IconIdField',
                    objectType: SmartField,
                    lookupCall: IconIdLookupCall,
                    label: 'Icon Id'
                  },
                  {
                    id: 'Button.KeyStrokeField',
                    objectType: StringField,
                    label: 'Key Stroke'
                  }
                ]
              },
              {
                id: 'Button.FormFieldPropertiesBox',
                objectType: FormFieldPropertiesBox
              },
              {
                id: 'Button.GridDataBox',
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

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type RadioButtonGroupFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'RadioButtonGroup': RadioButtonGroup<any>;
  'RadioButton1': RadioButton<any>;
  'RadioButton2': RadioButton<any>;
  'RadioButton3': RadioButton<any>;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'LookupCallField': SmartField<any>;
  'SelectedButtonField': SmartField<any>;
  'GridColumnCountField': NumberField;
  'ValueFieldPropertiesBox': ValueFieldPropertiesBox;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'LayoutConfigBox': LogicalGridLayoutConfigBox;
  'RadioButtonPropertiesTab': TabItem;
  'Button.TargetField': SmartField<any>;
  'Button.PropertiesBox': GroupBox;
  'Button.SelectedField': CheckBoxField;
  'Button.WrapTextField': CheckBoxField;
  'Button.IconIdField': SmartField<any>;
  'Button.KeyStrokeField': StringField;
  'Button.FormFieldPropertiesBox': FormFieldPropertiesBox;
  'Button.GridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'FormFieldActionsBox': FormFieldActionsBox;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & ValueFieldPropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & LogicalGridLayoutConfigBoxWidgetMap & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
