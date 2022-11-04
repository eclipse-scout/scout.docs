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
import {CancelMenu, CheckBoxField, CloseMenu, DateField, FormModel, GroupBox, OkMenu, ResetMenu, SaveMenu, StringField} from '@eclipse-scout/core';

export default (): FormModel => ({
  id: 'jswidgets.LifecycleForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    menus: [
      {
        id: 'OkMenu',
        objectType: OkMenu,
        tooltipText: '${textKey:OkMenuTooltip}'
      },
      {
        id: 'CancelMenu',
        objectType: CancelMenu,
        tooltipText: '${textKey:CancelMenuTooltip}'
      },
      {
        id: 'SaveMenu',
        objectType: SaveMenu,
        tooltipText: '${textKey:SaveMenuTooltip}'
      },
      {
        id: 'ResetMenu',
        objectType: ResetMenu,
        tooltipText: '${textKey:ResetMenuTooltip}'
      },
      {
        id: 'CloseMenu',
        objectType: CloseMenu,
        tooltipText: '${textKey:CloseMenuTooltip}'
      }
    ],
    fields: [
      {
        id: 'DetailBox',
        objectType: GroupBox,
        gridColumnCount: 1,
        fields: [
          {
            id: 'NameField',
            objectType: StringField,
            label: 'Name',
            mandatory: true
          },
          {
            id: 'ForenameField',
            objectType: StringField,
            label: 'Forename'
          },
          {
            id: 'BirthdayField',
            objectType: DateField,
            label: 'Birthday'
          },
          {
            id: 'ExceptionField',
            objectType: CheckBoxField,
            label: '${textKey:ExceptionWhileSaving}'
          },
          {
            id: 'HasCloseButtonField',
            objectType: CheckBoxField,
            label: '${textKey:HasCloseButton}',
            tooltipText: '${textKey:HasCloseButtonTooltip}',
            value: true,
            gridDataHints: {
              fillHorizontal: false
            }
          },
          {
            id: 'AskIfNeedSaveField',
            objectType: CheckBoxField,
            label: '${textKey:AskToSaveChangesOnCancel}'
          }
        ]
      }
    ]
  }
});

export type LifecycleFormWidgetMap = {
  'MainBox': GroupBox;
  'OkMenu': OkMenu;
  'CancelMenu': CancelMenu;
  'SaveMenu': SaveMenu;
  'ResetMenu': ResetMenu;
  'CloseMenu': CloseMenu;
  'DetailBox': GroupBox;
  'NameField': StringField;
  'ForenameField': StringField;
  'BirthdayField': DateField;
  'ExceptionField': CheckBoxField;
  'HasCloseButtonField': CheckBoxField;
  'AskIfNeedSaveField': CheckBoxField;
};
