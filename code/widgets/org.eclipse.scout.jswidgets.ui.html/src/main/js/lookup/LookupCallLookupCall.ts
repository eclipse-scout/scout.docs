/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {arrays, LookupCall, LookupResult, LookupRow, QueryBy, scout, StaticLookupCall} from '@eclipse-scout/core';
import {LocaleLookupCall, RainbowLookupCall, SalutationLookupCall, WorldLookupCall} from '../index';
import Deferred = JQuery.Deferred;

export class LookupCallLookupCall extends StaticLookupCall<LookupCall<any>> {

  constructor() {
    super();
  }

  protected override _queryByKey(deferred: Deferred<LookupResult<LookupCall<any>>>, key: LookupCall<any>) {
    if (key instanceof LookupCall) {
      deferred.resolve({
        queryBy: QueryBy.KEY,
        lookupRows: [{
          key: key,
          text: key.objectType as string,
          enabled: true
        } as LookupRow<LookupCall<any>>]
      });
      return;
    }
    let data = arrays.find(this.data, data => {
      return data[0] === key;
    });
    if (data) {
      deferred.resolve({
        queryBy: QueryBy.KEY,
        lookupRows: [this._dataToLookupRow(data)]
      });
    } else {
      deferred.reject();
    }
  }

  protected override _data(): any[] {
    return [
      [scout.create(LocaleLookupCall, {session: this.session}), 'jswidgets.LocaleLookupCall'],
      [scout.create(RainbowLookupCall, {session: this.session}), 'jswidgets.RainbowLookupCall'],
      [scout.create(SalutationLookupCall, {session: this.session}), 'jswidgets.SalutationLookupCall'],
      [scout.create(WorldLookupCall, {session: this.session}), 'jswidgets.WorldLookupCall']
    ];
  }
}
