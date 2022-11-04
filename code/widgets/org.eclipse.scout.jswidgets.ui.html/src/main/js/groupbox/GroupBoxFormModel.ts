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
import {FormField, FormModel, GroupBox, Menu, SmartField, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {
  EventsTab, EventsTabWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, GroupBoxAddFieldBox, GroupBoxAddFieldBoxWidgetMap, GroupBoxAddMenuBox, GroupBoxAddMenuBoxWidgetMap,
  GroupBoxDeleteFieldBox, GroupBoxDeleteFieldBoxWidgetMap, GroupBoxDeleteMenuBox, GroupBoxDeleteMenuBoxWidgetMap, GroupBoxPropertiesBox, GroupBoxPropertiesBoxWidgetMap, LogicalGridLayoutConfigBox, WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.GroupBoxForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    fields: [
      {
        id: 'DetailBox',
        objectType: GroupBox,
        label: 'Group Box',
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
        ],
        menus: [
          {
            id: 'Menu1',
            objectType: Menu,
            text: 'Menu',
            horizontalAlignment: 1
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
            label: 'Group Box Properties',
            fields: [
              {
                id: 'GroupBoxPropertiesBox',
                objectType: GroupBoxPropertiesBox,
                expandable: true,
                labelVisible: false,
                borderVisible: false
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
                id: 'BodyLayoutConfigBox',
                objectType: LogicalGridLayoutConfigBox
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
                id: 'Actions.AddMenuBox',
                objectType: GroupBoxAddMenuBox
              },
              {
                id: 'Actions.DeleteMenuBox',
                objectType: GroupBoxDeleteMenuBox
              },
              {
                id: 'Actions.AddFieldBox',
                objectType: GroupBoxAddFieldBox
              },
              {
                id: 'Actions.DeleteFieldBox',
                objectType: GroupBoxDeleteFieldBox
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

export type GroupBoxFormWidgetMap =
  {
    'MainBox': GroupBox;
    'DetailBox': GroupBox;
    'StringField1': StringField;
    'StringField2': StringField;
    'StringField3': StringField;
    'StringField4': StringField;
    'Menu1': Menu;
    'ConfigurationBox': TabBox;
    'PropertiesTab': TabItem;
    'GroupBoxPropertiesBox': GroupBoxPropertiesBox;
    'FormFieldPropertiesBox': FormFieldPropertiesBox;
    'GridDataBox': GridDataBox;
    'BodyLayoutConfigBox': LogicalGridLayoutConfigBox;
    'FieldPropertiesTab': TabItem;
    'Field.TargetField': SmartField<FormField>;
    'Field.FormFieldPropertiesBox': FormFieldPropertiesBox;
    'Field.GridDataBox': GridDataBox;
    'ActionsTab': TabItem;
    'Actions.AddMenuBox': GroupBoxAddMenuBox;
    'Actions.DeleteMenuBox': GroupBoxDeleteMenuBox;
    'Actions.AddFieldBox': GroupBoxAddFieldBox;
    'Actions.DeleteFieldBox': GroupBoxDeleteFieldBox;
    'WidgetActionsBox': WidgetActionsBox;
    'EventsTab': EventsTab;
  }
  & GroupBoxPropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap
  & GroupBoxAddMenuBoxWidgetMap & GroupBoxDeleteMenuBoxWidgetMap & GroupBoxAddFieldBoxWidgetMap & GroupBoxDeleteFieldBoxWidgetMap
  & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
