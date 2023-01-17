/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, FormModel, GroupBox, LabelField} from '@eclipse-scout/core';

export default (): FormModel => ({
  rootGroupBox: {
    objectType: GroupBox,
    borderDecoration: GroupBox.BorderDecoration.EMPTY,
    gridColumnCount: 1,
    gridDataHints: {
      useUiWidth: true
    },
    fields: [{
      objectType: LabelField,
      labelVisible: false,
      value: 'Hi there!',
      gridDataHints: {
        useUiWidth: true
      }
    }, {
      id: 'CloseButton',
      objectType: Button,
      displayStyle: Button.DisplayStyle.LINK,
      processButton: false,
      label: 'Close me please.',
      gridDataHints: {
        useUiWidth: true
      }
    }]
  }
});

export type MiniFormWidgetMap = {
  'CloseButton': Button;
};
