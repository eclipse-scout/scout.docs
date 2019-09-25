import {icons} from '@eclipse-scout/core';

export default function() {
  return {
  id: 'jswidgets.SamplePageWithTable',
  objectType: 'PageWithTable',
  leaf: true,
  text: 'Page with Table',
  detailTable: {
    id: 'jswidgets.SamplePageWithTable.Table',
    objectType: 'jswidgets.SamplePageWithTableDetailTable',
    columns: [
      {
        id: 'IdColumn',
        objectType: 'NumberColumn',
        visible: false
      },
      {
        id: 'StringColumn',
        objectType: 'Column',
        text: 'String Column',
        width: 300
      },
      {
        id: 'NumberColumn',
        objectType: 'NumberColumn',
        text: 'Number Column',
        width: 300
      },
      {
        id: 'BooleanColumn',
        objectType: 'BooleanColumn',
        text: 'Boolean Column',
        width: 150
      }
    ],
    menus: [
      {
        id: 'AddRowMenu',
        objectType: 'Menu',
        text: '${textKey:AddRow}',
        menuTypes: [
          'Table.EmptySpace'
        ],
        keyStroke: 'insert'
      },
      {
        id: 'AddManyMenu',
        objectType: 'Menu',
        text: 'Add many',
        menuTypes: [
          'Table.EmptySpace'
        ]
      },
      {
        id: 'DeleteRowMenu',
        objectType: 'Menu',
        text: '${textKey:DeleteRow}',
        menuTypes: [
          'Table.SingleSelection',
          'Table.MultiSelection'
        ],
        keyStroke: 'delete'
      },
      {
        id: 'TileToggleMenu',
        objectType: 'jswidgets.SamplePageWithTableTileToggleMenu',
        iconId: icons.LIGHTBULB_ON,
        stackable: false,
        horizontalAlignment: 1,
        menuTypes: [
          'Table.EmptySpace'
        ]
      }
    ],
    tableControls: [
      {
        id: 'SearchFormTableControl',
        objectType: 'FormTableControl',
        iconId: icons.SEARCH,
        form: {
          id: 'SearchForm',
          objectType: 'jswidgets.SamplePageWithTableSearchForm'
        }
      },
      {
        id: 'AggregateTableControl',
        objectType: 'AggregateTableControl'
      }
    ]
  }
};
}
