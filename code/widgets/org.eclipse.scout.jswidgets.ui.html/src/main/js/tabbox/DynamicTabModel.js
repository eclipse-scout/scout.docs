export default function() {
  return {
    id: 'jswidgets.DynamicTab',
    objectType: 'TabItem',
    label: 'Dyn Tab',
    fields: [
      {
        id: 'label',
        objectType: 'LabelField',
        labelVisible: false
      },
      {
        id: 'StringField',
        objectType: 'StringField',
        label: 'Sample Field'
      }
    ]
  };
}
