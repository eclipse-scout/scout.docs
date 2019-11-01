export default function() {
  return {
  id: 'jswidgets.GroupBoxDeleteFieldBox',
  objectType: 'GroupBox',
  label: 'Delete field',
  expandable: true,
  gridColumnCount: 2,
  fields: [
    {
      id: 'ToDeleteField',
      objectType: 'SmartField',
      label: 'Field'
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
};
}
