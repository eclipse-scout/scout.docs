/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, ExtensionModel, SmartField, TableHierarchicalStyle} from '@eclipse-scout/core';
import {HierarchicalStyleLookupCall, TablePropertiesBoxWidgetMap} from '../../index';

export default (): ExtensionModel => ({
  id: 'jswidgets.HierarchicalTablePropertiesBox',
  type: 'extension',
  extensions: [
    {
      operation: 'insert',
      target: {
        id: 'jswidgets.TablePropertiesBox',
        property: 'fields',
        index: 100
      },
      extension: [
        {
          id: 'HierarchicalStyleField',
          objectType: SmartField,
          label: 'Hierarchical Style',
          lookupCall: HierarchicalStyleLookupCall
        },
        {
          id: 'ExtendedHierarchyPaddingField',
          objectType: CheckBoxField,
          label: 'Toggle extended hierarchy padding',
          labelVisible: false
        }
      ]
    }
  ]
});

export type HierarchicalTablePropertiesBoxWidgetMap = {
  'HierarchicalStyleField': SmartField<TableHierarchicalStyle>;
  'ExtendedHierarchyPaddingField': CheckBoxField;
} & TablePropertiesBoxWidgetMap;
