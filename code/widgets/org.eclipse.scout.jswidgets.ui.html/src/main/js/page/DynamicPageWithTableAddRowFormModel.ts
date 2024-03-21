/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CancelMenu, Form, FormModel, GroupBox, OkMenu, RadioButtonGroup, StringField} from '@eclipse-scout/core';
import {PageTypeLookupCall, PageTypeType} from './PageTypeLookupCall';

export default (): FormModel => ({
  objectType: Form,
  title: 'Add row',
  saveNeededVisible: false,
  rootGroupBox: {
    objectType: GroupBox,
    borderDecoration: GroupBox.BorderDecoration.EMPTY,
    gridColumnCount: 1,
    menus: [
      {
        id: 'OkMenu',
        objectType: OkMenu
      },
      {
        id: 'CancelMenu',
        objectType: CancelMenu
      }
    ],
    fields: [
      {
        id: 'NameField',
        objectType: StringField,
        label: 'Name',
        mandatory: true
      },
      {
        id: 'PageTypeField',
        objectType: RadioButtonGroup,
        label: 'Child type',
        mandatory: true,
        gridColumnCount: 1,
        gridDataHints: {
          useUiHeight: true,
          weightY: 0
        },
        layoutConfig: {
          vgap: 0
        },
        lookupCall: PageTypeLookupCall
      }
    ]
  }
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type DynamicPageWithTableAddRowFormWidgetMap = {
  'OkMenu': OkMenu;
  'CancelMenu': CancelMenu;
  'NameField': StringField;
  'PageTypeField': RadioButtonGroup<PageTypeType>; // defined by hand
};
