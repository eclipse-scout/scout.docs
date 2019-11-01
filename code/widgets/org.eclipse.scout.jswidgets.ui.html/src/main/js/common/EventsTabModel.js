import {FormField} from '@eclipse-scout/core';

export default function() {
  return {
    id: 'jswidgets.EventsTab',
    objectType: 'TabItem',
    label: 'Events',
    gridColumnCount: 1,
    fields: [
      {
        id: 'EventsOverviewField',
        objectType: 'LabelField',
        labelVisible: false,
        htmlEnabled: true,
        wrapText: true,
        gridDataHints: {
          useUiHeight: true
        }
      },
      {
        id: 'EventLogField',
        objectType: 'StringField',
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
        objectType: 'Button',
        label: '${textKey:ClearEventLog}',
        displayStyle: 3,
        processButton: false
      }
    ]
  };
}
