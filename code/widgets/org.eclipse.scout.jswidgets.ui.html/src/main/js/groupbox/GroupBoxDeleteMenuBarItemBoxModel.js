export default () => ({
  id: 'jswidgets.GroupBoxDeleteMenuBarItemBox',
  objectType: 'GroupBox',
  label: 'Delete menubar item',
  expandable: true,
  gridColumnCount: 2,
  fields: [
    {
      id: 'ToDeleteMenuBarItem',
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
