/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {StaticLookupCall} from '@eclipse-scout/core';

export default class SalutationLookupCall extends StaticLookupCall {

  constructor() {
    super();
  }

  _data() {
    return SalutationLookupCall.DATA;
  }

  static DATA = [
    ['female', 'Weiblich'],
    ['male', 'Männlich'],
    ['impersonal', 'Unpersönlich'],
    ['unknown', 'Unbekannt']
  ];

  /**
   * Use the key property as cssClass. Used to demonstrate styling possibilities with lookup-rows in smart-fields.
   */
  _dataToLookupRow(data) {
    var lookupRow = super._dataToLookupRow(data);
    lookupRow.cssClass = lookupRow.key;
    return lookupRow;
  }
}
