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
import {FormField, FormModel, GroupBox, SequenceBox, SmartField, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {
  EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, LogicalGridLayoutConfigBox, LogicalGridLayoutConfigBoxWidgetMap,
  WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.SequenceBoxForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    fields: [
      {
        id: 'TopBox',
        objectType: GroupBox,
        fields: [
          {
            id: 'SequenceBox',
            objectType: SequenceBox,
            label: 'Sequence Box',
            gridDataHints: {
              w: 2
            },
            fields: [
              {
                id: 'StringField1',
                objectType: StringField,
                label: 'String Field 1'
              },
              {
                id: 'StringField2',
                objectType: StringField,
                label: 'String Field 2'
              },
              {
                id: 'StringField3',
                objectType: StringField,
                label: 'String Field 3'
              },
              {
                id: 'StringField4',
                objectType: StringField,
                label: 'String Field 4'
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
                label: 'Sequence Box Properties',
                fields: [
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
                id: 'FieldPropertiesTab',
                objectType: TabItem,
                label: 'Field Properties',
                fields: [
                  {
                    id: 'Field.TargetField',
                    objectType: SmartField,
                    label: 'Target'
                  },
                  {
                    id: 'Field.FormFieldPropertiesBox',
                    objectType: FormFieldPropertiesBox
                  },
                  {
                    id: 'Field.GridDataBox',
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
    ]
  }
});

export type SequenceBoxFormWidgetMap =
  {
    'MainBox': GroupBox;
    'TopBox': GroupBox;
    'SequenceBox': SequenceBox;
    'StringField1': StringField;
    'StringField2': StringField;
    'StringField3': StringField;
    'StringField4': StringField;
    'ConfigurationBox': TabBox;
    'PropertiesTab': TabItem;
    'FormFieldPropertiesBox': FormFieldPropertiesBox;
    'GridDataBox': GridDataBox;
    'LayoutConfigBox': LogicalGridLayoutConfigBox;
    'FieldPropertiesTab': TabItem;
    'Field.TargetField': SmartField<FormField>;
    'Field.FormFieldPropertiesBox': FormFieldPropertiesBox;
    'Field.GridDataBox': GridDataBox;
    'ActionsTab': TabItem;
    'FormFieldActionsBox': FormFieldActionsBox;
    'WidgetActionsBox': WidgetActionsBox;
    'EventsTab': EventsTab;
  }
  & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & LogicalGridLayoutConfigBoxWidgetMap
  & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
