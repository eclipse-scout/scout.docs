/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {LookupRow, StaticLookupCall} from '@eclipse-scout/core';

export class SalutationLookupCall extends StaticLookupCall<string> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
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
  protected override _dataToLookupRow(data: any[]): LookupRow<string> {
    let lookupRow = super._dataToLookupRow(data);
    lookupRow.cssClass = lookupRow.key;
    return lookupRow;
  }
}
