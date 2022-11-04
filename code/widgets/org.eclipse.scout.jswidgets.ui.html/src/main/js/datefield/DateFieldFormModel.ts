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
import {CheckBoxField, DateField, FormModel, GroupBox, Menu, NumberField, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {
  EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, ValueFieldPropertiesBox, ValueFieldPropertiesBoxWidgetMap,
  WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.DateFieldForm',
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
            id: 'DateField',
            objectType: DateField,
            label: '${textKey:DateField}'
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
                gridColumnCount: 2,
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
                  {
                    id: 'HasTimeField',
                    objectType: CheckBoxField,
                    label: 'Has Time',
                    labelVisible: false
                  },
                  {
                    id: 'HasDateField',
                    objectType: CheckBoxField,
                    label: 'Has Date',
                    labelVisible: false
                  },
                  {
                    id: 'AutoDateField',
                    objectType: DateField,
                    label: 'Auto Date',
                    tooltipText: '${textKey:AutoDateTooltip}',
                    hasTime: true
                  },
                  {
                    id: 'AllowedDatesField',
                    objectType: StringField,
                    label: 'Allowed Dates',
                    cssClass: 'allowed-dates-field',
                    menus: [{
                      id: 'AllowedSampleDatesMenu',
                      objectType: Menu,
                      text: '${textKey:UseSampleDates}'
                    }]
                  },
                  {
                    id: 'DateFormatField',
                    objectType: StringField,
                    label: 'Date Format'
                  },
                  {
                    id: 'TimeFormatField',
                    objectType: StringField,
                    label: 'Time Format'
                  },
                  {
                    id: 'TimePickerResolutionField',
                    objectType: NumberField,
                    label: 'Time Picker Resolution'
                  }
                ]
              },
              {
                id: 'CustomizationBox',
                objectType: GroupBox,
                gridColumnCount: 2,
                label: 'Customization Examples',
                fields: [
                  {
                    id: 'DontAllowCurrentDateField',
                    objectType: CheckBoxField,
                    label: 'Don\'t allow current date',
                    labelVisible: false
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

export type DateFieldFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'DateField': DateField;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'HasTimeField': CheckBoxField;
  'HasDateField': CheckBoxField;
  'AutoDateField': DateField;
  'AllowedDatesField': StringField;
  'AllowedSampleDatesMenu': Menu;
  'DateFormatField': StringField;
  'TimeFormatField': StringField;
  'TimePickerResolutionField': NumberField;
  'CustomizationBox': GroupBox;
  'DontAllowCurrentDateField': CheckBoxField;
  'ValueFieldPropertiesBox': ValueFieldPropertiesBox;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'FormFieldActionsBox': FormFieldActionsBox;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & ValueFieldPropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
