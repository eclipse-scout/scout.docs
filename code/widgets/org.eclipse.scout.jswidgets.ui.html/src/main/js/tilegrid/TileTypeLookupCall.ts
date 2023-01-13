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

export class TileTypeLookupCall extends StaticLookupCall<'default' | 'simple'> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return TileTypeLookupCall.DATA;
  }

  static DATA = [
    ['default', 'HtmlTile with default style'],
    ['simple', 'CustomTile with custom style']
  ];
}
