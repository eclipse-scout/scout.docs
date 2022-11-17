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
import {Button, CheckBoxField, FormModel, GroupBox, HtmlField, NumberField, SmartField, StringField, TabBox, TabItem} from '@eclipse-scout/core';
import {DisplayParentLookupCall, EventsTab, EventsTabWidgetMap} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.FileChooserForm',
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
            id: 'Button',
            objectType: Button,
            label: 'Open file chooser',
            processButton: false,
            gridDataHints: {
              horizontalAlignment: 0
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
                expandable: true,
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
                  {
                    id: 'AcceptTypesField',
                    objectType: StringField,
                    label: 'Accept Types',
                    tooltipText: '${textKey:FileChooserAcceptTypesTooltip}'
                  },
                  {
                    id: 'DisplayParentField',
                    objectType: SmartField,
                    lookupCall: DisplayParentLookupCall,
                    label: 'Display Parent'
                  },
                  {
                    id: 'MaximumUploadSizeField',
                    objectType: NumberField,
                    label: 'Max. Upload Size',
                    tooltipText: '${textKey:FileChooserMaximumUploadSizeTooltip}'
                  },
                  {
                    id: 'MultiSelectField',
                    objectType: CheckBoxField,
                    label: 'Multi Select',
                    labelVisible: false
                  }
                ]
              },
              {
                id: 'ChosenFilesBox',
                objectType: GroupBox,
                expandable: true,
                label: '${textKey:FileChooserChosenFiles}',
                fields: [
                  {
                    id: 'ChosenFilesField',
                    objectType: HtmlField,
                    labelVisible: false
                  }
                ]
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

export type FileChooserFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'Button': Button;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'AcceptTypesField': StringField;
  'DisplayParentField': SmartField<string>;
  'MaximumUploadSizeField': NumberField;
  'MultiSelectField': CheckBoxField;
  'ChosenFilesBox': GroupBox;
  'ChosenFilesField': HtmlField;
  'EventsTab': EventsTab;
} & EventsTabWidgetMap;
