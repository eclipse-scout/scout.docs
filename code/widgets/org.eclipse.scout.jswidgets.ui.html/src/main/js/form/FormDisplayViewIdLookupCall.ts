/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {DisplayViewId, StaticLookupCall} from '@eclipse-scout/core';

export class FormDisplayViewIdLookupCall extends StaticLookupCall<DisplayViewId> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return FormDisplayViewIdLookupCall.DATA;
  }

  static DATA = [
    ['C', 'C'],
    ['N', 'N'],
    ['NE', 'NE'],
    ['E', 'E'],
    ['SE', 'SE'],
    ['S', 'S'],
    ['SW', 'SW'],
    ['W', 'W'],
    ['NW', 'NW']
  ];
}
