/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {StaticLookupCall, Switch, SwitchDisplayStyle} from '@eclipse-scout/core';

export class SwitchDisplayStyleLookupCall extends StaticLookupCall<SwitchDisplayStyle> {

  protected override _data(): any[] {
    return SwitchDisplayStyleLookupCall.DATA;
  }

  static DATA = [
    [Switch.DisplayStyle.DEFAULT, 'default'],
    [Switch.DisplayStyle.SLIDER, 'slider']
  ];
}
