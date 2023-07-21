/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CancelMenu, CheckBoxField, DateField, FormModel, GroupBox, OkMenu, StringField} from '@eclipse-scout/core';

export default (): FormModel => ({
  id: 'PersonJsForm',
  title: 'Person',
  subTitle: 'JS',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    fields: [
      {
        id: 'GroupBox',
        objectType: GroupBox,
        fields: [
          {
            id: 'NameField',
            objectType: StringField,
            label: 'Name'
          },
          {
            id: 'FirstNameField',
            objectType: StringField,
            label: 'First Name'
          },
          {
            id: 'DateOfBirthField',
            objectType: DateField,
            label: 'Date of Birth'
          },
          {
            id: 'ActiveField',
            objectType: CheckBoxField,
            label: 'Active'
          }
        ]
      }
    ],
    menus: [
      {
        id: 'OkMenu',
        objectType: OkMenu
      },
      {
        id: 'CancelMenu',
        objectType: CancelMenu
      }
    ]
  }
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type PersonJsFormWidgetMap = {
  'MainBox': GroupBox;
  'GroupBox': GroupBox;
  'NameField': StringField;
  'FirstNameField': StringField;
  'DateOfBirthField': DateField;
  'ActiveField': CheckBoxField;
  'OkMenu': OkMenu;
  'CancelMenu': CancelMenu;
};
