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
