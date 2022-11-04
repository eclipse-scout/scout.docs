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
