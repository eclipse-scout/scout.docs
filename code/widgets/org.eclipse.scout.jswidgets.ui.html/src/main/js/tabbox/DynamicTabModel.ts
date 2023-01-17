/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {LabelField, StringField, TabItem, TabItemModel} from '@eclipse-scout/core';

export default (): TabItemModel => ({
  id: 'jswidgets.DynamicTab',
  objectType: TabItem,
  label: 'Dyn Tab',
  fields: [
    {
      id: 'label',
      objectType: LabelField,
      labelVisible: false
    },
    {
      id: 'StringField',
      objectType: StringField,
      label: 'Sample Field'
    }
  ]
});

export type DynamicTabWidgetMap = {
  'label': LabelField;
  'StringField': StringField;
};
