/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
