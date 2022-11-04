/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
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
