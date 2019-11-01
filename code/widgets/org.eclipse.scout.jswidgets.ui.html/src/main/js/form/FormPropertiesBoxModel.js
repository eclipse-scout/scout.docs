export default function() {
  return {
    id: 'jswidgets.FormPropertiesBox',
    objectType: 'GroupBox',
    label: 'Properties',
    labelVisible: false,
    borderVisible: false,
    fields: [
      {
        id: 'titleField',
        objectType: 'StringField',
        label: 'Title'
      },
      {
        id: 'subTitleField',
        objectType: 'StringField',
        label: 'Sub Title'
      },
      {
        id: 'iconIdField',
        objectType: 'SmartField',
        lookupCall: 'jswidgets.IconIdLookupCall',
        label: 'Icon Id'
      },
      {
        id: 'displayHintField',
        objectType: 'SmartField',
        lookupCall: 'jswidgets.FormDisplayHintLookupCall',
        label: 'Display Hint'
      },
      {
        id: 'displayParentField',
        objectType: 'SmartField',
        lookupCall: 'jswidgets.DisplayParentLookupCall',
        label: 'Display Parent'
      },
      {
        id: 'askIfNeedSaveField',
        objectType: 'CheckBoxField',
        label: 'Ask If Need Save',
        labelVisible: false
      },
      {
        id: 'cacheBoundsField',
        objectType: 'CheckBoxField',
        label: 'Cache Bounds',
        labelVisible: false
      },
      {
        id: 'closableField',
        objectType: 'CheckBoxField',
        label: 'Closable',
        labelVisible: false
      },
      {
        id: 'resizableField',
        objectType: 'CheckBoxField',
        label: 'Resizable',
        labelVisible: false
      },
      {
        id: 'modalField',
        objectType: 'CheckBoxField',
        label: 'Modal',
        labelVisible: false
      }
    ]
  };
}
