/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {AggregateTableControl, BooleanColumn, Column, FormMenu, FormTableControl, icons, Menu, NumberColumn, PageModel, PageWithTable, SmartColumn, Table} from '@eclipse-scout/core';
import {LocaleLookupCall, MiniForm, SamplePageWithTableSearchForm, SamplePageWithTableSearchFormWidgetMap} from '../index';

export default (): PageModel => ({
  id: 'jswidgets.SamplePageWithTable',
  objectType: PageWithTable,
  text: 'Page with Table',
  detailTable: {
    id: 'jswidgets.SamplePageWithTable.Table',
    objectType: Table,
    columns: [
      {
        id: 'IdColumn',
        objectType: NumberColumn,
        displayable: false
      },
      {
        id: 'StringColumn',
        objectType: Column,
        text: 'String Column',
        width: 300,
        sortActive: true,
        sortIndex: 0
      },
      {
        id: 'SmartColumn',
        objectType: SmartColumn,
        lookupCall: LocaleLookupCall,
        text: 'Smart Column',
        width: 300
      },
      {
        id: 'NumberColumn',
        objectType: NumberColumn,
        text: 'Number Column',
        width: 300
      },
      {
        id: 'BooleanColumn',
        objectType: BooleanColumn,
        text: 'Boolean Column',
        width: 150
      }
    ],
    menus: [
      {
        id: 'FormMenu',
        objectType: FormMenu,
        text: 'Form menu',
        menuTypes: [
          'Table.EmptySpace', 'Table.SingleSelection'
        ],
        form: {
          objectType: MiniForm
        }
      },
      {
        id: 'AddRowMenu',
        objectType: Menu,
        text: '${textKey:AddRow}',
        menuTypes: [
          'Table.EmptySpace'
        ],
        keyStroke: 'insert'
      },
      {
        id: 'AddManyMenu',
        objectType: Menu,
        text: 'Add many',
        menuTypes: [
          'Table.EmptySpace'
        ]
      },
      {
        id: 'DeleteRowMenu',
        objectType: Menu,
        text: '${textKey:DeleteRow}',
        menuTypes: [
          'Table.SingleSelection',
          'Table.MultiSelection'
        ],
        keyStroke: 'delete'
      },
      {
        id: 'TileToggleMenu',
        objectType: Menu,
        iconId: icons.SQUARE_BOLD,
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
        objectType: FormTableControl,
        iconId: icons.SEARCH,
        form: {
          id: 'SearchForm',
          objectType: SamplePageWithTableSearchForm
        }
      },
      {
        id: 'AggregateTableControl',
        objectType: AggregateTableControl
      }
    ]
  }
});

export type SamplePageWithTableTableWidgetMap = {
  'FormMenu': FormMenu;
  'AddRowMenu': Menu;
  'AddManyMenu': Menu;
  'DeleteRowMenu': Menu;
  'TileToggleMenu': Menu;
  'SearchFormTableControl': FormTableControl;
  'SearchForm': SamplePageWithTableSearchForm;
  'AggregateTableControl': AggregateTableControl;
} & SamplePageWithTableSearchFormWidgetMap;

export type SamplePageWithTableTableColumnMap = {
  'IdColumn': NumberColumn;
  'StringColumn': Column;
  'SmartColumn': SmartColumn<string>;
  'NumberColumn': NumberColumn;
  'BooleanColumn': BooleanColumn;
};

export class SamplePageWithTableTable extends Table {
  declare widgetMap: SamplePageWithTableTableWidgetMap;
  declare columnMap: SamplePageWithTableTableColumnMap;
}
