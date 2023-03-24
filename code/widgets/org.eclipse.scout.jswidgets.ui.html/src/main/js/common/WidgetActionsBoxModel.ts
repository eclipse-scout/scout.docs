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
import {ActionWithResultBox} from '../index';

export default (): GroupBoxModel => ({
  id: 'jswidgets.WidgetActionsBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'Widget Actions',
  expandable: true,
  fields: [
    {
      id: 'FocusSequenceBox',
      objectType: ActionWithResultBox,
      actionLabel: 'Focus',
      actionTooltipText: '${textKey:FocusTooltip}'
    },
    {
      id: 'IsFocusableSequenceBox',
      objectType: ActionWithResultBox,
      actionLabel: 'Is Focusable',
      actionTooltipText: '${textKey:FocusTooltip}'
    },
    {
      id: 'RevealButton',
      objectType: Button,
      label: 'Reveal',
      displayStyle: Button.DisplayStyle.LINK,
      processButton: false,
      tooltipText: '${textKey:RevealTooltip}'
    },
    {
      id: 'ScrollToTopButton',
      objectType: Button,
      label: 'Scroll To Top',
      displayStyle: Button.DisplayStyle.LINK,
      processButton: false
    },
    {
      id: 'ScrollToBottomButton',
      objectType: Button,
      label: 'Scroll To Bottom',
      displayStyle: Button.DisplayStyle.LINK,
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
  'FocusSequenceBox': ActionWithResultBox;
  'IsFocusableSequenceBox': ActionWithResultBox;
  'RevealButton': Button;
  'ScrollToTopButton': Button;
  'ScrollToBottomButton': Button;
  'ScrollTopField': NumberField;
};
