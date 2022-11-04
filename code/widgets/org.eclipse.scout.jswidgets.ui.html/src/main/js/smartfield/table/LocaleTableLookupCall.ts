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
