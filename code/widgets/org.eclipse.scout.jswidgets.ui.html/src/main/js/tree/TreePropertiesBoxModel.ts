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
import {CheckBoxField, GroupBox, GroupBoxModel, SmartField, TreeCheckableStyle} from '@eclipse-scout/core';
import {CheckableTreeStyleLookupCall} from '../index';

export default (): GroupBoxModel => ({
  id: 'jswidgets.TreePropertiesBox',
  objectType: GroupBox,
  label: 'Tree Properties',
  fields: [
    {
      id: 'AutoCheckChildrenField',
      objectType: CheckBoxField,
      label: 'Auto Check Children',
      labelVisible: false,
      gridDataHints: {
        fillHorizontal: false
      }
    },
    {
      id: 'CheckableField',
      objectType: CheckBoxField,
      label: 'Checkable',
      labelVisible: false
    },
    {
      id: 'MultiCheckField',
      objectType: CheckBoxField,
      label: 'Multi Check',
      labelVisible: false
    },
    {
      id: 'TextFilterEnabledField',
      objectType: CheckBoxField,
      label: 'Text Filter Enabled',
      labelVisible: false
    },
    {
      id: 'CheckableStyleField',
      objectType: SmartField,
      label: 'Checkable Style',
      lookupCall: CheckableTreeStyleLookupCall
    }
  ]
});

export type TreePropertiesBoxWidgetMap = {
  'AutoCheckChildrenField': CheckBoxField;
  'CheckableField': CheckBoxField;
  'MultiCheckField': CheckBoxField;
  'TextFilterEnabledField': CheckBoxField;
  'CheckableStyleField': SmartField<TreeCheckableStyle>;
};
