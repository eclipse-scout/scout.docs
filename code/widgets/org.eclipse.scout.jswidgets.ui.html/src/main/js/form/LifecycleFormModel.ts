/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CancelMenu, CheckBoxField, CloseMenu, DateField, FormField, FormFieldMenu, FormModel, GroupBox, LabelField, OkMenu, ResetMenu, SaveMenu, SequenceBox, SmartField, StringField} from '@eclipse-scout/core';
import {StatusSeverityLookupCall} from '../index';

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
      },
      {
        objectType: FormFieldMenu,
        field: {
          objectType: DateField,
          label: '${textKey:CreationDate}',
          labelPosition: FormField.LabelPosition.ON_FIELD
        }
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
          },
          {
            id: 'AddErrorStatusSequenceBox',
            objectType: SequenceBox,
            label: 'Add error status',
            labelVisible: false,
            fields: [
              {
                id: 'AddErrorStatusWithSeverityField',
                objectType: LabelField,
                labelVisible: false,
                statusVisible: false,
                htmlEnabled: true,
                value: '<span class="app-link" data-ref="add">Add</span> error status with severity',
                gridDataHints: {
                  useUiWidth: true,
                  weightX: 0
                }
              },
              {
                id: 'StatusSeverityField',
                objectType: SmartField,
                label: 'Status Severity',
                labelVisible: false,
                statusVisible: false,
                lookupCall: StatusSeverityLookupCall,
                gridDataHints: {
                  weightX: 1
                }
              },
              {
                id: 'ToFieldField',
                objectType: LabelField,
                labelVisible: false,
                statusVisible: false,
                value: 'to field',
                gridDataHints: {
                  useUiWidth: true,
                  weightX: 0
                }
              },
              {
                id: 'TargetField',
                objectType: SmartField,
                label: 'Target',
                labelVisible: false,
                statusVisible: false,
                gridDataHints: {
                  weightX: 1
                }
              }
            ]
          }
        ]
      }
    ]
  }
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

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
  'AddErrorStatusSequenceBox': SequenceBox;
  'AddErrorStatusWithSeverityField': LabelField;
  'StatusSeverityField': SmartField<any>;
  'ToFieldField': LabelField;
  'TargetField': SmartField<any>;
};
