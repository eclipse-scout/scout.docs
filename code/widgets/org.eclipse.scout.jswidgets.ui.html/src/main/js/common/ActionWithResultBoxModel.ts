/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, LabelField, NumberField, SequenceBox, SequenceBoxModel} from '@eclipse-scout/core';

export default (): SequenceBoxModel => ({
  objectType: SequenceBox,
  labelVisible: false,
  statusVisible: false,
  fields: [{
    id: 'ActionButton',
    objectType: Button,
    displayStyle: 3,
    processButton: false,
    gridDataHints: {
      useUiWidth: true,
      weightX: 0
    }
  }, {
    id: 'ResultField',
    objectType: LabelField,
    labelVisible: false
  }]
});

export type ActionWithResultBoxWidgetMap = {
  'ActionButton': Button;
  'ResultField': LabelField;
};
