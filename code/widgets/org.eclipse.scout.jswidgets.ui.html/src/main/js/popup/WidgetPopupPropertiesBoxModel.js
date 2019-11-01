export default function() {
  return {
    id: 'jswidgets.WidgetPopupPropertiesBox',
    objectType: 'GroupBox',
    gridColumnCount: 2,
    label: 'Widget Popup Properties',
    expandable: true,
    fields: [
      {
        id: 'ClosableField',
        objectType: 'CheckBoxField',
        label: 'Closable',
        labelVisible: false
      },
      {
        id: 'MovableField',
        objectType: 'CheckBoxField',
        label: 'Movable',
        labelVisible: false
      },
      {
        id: 'ResizableField',
        objectType: 'CheckBoxField',
        label: 'Resizable',
        labelVisible: false
      }
    ]
  };
}
