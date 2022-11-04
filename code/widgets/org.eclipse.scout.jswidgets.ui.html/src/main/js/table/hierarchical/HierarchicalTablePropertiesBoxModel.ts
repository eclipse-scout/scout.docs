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
import {CheckBoxField, ExtensionModel, SmartField, TableHierarchicalStyle} from '@eclipse-scout/core';
import {TablePropertiesBoxWidgetMap} from '../../index';

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
          lookupCall: 'jswidgets.HierarchicalStyleLookupCall'
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
