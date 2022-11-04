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
import {FormModel, GroupBox, Menu, TabItem, Tree, TreeField} from '@eclipse-scout/core';
import {
  ConfigurationBox, EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, TreePropertiesBox, TreePropertiesBoxWidgetMap,
  WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.TreeForm',
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
            id: 'TreeField',
            objectType: TreeField,
            labelVisible: false,
            gridDataHints: {
              h: 10
            },
            tree: {
              id: 'Tree',
              objectType: Tree,
              menus: [
                {
                  id: 'AddNodeMenu',
                  objectType: Menu,
                  text: '${textKey:AddNode}',
                  menuTypes: [
                    'Tree.EmptySpace'
                  ],
                  keyStroke: 'insert'
                },
                {
                  id: 'AddChildNodeMenu',
                  objectType: Menu,
                  text: '${textKey:AddChildNode}',
                  menuTypes: [
                    'Tree.EmptySpace'
                  ],
                  keyStroke: 'insert'
                },
                {
                  id: 'DeleteNodeMenu',
                  objectType: Menu,
                  text: '${textKey:DeleteNode}',
                  menuTypes: [
                    'Tree.SingleSelection',
                    'Tree.MultiSelection'
                  ],
                  keyStroke: 'delete'
                },
                {
                  id: 'DeleteAllMenu',
                  objectType: Menu,
                  text: '${textKey:DeleteAll}',
                  menuTypes: [
                    'Tree.EmptySpace'
                  ],
                  childActions: [
                    {
                      id: 'DeleteAllNodesMenu',
                      objectType: Menu,
                      text: '${textKey:DeleteAllNodes}',
                      menuTypes: [
                        'Tree.EmptySpace'
                      ]
                    },
                    {
                      id: 'DeleteAllChildNodesMenu',
                      objectType: Menu,
                      text: '${textKey:DeleteAllChildNodes}',
                      menuTypes: [
                        'Tree.EmptySpace'
                      ]
                    }
                  ]
                }
              ]
            }
          }
        ]
      },
      {
        id: 'ConfigurationBox',
        objectType: ConfigurationBox,
        selectedTab: 'PropertiesTab',
        tabItems: [
          {
            id: 'PropertiesTab',
            objectType: TabItem,
            label: 'Properties',
            fields: [
              {
                id: 'PropertiesBox',
                objectType: TreePropertiesBox,
                label: 'Properties',
                labelVisible: false,
                borderVisible: false
              },
              {
                id: 'FormFieldPropertiesBox',
                objectType: FormFieldPropertiesBox,
                expanded: false
              },
              {
                id: 'GridDataBox',
                objectType: GridDataBox,
                label: 'Grid Data Hints',
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

export type TreeFormWidgetMap =
  {
    'MainBox': GroupBox;
    'DetailBox': GroupBox;
    'TreeField': TreeField;
    'Tree': Tree;
    'AddNodeMenu': Menu;
    'AddChildNodeMenu': Menu;
    'DeleteNodeMenu': Menu;
    'DeleteAllMenu': Menu;
    'DeleteAllNodesMenu': Menu;
    'DeleteAllChildNodesMenu': Menu;
    'ConfigurationBox': ConfigurationBox;
    'PropertiesTab': TabItem;
    'PropertiesBox': TreePropertiesBox;
    'FormFieldPropertiesBox': FormFieldPropertiesBox;
    'GridDataBox': GridDataBox;
    'ActionsTab': TabItem;
    'FormFieldActionsBox': FormFieldActionsBox;
    'WidgetActionsBox': WidgetActionsBox;
    'EventsTab': EventsTab;
  }
  & TreePropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap
  & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
