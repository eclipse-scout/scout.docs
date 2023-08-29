/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, Form, FormField, FormModel, GroupBox, Menu, SequenceBox, StringField} from '@eclipse-scout/core';

export default (): FormModel => ({
  id: 'jswidgets.RestForm',
  displayHint: Form.DisplayHint.VIEW,
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    fields: [
      {
        id: 'DetailBox',
        objectType: GroupBox,
        fields: [
          {
            objectType: GroupBox,
            label: 'UI',
            fields: [
              {
                id: 'TopicField',
                objectType: StringField,
                label: 'Topic'
              }, {
                objectType: SequenceBox,
                labelVisible: false,
                fields: [
                  {
                    id: 'SubscribeButton',
                    objectType: Button,
                    label: 'Subscribe',
                    processButton: false,
                    labelVisible: false,
                    gridDataHints: {
                      useUiWidth: true,
                      weightX: 0
                    }
                  }, {
                    id: 'UnsubscribeButton',
                    objectType: Button,
                    label: 'Unsubscribe',
                    processButton: false,
                    labelVisible: false,
                    gridDataHints: {
                      useUiWidth: true,
                      weightX: 0
                    }
                  }
                ]
              }
            ]
          },
          {
            objectType: GroupBox,
            label: 'Backend',
            fields: [
              {
                id: 'PublishTopicField',
                objectType: StringField,
                label: 'Topic'
              },
              {
                id: 'MessageField',
                objectType: StringField,
                multilineText: true,
                label: 'Message',
                gridDataHints: {
                  h: 3,
                  weightY: 0
                },
                menus: [
                  {
                    id: 'SampleMenu',
                    objectType: Menu,
                    text: 'Sample'
                  }
                ]
              },
              {
                id: 'PublishButton',
                objectType: Button,
                label: 'Publish',
                processButton: false
              }
            ]
          },
          {
            id: 'LogField',
            objectType: StringField,
            label: 'Log',
            labelPosition: FormField.LabelPosition.TOP,
            enabled: false,
            multilineText: true,
            gridDataHints: {
              w: 2,
              h: 3
            }
          }, {
            id: 'ClearLogButton',
            objectType: Button,
            label: 'Clear log',
            displayStyle: Button.DisplayStyle.LINK,
            processButton: false
          }
        ]
      }
    ]
  }
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type UiNotificationFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'TopicField': StringField;
  'SubscribeButton': Button;
  'UnsubscribeButton': Button;
  'PublishTopicField': StringField;
  'MessageField': StringField;
  'SampleMenu': Menu;
  'PublishButton': Button;
  'LogField': StringField;
  'ClearLogButton': Button;
};
