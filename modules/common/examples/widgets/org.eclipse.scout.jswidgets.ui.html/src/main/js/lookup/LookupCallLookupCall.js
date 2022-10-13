/*
 * Copyright (c) 2014-2018 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {arrays, LookupCall, QueryBy, scout, StaticLookupCall} from '@eclipse-scout/core';

export default class LookupCallLookupCall extends StaticLookupCall {

  constructor() {
    super();
  }

  _queryByKey(deferred, key) {
    if (key instanceof LookupCall) {
      deferred.resolve({
        queryBy: QueryBy.KEY,
        lookupRows: [{
          key: key,
          text: key.objectType,
          enabled: true
        }]
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

  _data() {
    return [
      [scout.create('jswidgets.LocaleLookupCall', {session: this.session}), 'jswidgets.LocaleLookupCall'],
      [scout.create('jswidgets.RainbowLookupCall', {session: this.session}), 'jswidgets.RainbowLookupCall'],
      [scout.create('jswidgets.SalutationLookupCall', {session: this.session}), 'jswidgets.SalutationLookupCall'],
      [scout.create('jswidgets.WorldLookupCall', {session: this.session}), 'jswidgets.WorldLookupCall']
    ];
  }
}
