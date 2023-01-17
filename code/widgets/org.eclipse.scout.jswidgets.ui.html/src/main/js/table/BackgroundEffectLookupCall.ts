/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {NumberColumnBackgroundEffect, StaticLookupCall} from '@eclipse-scout/core';

export class BackgroundEffectLookupCall extends StaticLookupCall<NumberColumnBackgroundEffect> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return BackgroundEffectLookupCall.DATA;
  }

  static DATA = [
    ['colorGradient1', 'colorGradient1'],
    ['colorGradient2', 'colorGradient2'],
    ['barChart', 'barChart']
  ];
}
