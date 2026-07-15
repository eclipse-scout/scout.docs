/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, FormField, GroupBox, GroupBoxModel, LabelField, SmartField} from '@eclipse-scout/core';
import {IconIdLookupCall, TableRowDropTypeLookupCall} from '../index';

export default (): GroupBoxModel => ({
  objectType: GroupBox,
  fields: [
    {
      id: 'SelectedRowsLabelField',
      objectType: LabelField,
      labelVisible: false,
      statusVisible: false,
      selectable: false,
      gridDataHints: {
        w: FormField.FULL_WIDTH
      }
    },
    {
      id: 'LeftBox',
      objectType: GroupBox,
      borderVisible: false,
      gridDataHints: {
        w: 1
      },
      gridColumnCount: 1,
      fields: [
        {
          id: 'IconIdField',
          objectType: SmartField,
          lookupCall: IconIdLookupCall,
          label: 'Icon Id'
        },
        {
          id: 'EnabledField',
          objectType: CheckBoxField,
          label: 'Enabled'
        },
        {
          id: 'DraggableField',
          objectType: CheckBoxField,
          label: 'Draggable'
        }
      ]
    },
    {
      id: 'RightBox',
      objectType: GroupBox,
      borderVisible: false,
      gridDataHints: {
        w: 1
      },
      gridColumnCount: 1,
      fields: [
        {
          id: 'DropTypeBeforeField',
          objectType: SmartField,
          lookupCall: TableRowDropTypeLookupCall,
          label: 'Drop Type Before'
        },
        {
          id: 'DropTypeAfterField',
          objectType: SmartField,
          lookupCall: TableRowDropTypeLookupCall,
          label: 'Drop Type After'
        },
        {
          id: 'DropTypeInsideField',
          objectType: SmartField,
          lookupCall: TableRowDropTypeLookupCall,
          label: 'Drop Type Inside'
        }
      ]
    }
  ]
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type RowPropertiesBoxWidgetMap = {
  'SelectedRowsLabelField': LabelField;
  'LeftBox': GroupBox;
  'IconIdField': SmartField<any>;
  'EnabledField': CheckBoxField;
  'DraggableField': CheckBoxField;
  'RightBox': GroupBox;
  'DropTypeBeforeField': SmartField<any>;
  'DropTypeAfterField': SmartField<any>;
  'DropTypeInsideField': SmartField<any>;
};
