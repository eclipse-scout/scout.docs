/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {ExtensionModel, StringField} from '@eclipse-scout/core';

export default (): ExtensionModel => ({
  type: 'extension',
  extensions: [
    {
      operation: 'appendTo',
      target: {
        id: 'StringField'
      },
      extension: {
        multilineText: true,
        wrapText: true,
        value: 'wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww',
        gridDataHints: {
          h: 3,
          fillVertical: false,
          useUiHeight: true
        }
      }
    }
  ]
});
