/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Action, FormModel, GroupBox, Menu, ResetMenu, StringField} from '@eclipse-scout/core';

export default (): FormModel => ({
  id: 'jswidgets.SamplePageWithTableSearchForm',
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
            id: 'StringField',
            objectType: StringField,
            maxLength: 200,
            label: 'String Column'
          }
        ]
      }
    ],
    menus: [
      {
        id: 'SearchButton',
        objectType: Menu,
        actionStyle: Action.ActionStyle.BUTTON,
        text: '${textKey:Search}',
        keyStroke: 'ENTER'
      },
      {
        id: 'ResetMenu',
        objectType: ResetMenu
      }
    ]
  }
});

export type SamplePageWithTableSearchFormWidgetMap = {
  'MainBox': GroupBox;
  'DetailBox': GroupBox;
  'StringField': StringField;
  'SearchButton': Menu;
  'ResetMenu': ResetMenu;
};
