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
