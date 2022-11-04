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
import {Button, FormField, GroupBoxModel, LabelField, StringField, TabItem} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  id: 'jswidgets.EventsTab',
  objectType: TabItem,
  label: 'Events',
  gridColumnCount: 1,
  fields: [
    {
      id: 'EventsOverviewField',
      objectType: LabelField,
      labelVisible: false,
      htmlEnabled: true,
      wrapText: true,
      gridDataHints: {
        useUiHeight: true
      }
    },
    {
      id: 'EventLogField',
      objectType: StringField,
      label: '${textKey:EventLog}',
      labelPosition: FormField.LabelPosition.TOP,
      enabled: false,
      multilineText: true,
      gridDataHints: {
        h: 3
      }
    },
    {
      id: 'ClearEventLogButton',
      objectType: Button,
      label: '${textKey:ClearEventLog}',
      displayStyle: 3,
      processButton: false
    }
  ]
});

export type EventsTabWidgetMap = {
  'EventsOverviewField': LabelField;
  'EventLogField': StringField;
  'ClearEventLogButton': Button;
};
