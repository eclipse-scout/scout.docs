import {Action} from '@eclipse-scout/core';

export default () => ({
  id: 'jswidgets.SamplePageWithTableSearchForm',
  rootGroupBox: {
    id: 'MainBox',
    objectType: 'GroupBox',
    fields: [
      {
        id: 'DetailBox',
        objectType: 'GroupBox',
        gridColumnCount: 2,
        fields: [
          {
            id: 'StringField',
            objectType: 'StringField',
            maxLength: 200,
            label: 'String Column'
          }
        ]
      }
    ],
    menus: [
      {
        id: 'SearchButton',
        objectType: 'Menu',
        actionStyle: Action.ActionStyle.BUTTON,
        text: '${textKey:Search}',
        keyStroke: 'ENTER'
      },
      {
        id: 'ResetMenu',
        objectType: 'ResetMenu'
      }
    ]
  }
});
