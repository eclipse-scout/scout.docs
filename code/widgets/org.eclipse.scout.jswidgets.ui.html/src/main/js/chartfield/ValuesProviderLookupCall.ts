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

export class ValuesProviderLookupCall extends StaticLookupCall<number> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return ValuesProviderLookupCall.DATA;
  }

  static Type = {
    VALUE_PROVIDER_RANDOM: 1,
    VALUE_PROVIDER_RANDOM_POSITIVE: 2,
    VALUE_PROVIDER_ALL_0: 3,
    VALUE_PROVIDER_ALL_1: 4,
    VALUE_PROVIDER_ALL_50000: 5,
    VALUE_PROVIDER_RANDOM_500000: 6
  };

  static DATA = [
    [ValuesProviderLookupCall.Type.VALUE_PROVIDER_RANDOM, 'Random'],
    [ValuesProviderLookupCall.Type.VALUE_PROVIDER_RANDOM_POSITIVE, 'Random (only positive)'],
    [ValuesProviderLookupCall.Type.VALUE_PROVIDER_ALL_0, 'All 0'],
    [ValuesProviderLookupCall.Type.VALUE_PROVIDER_ALL_1, 'All 1'],
    [ValuesProviderLookupCall.Type.VALUE_PROVIDER_ALL_50000, 'All 50\'000'],
    [ValuesProviderLookupCall.Type.VALUE_PROVIDER_RANDOM_500000, 'Random -500\'000 to 500\'000']
  ];
}
