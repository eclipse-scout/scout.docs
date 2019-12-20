export default function() {
  return {
    type: 'extension',
    id: 'IntegerFieldForm',
    extensions: [
      {
        operation: 'appendTo',
        target: {id: 'NumberField'},
        extension: {
          objectType: 'IntegerField',
          label: '${textKey:IntegerField}'
        }
      },
      {
        operation: 'appendTo',
        target: {id: 'CalculatorField'},
        extension: {
          objectType: 'IntegerField'
        }
      },
      {
        operation: 'appendTo',
        target: {id: 'MinValueField'},
        extension: {
          objectType: 'IntegerField'
        }
      },
      {
        operation: 'appendTo',
        target: {id: 'MaxValueField'},
        extension: {
          objectType: 'IntegerField'
        }
      }
    ]
  }
}
