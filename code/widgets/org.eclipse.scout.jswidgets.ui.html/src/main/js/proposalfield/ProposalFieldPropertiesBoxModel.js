export default function() {
  return {
  id: 'jswidgets.ProposalFieldPropertiesBox',
  type: 'extension',
  extensions: [
    {
      operation: 'insert',
      target: {
        id: 'jswidgets.SmartFieldPropertiesBox',
        property: 'fields',
        before: 'ActiveFilterEnabledField'
      },
      extension: [
        {
          id: 'MaxLengthField',
          objectType: 'NumberField',
          label: 'Max Length'
        },
        {
          id: 'TrimTextField',
          objectType: 'CheckBoxField',
          label: 'Trim Text'
        }
      ]
    }
  ]
};
}
