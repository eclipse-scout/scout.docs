/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
export default function() {
  return {
    id: 'jswidgets.TreeForm',
    displayHint: 'view',
    rootGroupBox: {
      id: 'MainBox',
      objectType: 'GroupBox',
      fields: [
        {
          id: 'DetailBox',
          objectType: 'GroupBox',
          gridColumnCount: 1,
          fields: [
            {
              id: 'TreeField',
              objectType: 'TreeField',
              labelVisible: false,
              gridDataHints: {
                h: 10
              },
              tree: {
                id: 'Tree',
                objectType: 'Tree',
                menus: [
                  {
                    id: 'AddNodeMenu',
                    objectType: 'Menu',
                    text: '${textKey:AddNode}',
                    menuTypes: [
                      'Tree.EmptySpace'
                    ],
                    keyStroke: 'insert'
                  },
                  {
                    id: 'AddChildNodeMenu',
                    objectType: 'Menu',
                    text: '${textKey:AddChildNode}',
                    menuTypes: [
                      'Tree.EmptySpace'
                    ],
                    keyStroke: 'insert'
                  },
                  {
                    id: 'DeleteNodeMenu',
                    objectType: 'Menu',
                    text: '${textKey:DeleteNode}',
                    menuTypes: [
                      'Tree.SingleSelection',
                      'Tree.MultiSelection'
                    ],
                    keyStroke: 'delete'
                  },
                  {
                    id: 'DeleteAllMenu',
                    objectType: 'Menu',
                    text: '${textKey:DeleteAll}',
                    menuTypes: [
                      'Tree.EmptySpace'
                    ],
                    childActions: [
                      {
                        id: 'DeleteAllNodesMenu',
                        objectType: 'Menu',
                        text: '${textKey:DeleteAllNodes}',
                        menuTypes: [
                          'Tree.EmptySpace'
                        ]
                      },
                      {
                        id: 'DeleteAllChildNodesMenu',
                        objectType: 'Menu',
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
          objectType: 'jswidgets.ConfigurationBox',
          selectedTab: 'PropertiesTab',
          tabItems: [
            {
              id: 'PropertiesTab',
              objectType: 'TabItem',
              label: 'Properties',
              fields: [
                {
                  id: 'PropertiesBox',
                  objectType: 'jswidgets.TreePropertiesBox',
                  label: 'Properties',
                  labelVisible: false,
                  borderVisible: false
                },
                {
                  id: 'FormFieldPropertiesBox',
                  objectType: 'jswidgets.FormFieldPropertiesBox',
                  expanded: false
                },
                {
                  id: 'GridDataBox',
                  objectType: 'jswidgets.GridDataBox',
                  label: 'Grid Data Hints',
                  expanded: false
                }
              ]
            },
            {
              id: 'ActionsTab',
              objectType: 'TabItem',
              label: 'Actions',
              fields: [
                {
                  id: 'FormFieldActionsBox',
                  objectType: 'jswidgets.FormFieldActionsBox'
                },
                {
                  id: 'WidgetActionsBox',
                  objectType: 'jswidgets.WidgetActionsBox'
                }
              ]
            },
            {
              id: 'EventsTab',
              objectType: 'jswidgets.EventsTab'
            }
          ]
        }
      ]
    }
  };
}
