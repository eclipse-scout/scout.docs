/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, CancelMenu, CheckBoxField, Form, FormField, FormModel, GroupBox, icons, OkMenu, Page, SequenceBox, StringField} from '@eclipse-scout/core';

export default (): FormModel => ({
  objectType: Form,
  title: 'Page configuration',
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
        objectType: SequenceBox,
        labelVisible: false,
        statusVisible: false,
        gridDataHints: {
          useUiHeight: true
        },
        fields: [
          {
            id: 'NameField',
            objectType: StringField,
            label: 'Name',
            labelPosition: FormField.LabelPosition.TOP,
            statusVisible: false,
            mandatory: true
          },
          {
            id: 'UpdateNameButton',
            objectType: Button,
            processButton: false,
            iconId: icons.ROTATE_RIGHT,
            displayStyle: Button.DisplayStyle.BORDERLESS,
            labelVisible: false,
            statusVisible: false,
            gridDataHints: {
              fillVertical: false,
              verticalAlignment: 1,
              useUiWidth: true,
              weightX: 0
            }
          }
        ]
      },
      {
        id: 'FlagsBox',
        objectType: GroupBox,
        borderVisible: false,
        gridColumnCount: 1,
        bodyLayoutConfig: {
          vgap: 0
        },
        fields: [
          {
            id: 'DetailFormVisibleCheckBox',
            objectType: CheckBoxField,
            label: 'Detail Form',
            labelVisible: false
          },
          {
            id: 'DetailTableVisibleCheckBox',
            objectType: CheckBoxField,
            label: 'Detail Table',
            labelVisible: false
          },
          {
            id: 'LeafCheckBox',
            objectType: CheckBoxField,
            label: 'Leaf',
            labelVisible: false
          },
          {
            id: 'DrillDownOnRowClickCheckBox',
            objectType: CheckBoxField,
            label: 'Drill Down on Row Click',
            labelVisible: false
          },
          {
            id: 'LazyExpandingEnabledCheckBox',
            objectType: CheckBoxField,
            label: 'Lazy Expanding Enabled',
            labelVisible: false
          }
        ]
      }
    ]
  }
});

export interface PageConfigFormModel extends FormModel {
  page?: Page;
}

export interface PageConfigFormData {
  name?: string;
  detailFormVisible?: boolean;
  detailTableVisible?: boolean;
  leaf?: boolean;
  drillDownOnRowClick?: boolean;
  lazyExpandingEnabled?: boolean;
}

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type PageConfigFormWidgetMap = {
  'OkMenu': OkMenu;
  'CancelMenu': CancelMenu;
  'NameField': StringField;
  'UpdateNameButton': Button;
  'FlagsBox': GroupBox;
  'DetailFormVisibleCheckBox': CheckBoxField;
  'DetailTableVisibleCheckBox': CheckBoxField;
  'LeafCheckBox': CheckBoxField;
  'DrillDownOnRowClickCheckBox': CheckBoxField;
  'LazyExpandingEnabledCheckBox': CheckBoxField;
};
