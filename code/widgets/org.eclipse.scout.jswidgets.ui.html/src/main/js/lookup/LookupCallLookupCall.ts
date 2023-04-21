/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {arrays, LookupCall, LookupRow, StaticLookupCall} from '@eclipse-scout/core';
import {LocaleLookupCall, RainbowLookupCall, SalutationLookupCall, WorldLookupCall} from '../index';

export class LookupCallLookupCall extends StaticLookupCall<LookupCall<any>> {

  protected override _lookupRowByKey(key: LookupCall<any>): LookupRow<LookupCall<any>> {
    let data = arrays.find(this.data, data => key instanceof data[0]);
    if (!data) {
      return null;
    }
    return this._dataToLookupRow(data);
  }

  protected override _data(): any[] {
    return [
      [LocaleLookupCall, 'LocaleLookupCall'],
      [RainbowLookupCall, 'RainbowLookupCall'],
      [SalutationLookupCall, 'SalutationLookupCall'],
      [WorldLookupCall, 'WorldLookupCall']
    ];
  }
}
