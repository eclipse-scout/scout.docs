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
import {BrowserField, Button, CheckBoxField, FormModel, GroupBox, HtmlField, ProposalField, StaticLookupCall, StringField, TabItem} from '@eclipse-scout/core';
import {
  ConfigurationBox, EventsTab, EventsTabWidgetMap, FormFieldActionsBox, FormFieldActionsBoxWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GridDataBox, GridDataBoxWidgetMap, WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.BrowserFieldForm',
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
            id: 'BrowserField',
            objectType: BrowserField,
            labelVisible: false,
            location: 'html/Scout.html',
            sandboxEnabled: false,
            gridDataHints: {
              h: 6,
              w: 2
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
                objectType: GroupBox,
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
                  {
                    id: 'ScrollBarEnabledField',
                    objectType: CheckBoxField,
                    label: 'Scroll Bar Enabled',
                    labelVisible: false
                  },
                  {
                    id: 'SandboxEnabledField',
                    objectType: CheckBoxField,
                    label: 'Sandbox Enabled',
                    labelVisible: false
                  },
                  {
                    id: 'SandboxPermissionsField',
                    objectType: StringField,
                    label: 'Sandbox Permissions'
                  },
                  {
                    id: 'LocationField',
                    objectType: ProposalField,
                    label: 'Location',
                    lookupCall: {
                      objectType: StaticLookupCall,
                      data: [
                        ['https://www.example.net', 'https://www.example.net'],
                        ['https://www.bing.com/search?q=Eclipse%20Scout', 'https://www.bing.com/search?q=Eclipse%20Scout'],
                        ['https://www.bsi-software.com', 'https://www.bsi-software.com'],
                        ['html/Scout.html', 'html/Scout.html'],
                        ['html/PostMessageDemo.html', 'html/PostMessageDemo.html']
                      ]
                    }
                  },
                  {
                    id: 'TrackLocationField',
                    objectType: CheckBoxField,
                    label: 'Track Location',
                    labelVisible: false,
                    tooltipText: '${textKey:TrackLocationTooltipText}',
                    gridDataHints: {
                      fillHorizontal: false
                    }
                  },
                  {
                    id: 'ShowInExternalWindowField',
                    objectType: CheckBoxField,
                    label: 'Show External Window',
                    labelVisible: false
                  },
                  {
                    id: 'AutoCloseExternalWindowField',
                    objectType: CheckBoxField,
                    label: 'Auto Close External Window',
                    labelVisible: false
                  },
                  {
                    id: 'ExternalWindowButtonTextField',
                    objectType: StringField,
                    label: 'External Window Button Text'
                  },
                  {
                    id: 'ExternalWindowFieldTextField',
                    objectType: StringField,
                    label: 'External Window Field Text'
                  }
                ]
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
                id: 'BrowserFieldActionsBox',
                objectType: GroupBox,
                gridColumnCount: 2,
                label: 'Browser Field Actions',
                expandable: true,
                fields: [
                  {
                    id: 'MessageDescriptionField',
                    objectType: HtmlField,
                    labelVisible: false,
                    gridDataHints: {
                      useUiHeight: true,
                      w: 2
                    },
                    value: 'The browser field can communicate with an embedded page via the "postMessage" ' +
                      'method. Arbitrary messages can be sent to and received from the the &lt;iframe&gt; object. ' +
                      'To see it in action, load the <i>PostMessageDemo.html</i> page.<br>' +
                      'More details can be found at <a href="https://developer.mozilla.org/en-US/docs/Web/API/Window/postMessage" ' +
                      'target="_blank" rel="noopener noreferrer">' +
                      'https://developer.mozilla.org/en-US/docs/Web/API/Window/postMessage</a>.'
                  },
                  {
                    id: 'PostTextMessageButton',
                    objectType: Button,
                    label: 'Post text message',
                    labelVisible: false,
                    processButton: false,
                    displayStyle: Button.DisplayStyle.LINK
                  },
                  {
                    id: 'PostJsonMessageButton',
                    objectType: Button,
                    label: 'Post JSON message',
                    labelVisible: false,
                    processButton: false,
                    displayStyle: Button.DisplayStyle.LINK
                  }
                ]
              },
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

export type BrowserFieldFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'BrowserField': BrowserField;
  'ConfigurationBox': ConfigurationBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'ScrollBarEnabledField': CheckBoxField;
  'SandboxEnabledField': CheckBoxField;
  'SandboxPermissionsField': StringField;
  'LocationField': ProposalField;
  'TrackLocationField': CheckBoxField;
  'ShowInExternalWindowField': CheckBoxField;
  'AutoCloseExternalWindowField': CheckBoxField;
  'ExternalWindowButtonTextField': StringField;
  'ExternalWindowFieldTextField': StringField;
  'FormFieldPropertiesBox': FormFieldPropertiesBox;
  'GridDataBox': GridDataBox;
  'ActionsTab': TabItem;
  'BrowserFieldActionsBox': GroupBox;
  'MessageDescriptionField': HtmlField;
  'PostTextMessageButton': Button;
  'PostJsonMessageButton': Button;
  'FormFieldActionsBox': FormFieldActionsBox;
  'WidgetActionsBox': WidgetActionsBox;
  'EventsTab': EventsTab;
} & FormFieldPropertiesBoxWidgetMap & GridDataBoxWidgetMap & FormFieldActionsBoxWidgetMap & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
