/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {ExtensionModel, NumberField} from '@eclipse-scout/core';
import {LogicalGridLayoutConfigBoxWidgetMap} from '../index';

export default (): ExtensionModel => ({
  id: 'jswidgets.TileGridLayoutConfigBox',
  type: 'extension',
  extensions: [
    {
      operation: 'insert',
      target: {
        id: 'jswidgets.LogicalGridLayoutConfigBox',
        property: 'fields',
        after: 'MinWidthField'
      },
      extension: [
        {
          id: 'MaxWidthField',
          objectType: NumberField,
          label: 'Max Width'
        }
      ]
    }
  ]
});

export type TileGridLayoutConfigBoxWidgetMap = {
  'MaxWidthField': NumberField;
} & LogicalGridLayoutConfigBoxWidgetMap;
