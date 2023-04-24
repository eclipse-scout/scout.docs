/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {GroupBox, GroupBoxModel, NumberColumnBackgroundEffect, NumberField, SmartField, StringField} from '@eclipse-scout/core';
import {BackgroundEffectLookupCall} from '../index';

export default (): GroupBoxModel => ({
  id: 'jswidgets.NumberColumnPropertiesBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'NumberColumn Properties',
  expandable: true,
  fields: [
    {
      id: 'MinValueField',
      objectType: NumberField,
      label: 'Min Value'
    },
    {
      id: 'MaxValueField',
      objectType: NumberField,
      label: 'Max Value'
    },
    {
      id: 'FractionDigitsField',
      objectType: NumberField,
      label: 'Fraction Digits'
    },
    {
      id: 'FormatField',
      objectType: StringField,
      label: 'Format'
    },
    {
      id: 'MultiplierField',
      objectType: NumberField,
      label: 'Multiplier'
    },
    {
      id: 'BackgroundEffectField',
      objectType: SmartField,
      lookupCall: BackgroundEffectLookupCall,
      label: 'Background Effect',
      displayStyle: 'default'
    }
  ]
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type NumberColumnPropertiesBoxWidgetMap = {
  'MinValueField': NumberField;
  'MaxValueField': NumberField;
  'FractionDigitsField': NumberField;
  'FormatField': StringField;
  'MultiplierField': NumberField;
  'BackgroundEffectField': SmartField<NumberColumnBackgroundEffect>;
};
