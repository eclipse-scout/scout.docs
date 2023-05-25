/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {arrays, LookupRow, StaticLookupCall} from '@eclipse-scout/core';

export class ObjectTypeLookupCall<TKey extends object = object> extends StaticLookupCall<object> {

  protected override _lookupRowByKey(key: object): LookupRow<object> {
    // Key maybe a class or an instance of a class
    let data = arrays.find(this.data, data => key === data[0] || key?.constructor === data[0]);
    if (!data) {
      return null;
    }
    return this._dataToLookupRow(data);
  }
}
