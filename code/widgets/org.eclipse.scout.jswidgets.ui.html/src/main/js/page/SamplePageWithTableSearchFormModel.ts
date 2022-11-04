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
