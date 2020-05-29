export default () => ({
  id: 'jswidgets.GroupBoxDeleteMenuBox',
  objectType: 'GroupBox',
  label: 'Delete menu',
  expandable: true,
  gridColumnCount: 2,
  fields: [
    {
      id: 'MenuToDeleteField',
      objectType: 'SmartField',
      label: 'Menubar Item'
    },
    {
      id: 'DeleteButton',
      objectType: 'Button',
      label: 'Delete',
      enabled: false,
      processButton: false,
      keyStroke: 'ctrl-delete'
    }
  ]
});
