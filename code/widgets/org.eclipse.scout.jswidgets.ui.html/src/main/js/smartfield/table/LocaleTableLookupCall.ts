/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {LookupRow, scout} from '@eclipse-scout/core';
import {LocaleLookupCall} from '../../index';

export class LocaleTableLookupCall extends LocaleLookupCall {

  constructor() {
    super();
  }

  protected override _dataToLookupRow(data: any[]): LookupRow<string> {
    return scout.create(LookupRow, {
      key: data[0],
      text: data[1],
      additionalTableRowData: {
        tag: data[0]
      }
    }) as LookupRow<string>;
  }
}
