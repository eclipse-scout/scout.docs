/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormField, FormModel, GroupBox, HtmlField, icons, Menu} from '@eclipse-scout/core';

export default (): FormModel => ({
  objectType: Form,
  rootGroupBox: {
    objectType: GroupBox,
    borderDecoration: GroupBox.BorderDecoration.EMPTY,
    menus: [
      {
        id: 'UpdateMenu',
        objectType: Menu,
        iconId: icons.ROTATE_RIGHT,
        tooltipText: 'Update emoji',
        horizontalAlignment: 1,
        stackable: false
      }
    ],
    fields: [
      {
        id: 'HtmlField',
        objectType: HtmlField,
        labelVisible: false,
        statusVisible: false,
        gridDataHints: {
          w: FormField.FULL_WIDTH,
          h: 2
        },
        selectable: false
      }
    ]
  }
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type RandomEmojiFormWidgetMap = {
  'UpdateMenu': Menu;
  'HtmlField': HtmlField;
};
