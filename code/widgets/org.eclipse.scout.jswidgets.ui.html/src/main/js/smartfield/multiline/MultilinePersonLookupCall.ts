/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {StaticLookupCall} from '@eclipse-scout/core';

export class MultilinePersonLookupCall extends StaticLookupCall<number> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return MultilinePersonLookupCall.DATA;
  }

  static DATA = [
    [1, 'Rachel Olson\nEcuador'],
    [2, 'Stephanie Simmons\nBrazil'],
    [3, 'Larry Hudson\nJapan'],
    [4, 'Phyllis Chapman\nRussia'],
    [5, 'Melissa Nichols\nIndonesia']
  ];
}
