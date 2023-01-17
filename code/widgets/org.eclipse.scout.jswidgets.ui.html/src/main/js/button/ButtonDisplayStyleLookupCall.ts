/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, ButtonDisplayStyle, StaticLookupCall} from '@eclipse-scout/core';

export class ButtonDisplayStyleLookupCall extends StaticLookupCall<ButtonDisplayStyle> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return ButtonDisplayStyleLookupCall.DATA;
  }

  static DATA = [
    [Button.DisplayStyle.DEFAULT, 'default'],
    [Button.DisplayStyle.TOGGLE, 'toggle'],
    [Button.DisplayStyle.LINK, 'link'],
    [Button.DisplayStyle.BORDERLESS, 'borderless']
  ];
}
