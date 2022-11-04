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
import {Button, CloseMenu, FormModel, GroupBox, LabelField, TabBox, TabItem} from '@eclipse-scout/core';
import {EventsTab, EventsTabWidgetMap, FormPropertiesBox, FormPropertiesBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.FormForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    menus: [{
      id: 'CloseMenu',
      objectType: CloseMenu,
      tooltipText: '${textKey:CloseMenuTooltip}',
      visible: false
    }],
    fields: [
      {
        id: 'DetailBox',
        objectType: GroupBox,
        gridColumnCount: 1,
        fields: [
          {
            id: 'OpenFormButton',
            objectType: Button,
            label: '${textKey:OpenForm}',
            cssClass: 'open-form-button',
            processButton: false,
            gridDataHints: {
              horizontalAlignment: 0
            }
          },
          {
            id: 'OpenLifecycleFormButton',
            objectType: Button,
            label: '${textKey:OpenLifecycleForm}',
            processButton: false,
            gridDataHints: {
              horizontalAlignment: 0
            }
          },
          {
            id: 'LifecycleDataField',
            objectType: LabelField,
            label: '${textKey:LifecycleData}',
            visible: false,
            gridDataHints: {
              h: 3,
              weightY: 0
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
                id: 'PropertiesBox',
                objectType: FormPropertiesBox
              }
            ]
          },
          {
            id: 'CurrentFormPropertiesTab',
            objectType: TabItem,
            label: 'Properties current form',
            fields: [
              {
                id: 'CurrentFormPropertiesBox',
                objectType: FormPropertiesBox
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

export type FormFormWidgetMap = {
  'MainBox': GroupBox;
  'CloseMenu': CloseMenu;
  'DetailBox': GroupBox;
  'OpenFormButton': Button;
  'OpenLifecycleFormButton': Button;
  'LifecycleDataField': LabelField;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': FormPropertiesBox;
  'CurrentFormPropertiesTab': TabItem;
  'CurrentFormPropertiesBox': FormPropertiesBox;
  'ActionsTab': TabItem;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & FormPropertiesBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
