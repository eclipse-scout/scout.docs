export default function() {
  return {
    id: 'jswidgets.TabBoxProperties',
    objectType: 'TabItem',
    label: 'Tab Box Properties',
    fields: [
      {
        id: 'TabBoxProperties.SelectedTabField',
        objectType: 'SmartField',
        label: 'Selected Tab Item'
      },
      {
        id: 'TabBoxProperties.TabAreaStyleField',
        objectType: 'SmartField',
        lookupCall: 'jswidgets.TabAreaStyleLookupCall',
        label: 'Tab Area Style'
      },
      {
        id: 'TabBoxProperties.ShowMenus',
        objectType: 'CheckBoxField',
        label: 'Show Menus'
      },
      {
        id: 'TabBoxProperties.FormFieldPropertiesBox',
        objectType: 'jswidgets.FormFieldPropertiesBox'
      },
      {
        id: 'TabBoxProperties.GridDataBox',
        objectType: 'jswidgets.GridDataBox',
        label: 'Grid Data Hints'
      }
    ]
  };
}
