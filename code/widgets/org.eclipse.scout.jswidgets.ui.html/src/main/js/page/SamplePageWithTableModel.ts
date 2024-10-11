/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {AggregateTableControl, BooleanColumn, Column, FormMenu, FormTableControl, icons, Menu, NumberColumn, PageModel, PageWithTable, SmartColumn, Table} from '@eclipse-scout/core';
import {LocaleLookupCall, MiniForm, SamplePageWithTableSearchForm, SamplePageWithTableSearchFormWidgetMap} from '../index';
import {ChartTableControl} from '@eclipse-scout/chart';

export default (): PageModel => ({
  id: 'jswidgets.SamplePageWithTable',
  objectType: PageWithTable,
  text: 'Page with Table',
  detailTable: {
    id: 'jswidgets.SamplePageWithTable.Table',
    objectType: Table,
    maxRowCount: 5,
    columns: [
      {
        id: 'IdColumn',
        objectType: NumberColumn,
        displayable: false,
        primaryKey: true
      },
      {
        id: 'StringColumn',
        objectType: Column,
        text: 'String Column',
        width: 200,
        sortActive: true,
        sortIndex: 0
      },
      {
        id: 'SmartColumn',
        objectType: SmartColumn,
        lookupCall: LocaleLookupCall,
        text: 'Smart Column',
        width: 200
      },
      {
        id: 'NumberColumn',
        objectType: NumberColumn,
        text: 'Number Column',
        width: 150
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
          Table.MenuType.EmptySpace, Table.MenuType.SingleSelection
        ],
        form: {
          objectType: MiniForm
        }
      },
      {
        id: 'AddRowMenu',
        objectType: Menu,
        text: '${textKey:AddRow}',
        keyStroke: 'insert'
      },
      {
        id: 'AddManyMenu',
        objectType: Menu,
        text: 'Add many'
      },
      {
        id: 'DeleteRowMenu',
        objectType: Menu,
        text: '${textKey:DeleteRow}',
        menuTypes: [
          Table.MenuType.SingleSelection,
          Table.MenuType.MultiSelection
        ],
        keyStroke: 'delete'
      },
      {
        id: 'TileToggleMenu',
        objectType: Menu,
        iconId: icons.SQUARE_BOLD,
        stackable: false,
        horizontalAlignment: 1
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
        id: 'ChartTableControl',
        objectType: ChartTableControl
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
