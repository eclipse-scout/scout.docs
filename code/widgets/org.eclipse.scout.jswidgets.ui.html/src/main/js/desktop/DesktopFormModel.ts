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
import {Button, CheckBoxField, Desktop, DesktopUriAction, FormModel, GroupBox, Label, ProposalField, SmartField, StaticLookupCall, TabBox, TabItem, WidgetField} from '@eclipse-scout/core';
import {EventsTab, EventsTabWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.DesktopForm',
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
            objectType: WidgetField,
            labelVisible: false,
            statusVisible: false,
            gridDataHints: {
              useUiHeight: true
            },
            fieldWidget: {
              id: 'Label',
              objectType: Label,
              value: '${textKey:DesktopDescription}'
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
                objectType: GroupBox,
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
                  {
                    id: 'NavigationVisibleField',
                    objectType: CheckBoxField,
                    label: 'Navigation Visible',
                    labelVisible: false
                  },
                  {
                    id: 'HeaderVisibleField',
                    objectType: CheckBoxField,
                    label: 'Header Visible',
                    labelVisible: false
                  },
                  {
                    id: 'DenseField',
                    objectType: CheckBoxField,
                    label: 'Dense',
                    labelVisible: false
                  }
                ]
              }
            ]
          },
          {
            id: 'ActionsTab',
            objectType: TabItem,
            label: 'Actions',
            fields: [
              {
                id: 'DesktopActionsBox',
                objectType: GroupBox,
                fields: [
                  {
                    id: 'OpenUriBox',
                    objectType: GroupBox,
                    label: 'Open Uri',
                    fields: [
                      {
                        id: 'UriField',
                        objectType: ProposalField,
                        label: 'Uri',
                        lookupCall: {
                          objectType: StaticLookupCall,
                          data: [
                            ['https://www.eclipse.org/scout', 'https://www.eclipse.org/scout'],
                            ['http://google.com', 'http://google.com'],
                            ['mailto:info@example.org', 'mailto:info@example.org'],
                            ['tel:+1234567890', 'tel:+1234567890'],
                            ['img/fish.jpg', 'img/fish.jpg'],
                            ['misc/pdf-file.pdf', 'misc/pdf-file.pdf'],
                            ['http://maps.apple.com/?q=Baden, Schweiz', 'http://maps.apple.com/?q=Baden, Schweiz']
                          ]
                        }
                      },
                      {
                        id: 'UriActionField',
                        objectType: SmartField,
                        label: 'Action',
                        lookupCall: {
                          objectType: StaticLookupCall,
                          data: [
                            [Desktop.UriAction.DOWNLOAD, 'DOWNLOAD'],
                            [Desktop.UriAction.OPEN, 'OPEN'],
                            [Desktop.UriAction.NEW_WINDOW, 'NEW_WINDOW'],
                            [Desktop.UriAction.POPUP_WINDOW, 'POPUP_WINDOW'],
                            [Desktop.UriAction.SAME_WINDOW, 'SAME_WINDOW']
                          ]
                        }
                      },
                      {
                        id: 'OpenUriButton',
                        objectType: Button,
                        processButton: false,
                        label: 'Open'
                      }
                    ]
                  }
                ]
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

export type DesktopFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'Label': Label;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'NavigationVisibleField': CheckBoxField;
  'HeaderVisibleField': CheckBoxField;
  'DenseField': CheckBoxField;
  'ActionsTab': TabItem;
  'DesktopActionsBox': GroupBox;
  'OpenUriBox': GroupBox;
  'UriField': ProposalField;
  'UriActionField': SmartField<DesktopUriAction>;
  'OpenUriButton': Button;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
