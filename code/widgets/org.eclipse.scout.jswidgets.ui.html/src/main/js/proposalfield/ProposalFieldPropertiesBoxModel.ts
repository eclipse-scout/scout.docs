/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, ExtensionModel, NumberField} from '@eclipse-scout/core';
import {SmartFieldPropertiesBoxWidgetMap} from '../index';

export default (): ExtensionModel => ({
  id: 'jswidgets.ProposalFieldPropertiesBox',
  type: 'extension',
  extensions: [
    {
      operation: 'insert',
      target: {
        id: 'jswidgets.SmartFieldPropertiesBox',
        property: 'fields',
        before: 'ActiveFilterEnabledField'
      },
      extension: [
        {
          id: 'MaxLengthField',
          objectType: NumberField,
          label: 'Max Length'
        },
        {
          id: 'TrimTextField',
          objectType: CheckBoxField,
          label: 'Trim Text'
        }
      ]
    }
  ]
});

export type ProposalFieldPropertiesBoxWidgetMap = {
  'MaxLengthField': NumberField;
  'TrimTextField': CheckBoxField;
} & SmartFieldPropertiesBoxWidgetMap;
