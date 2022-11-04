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
