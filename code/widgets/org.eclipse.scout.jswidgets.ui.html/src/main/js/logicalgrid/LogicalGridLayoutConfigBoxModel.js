export default function() {
  return {
  id: 'jswidgets.LogicalGridLayoutConfigBox',
  objectType: 'GroupBox',
  gridColumnCount: 2,
  label: 'Layout Config',
  expandable: true,
  fields: [
  {
      id: 'HGapField',
      objectType: 'NumberField',
      label: 'H Gap'
    },
    {
      id: 'VGapField',
      objectType: 'NumberField',
      label: 'V Gap'
    },
    {
      id: 'ColumnWidthField',
      objectType: 'NumberField',
      label: 'Column Width'
    },
    {
      id: 'RowHeightField',
      objectType: 'NumberField',
      label: 'Row Height'
    },
    {
      id: 'MinWidthField',
      objectType: 'NumberField',
      label: 'Min Width'
    }
  ]
};
}
