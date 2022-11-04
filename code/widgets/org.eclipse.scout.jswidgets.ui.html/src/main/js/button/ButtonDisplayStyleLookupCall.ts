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
