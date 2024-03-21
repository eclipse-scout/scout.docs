/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CancelMenu, CheckBoxField, Form, FormField, FormModel, GroupBox, Mode, ModeSelector, ModeSelectorField, OkMenu, Page, StringField} from '@eclipse-scout/core';
import {PageTypeType} from './PageTypeLookupCall';

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
        id: 'NameField',
        objectType: StringField,
        label: 'Name',
        labelPosition: FormField.LabelPosition.TOP,
        mandatory: true
      },
      {
        id: 'PageTypeField',
        objectType: ModeSelectorField,
        label: 'Page type',
        labelPosition: FormField.LabelPosition.TOP,
        fieldStyle: FormField.FieldStyle.CLASSIC,
        mandatory: true,
        modeSelector: {
          objectType: ModeSelector,
          modes: [
            {
              objectType: Mode,
              text: 'Page With Nodes',
              ref: 'PageWithNodes'
            },
            {
              objectType: Mode,
              text: 'Page With Table',
              ref: 'PageWithTable'
            }
          ]
        }
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
  pageType?: PageTypeType;
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
  'PageTypeField': ModeSelectorField<PageTypeType>; // defined by hand
  'FlagsBox': GroupBox;
  'DetailFormVisibleCheckBox': CheckBoxField;
  'DetailTableVisibleCheckBox': CheckBoxField;
  'LeafCheckBox': CheckBoxField;
  'DrillDownOnRowClickCheckBox': CheckBoxField;
  'LazyExpandingEnabledCheckBox': CheckBoxField;
};
