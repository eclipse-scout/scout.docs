/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Menu, objects, StaticLookupCall, SubMenuVisibility} from '@eclipse-scout/core';

export class SubMenuVisibilityLookupCall extends StaticLookupCall<SubMenuVisibility> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return SubMenuVisibilityLookupCall.DATA;
  }

  static DATA = objects.values(Menu.SubMenuVisibility).map(value => [value, value]);
}
