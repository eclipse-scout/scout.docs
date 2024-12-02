/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, ExtensionModel} from '@eclipse-scout/core';
import {LookupCallColumnPropertiesBoxWidgetMap} from './LookupCallColumnPropertiesBoxModel';

export default (): ExtensionModel => ({
  type: 'extension',
  extensions: [
    {
      operation: 'insert',
      target: {
        root: true,
        property: 'fields'
      },
      extension: [
        {
          id: 'DistinctField',
          objectType: CheckBoxField,
          label: 'Distinct',
          labelVisible: false
        }
      ]
    }
  ]
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type LookupColumnPropertiesBoxWidgetMap = {
  'DistinctField': CheckBoxField;
} & LookupCallColumnPropertiesBoxWidgetMap;
