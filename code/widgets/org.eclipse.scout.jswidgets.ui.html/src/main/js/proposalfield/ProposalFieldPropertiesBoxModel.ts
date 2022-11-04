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
