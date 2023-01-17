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

export class MenuBarItemTypeLookupCall extends StaticLookupCall<'Button' | 'Menu'> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return MenuBarItemTypeLookupCall.DATA;
  }

  static DATA = [
    ['Button', 'Button'],
    ['Menu', 'Menu']
  ];
}
