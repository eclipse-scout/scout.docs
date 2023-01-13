/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {ExtensionModel, IntegerField} from '@eclipse-scout/core';

export default (): ExtensionModel => ({
  type: 'extension',
  id: 'IntegerFieldForm',
  extensions: [
    {
      operation: 'appendTo',
      target: {id: 'NumberField'},
      extension: {
        objectType: IntegerField,
        label: '${textKey:IntegerField}'
      }
    },
    {
      operation: 'appendTo',
      target: {id: 'CalculatorField'},
      extension: {
        objectType: IntegerField
      }
    },
    {
      operation: 'appendTo',
      target: {id: 'MinValueField'},
      extension: {
        objectType: IntegerField
      }
    },
    {
      operation: 'appendTo',
      target: {id: 'MaxValueField'},
      extension: {
        objectType: IntegerField
      }
    }
  ]
});
