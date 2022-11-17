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
      id: 'MultiplierField',
      objectType: NumberField,
      label: 'Multiplier'
    },
    {
      id: 'FormatField',
      objectType: StringField,
      label: 'Format'
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

export type NumberColumnPropertiesBoxWidgetMap = {
  'MinValueField': NumberField;
  'MaxValueField': NumberField;
  'MultiplierField': NumberField;
  'FormatField': StringField;
  'BackgroundEffectField': SmartField<NumberColumnBackgroundEffect>;
};
