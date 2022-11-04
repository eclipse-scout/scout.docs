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
import {BreadcrumbBar, BreadcrumbBarField, BreadcrumbItem, FormModel, GroupBox, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {EventsTab, EventsTabWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.BreadcrumbBarFieldForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    fields: [
      {
        id: 'DetailBox',
        objectType: GroupBox,
        gridColumnCount: 2,
        fields: [
          {
            id: 'BreadcrumbBarField',
            objectType: BreadcrumbBarField,
            label: 'Breadcrumb Bar Field',
            breadcrumbBar: {
              objectType: BreadcrumbBar,
              breadcrumbItems: [
                {
                  objectType: BreadcrumbItem,
                  text: 'Root Folder',
                  ref: null
                },
                {
                  objectType: BreadcrumbItem,
                  text: 'Subfolder',
                  ref: 'sub-id'
                },
                {
                  objectType: BreadcrumbItem,
                  text: 'Sub-Subfolder',
                  ref: 'sub-sub-id'
                },
                {
                  objectType: BreadcrumbItem,
                  text: 'Sub-Sub-Subfolder',
                  ref: 'sub-sub-sub-id'
                }
              ]
            }
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
                id: 'BreadcrumbBarProperties',
                objectType: GroupBox,
                label: 'Breadcrumb Bar Configuration',
                fields: [
                  {
                    id: 'BreadcrumbItemsField',
                    objectType: StringField,
                    label: 'Breadcrumb Items',
                    multilineText: true,
                    tooltipText: 'Separate breadcrumbs by new line character',
                    gridDataHints: {
                      h: 3
                    }
                  }
                ]
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

export type BreadcrumbBarFieldFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'BreadcrumbBarField': BreadcrumbBarField;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'BreadcrumbBarProperties': GroupBox;
  'BreadcrumbItemsField': StringField;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
