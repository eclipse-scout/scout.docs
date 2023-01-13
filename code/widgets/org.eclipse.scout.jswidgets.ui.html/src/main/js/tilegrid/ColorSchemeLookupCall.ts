/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {colorSchemes, StaticLookupCall} from '@eclipse-scout/core';

export class ColorSchemeLookupCall extends StaticLookupCall<string> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return ColorSchemeLookupCall.DATA;
  }

  static DATA = [
    [colorSchemes.ColorSchemeId.DEFAULT, 'default'],
    [colorSchemes.ColorSchemeId.DEFAULT + '-inverted', 'default-inverted'],
    [colorSchemes.ColorSchemeId.ALTERNATIVE, 'alternative'],
    [colorSchemes.ColorSchemeId.ALTERNATIVE + '-inverted', 'alternative-inverted'],
    [colorSchemes.ColorSchemeId.RAINBOW, 'rainbow']
  ];
}
