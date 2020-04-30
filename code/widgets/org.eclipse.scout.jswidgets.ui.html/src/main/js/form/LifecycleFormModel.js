export default () => ({
  id: 'jswidgets.LifecycleForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: 'GroupBox',
    menus: [
      {
        id: 'OkMenu',
        objectType: 'OkMenu',
        tooltipText: '${textKey:OkMenuTooltip}'
      },
      {
        id: 'CancelMenu',
        objectType: 'CancelMenu',
        tooltipText: '${textKey:CancelMenuTooltip}'
      },
      {
        id: 'SaveMenu',
        objectType: 'SaveMenu',
        tooltipText: '${textKey:SaveMenuTooltip}'
      },
      {
        id: 'ResetMenu',
        objectType: 'ResetMenu',
        tooltipText: '${textKey:ResetMenuTooltip}'
      },
      {
        id: 'CloseMenu',
        objectType: 'CloseMenu',
        tooltipText: '${textKey:CloseMenuTooltip}'
      }
    ],
    fields: [
      {
        id: 'DetailBox',
        objectType: 'GroupBox',
        gridColumnCount: 1,
        fields: [
          {
            id: 'NameField',
            objectType: 'StringField',
            label: 'Name'
          },
          {
            id: 'BirthdayField',
            objectType: 'DateField',
            label: 'Birthday'
          },
          {
            id: 'ExceptionField',
            objectType: 'CheckBoxField',
            label: '${textKey:ExceptionWhileSaving}'
          },
          {
            id: 'HasCloseButtonField',
            objectType: 'CheckBoxField',
            label: '${textKey:HasCloseButton}',
            tooltipText: '${textKey:HasCloseButtonTooltip}',
            value: true,
            gridDataHints: {
              fillHorizontal: false
            }
          },
          {
            id: 'AskIfNeedSaveField',
            objectType: 'CheckBoxField',
            label: '${textKey:AskToSaveChangesOnCancel}'
          }
        ]
      }
    ]
  }
});
