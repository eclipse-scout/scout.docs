/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Popup, PopupAlignment, StaticLookupCall} from '@eclipse-scout/core';

export class PopupVerticalAlignLookupCall extends StaticLookupCall<PopupAlignment> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return PopupVerticalAlignLookupCall.DATA;
  }

  static DATA = [
    [Popup.Alignment.TOP, 'top'],
    [Popup.Alignment.TOPEDGE, 'topedge'],
    [Popup.Alignment.CENTER, 'center'],
    [Popup.Alignment.BOTTOM, 'bottom'],
    [Popup.Alignment.BOTTOMEDGE, 'bottomedge']
  ];
}
