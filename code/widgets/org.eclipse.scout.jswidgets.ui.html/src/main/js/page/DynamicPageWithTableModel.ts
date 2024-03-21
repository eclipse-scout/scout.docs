/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Column, PageModel, PageWithTable, SmartColumn, Table} from '@eclipse-scout/core';
import {PageTypeLookupCall, PageTypeType} from '../index';

export default (): PageModel => ({
  objectType: PageWithTable,
  text: 'Dynamic Page with Table',
  detailTable: {
    objectType: Table,
    columns: [
      {
        id: 'NameColumn',
        objectType: Column,
        text: 'Name',
        width: 250
      },
      {
        id: 'PageTypeColumn',
        objectType: SmartColumn,
        text: 'Type',
        width: 250,
        lookupCall: PageTypeLookupCall
      }
    ]
  }
});

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export class DynamicPageWithTableTable extends Table {
  declare columnMap: DynamicPageWithTableTableColumnMap;
}

export type DynamicPageWithTableTableColumnMap = {
  'NameColumn': Column;
  'PageTypeColumn': SmartColumn<PageTypeType>; // defined by hand
};
