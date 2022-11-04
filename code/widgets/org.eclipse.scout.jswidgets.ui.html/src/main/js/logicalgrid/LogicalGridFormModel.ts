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
import {FormField, FormModel, GroupBox, SmartField, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {GridDataBox, GridDataBoxWidgetMap, GroupBoxAddFieldBox, GroupBoxAddFieldBoxWidgetMap, GroupBoxDeleteFieldBox, GroupBoxDeleteFieldBoxWidgetMap} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.LogicalGridForm',
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
                labelVisible: false,
                borderVisible: false,
                fields: [
                  {
                    id: 'TargetField',
                    objectType: SmartField,
                    label: '${textKey:Target}'
                  },
                  {
                    id: 'LogicalGridField',
                    objectType: SmartField,
                    label: 'Logical Grid',
                    lookupCall: 'jswidgets.LogicalGridLookupCall',
                    tooltipText: '${textKey:LogicalGridTooltip}'
                  }
                ]
              },
              {
                id: 'GridDataBox',
                objectType: GridDataBox,
                label: 'Grid Data Hints'
              },
              {
                id: 'CalculatedGridDataBox',
                objectType: GridDataBox,
                label: 'Calculated Grid Data',
                useHints: false,
                expandable: true,
                expanded: false
              }
            ]
          },
          {
            id: 'ActionsTab',
            objectType: TabItem,
            label: 'Actions',
            fields: [
              {
                id: 'Actions.AddFieldBox',
                objectType: GroupBoxAddFieldBox
              },
              {
                id: 'Actions.DeleteFieldBox',
                objectType: GroupBoxDeleteFieldBox
              }
            ]
          }
        ]
      }
    ]
  }
});

export type LogicalGridFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'StringField1': StringField;
  'StringField2': StringField;
  'StringField3': StringField;
  'StringField4': StringField;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'TargetField': SmartField<FormField>;
  'LogicalGridField': SmartField<string>;
  'GridDataBox': GridDataBox;
  'CalculatedGridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'Actions.AddFieldBox': GroupBoxAddFieldBox;
  'Actions.DeleteFieldBox': GroupBoxDeleteFieldBox;
} & GridDataBoxWidgetMap & GroupBoxAddFieldBoxWidgetMap & GroupBoxDeleteFieldBoxWidgetMap;
