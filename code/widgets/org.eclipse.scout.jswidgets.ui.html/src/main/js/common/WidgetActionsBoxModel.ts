/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, GroupBox, GroupBoxModel, LabelField, NumberField, SequenceBox} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  id: 'jswidgets.WidgetActionsBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'Widget Actions',
  expandable: true,
  fields: [
    {
      id: 'FocusSequenceBox',
      objectType: SequenceBox,
      labelVisible: false,
      statusVisible: false,
      fields: [
        {
          id: 'FocusButton',
          objectType: Button,
          label: 'Focus',
          displayStyle: 3,
          processButton: false,
          tooltipText: '${textKey:FocusTooltip}',
          gridDataHints: {
            useUiWidth: true,
            weightX: 0
          }
        },
        {
          id: 'FocusReturnField',
          objectType: LabelField,
          labelVisible: false
        }
      ]
    },
    {
      id: 'IsFocusableSequenceBox',
      objectType: SequenceBox,
      labelVisible: false,
      statusVisible: false,
      fields: [
        {
          id: 'IsFocusableButton',
          objectType: Button,
          label: 'Is Focusable',
          displayStyle: 3,
          processButton: false,
          gridDataHints: {
            useUiWidth: true,
            weightX: 0
          }
        },
        {
          id: 'IsFocusableReturnField',
          objectType: LabelField,
          labelVisible: false
        }
      ]
    },
    {
      id: 'RevealButton',
      objectType: Button,
      label: 'Reveal',
      displayStyle: 3,
      processButton: false,
      tooltipText: '${textKey:RevealTooltip}'
    },
    {
      id: 'ScrollToTopButton',
      objectType: Button,
      label: 'Scroll To Top',
      displayStyle: 3,
      processButton: false
    },
    {
      id: 'ScrollToBottomButton',
      objectType: Button,
      label: 'Scroll To Bottom',
      displayStyle: 3,
      processButton: false
    },
    {
      id: 'ScrollTopField',
      objectType: NumberField,
      label: 'Scroll Top'
    }
  ]
});

export type WidgetActionsBoxWidgetMap = {
  'FocusSequenceBox': SequenceBox;
  'FocusButton': Button;
  'FocusReturnField': LabelField;
  'IsFocusableSequenceBox': SequenceBox;
  'IsFocusableButton': Button;
  'IsFocusableReturnField': LabelField;
  'RevealButton': Button;
  'ScrollToTopButton': Button;
  'ScrollToBottomButton': Button;
  'ScrollTopField': NumberField;
};
