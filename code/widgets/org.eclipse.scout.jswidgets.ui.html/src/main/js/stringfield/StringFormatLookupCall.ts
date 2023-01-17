/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {StaticLookupCall, StringField, StringFieldFormat} from '@eclipse-scout/core';

export class StringFormatLookupCall extends StaticLookupCall<StringFieldFormat> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return StringFormatLookupCall.DATA;
  }

  static DATA = [
    [StringField.Format.UPPER, 'Upper Case'],
    [StringField.Format.LOWER, 'Lower Case']
  ];
}
