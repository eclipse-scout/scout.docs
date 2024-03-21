/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {LookupRow, StaticLookupCall} from '@eclipse-scout/core';

export class PageTypeLookupCall extends StaticLookupCall<PageTypeType> {

  includeInactive: boolean;

  protected override _data(): any[] {
    let data = [
      {type: 'PageWithNodes', label: 'Page with Nodes', active: true},
      {type: 'PageWithTable', label: 'Page with Table', active: true},
      {type: 'None', label: 'None', active: false}
    ] as PageTypeLookupCallData[];
    return data.map(d => [d.type, d.label, null, d.active]);
  }

  protected override _dataToLookupRow(data: any[], index?: number): LookupRow<PageTypeType> {
    let lookupRow = super._dataToLookupRow(data, index);
    lookupRow.active = this.includeInactive || data[3];
    return lookupRow;
  }
}

export type PageTypeType = 'PageWithNodes' | 'PageWithTable' | 'None';

export interface PageTypeLookupCallData {
  type: PageTypeType;
  label: string;
  active: boolean;
}
