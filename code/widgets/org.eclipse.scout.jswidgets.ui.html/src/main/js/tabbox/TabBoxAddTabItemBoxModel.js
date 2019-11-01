export default function() {
  return {
    id: 'jswidgets.TabBoxAddTabItemBox',
    objectType: 'GroupBox',
    label: 'Add Tab Item',
    expandable: true,
    gridColumnCount: 2,
    fields: [
      {
        id: 'AddTabItem.Label',
        objectType: 'StringField',
        label: 'Label'
      },
      {
        id: 'AddTabItem.SubLabel',
        objectType: 'StringField',
        label: 'Sub Label'
      },
      {
        id: 'AddTabItem.TabItemSmartField',
        objectType: 'SmartField',
        label: 'Before'
      },
      {
        id: 'AddTabItem.CreateButton',
        objectType: 'Button',
        label: 'Add',
        processButton: false,
        keyStroke: 'ctrl-insert'
      }
    ]
  };
}
