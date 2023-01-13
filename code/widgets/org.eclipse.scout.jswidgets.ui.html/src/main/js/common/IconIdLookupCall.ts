/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {icons, LookupRow, StaticLookupCall, strings} from '@eclipse-scout/core';

export class IconIdLookupCall extends StaticLookupCall<string> {

  protected override _data(): any[] {
    return Object.keys(icons)
      .filter(name => {
        let value = icons[name];
        return typeof value === 'string' && strings.startsWith(value, 'font:');
      })
      .sort((name1, name2) => strings.nvl(name1).localeCompare(strings.nvl(name2)))
      .map(name => {
        let iconId = icons[name];
        return [iconId, name];
      });
  }

  protected override _dataToLookupRow(data: any[], index?: number): LookupRow<string> {
    let lookupRow = super._dataToLookupRow(data);
    if (lookupRow && lookupRow.key) {
      lookupRow.iconId = lookupRow.key;
    }
    return lookupRow;
  }
}
