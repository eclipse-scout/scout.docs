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

export class PopupHorizontalAlignLookupCall extends StaticLookupCall<PopupAlignment> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return PopupHorizontalAlignLookupCall.DATA;
  }

  static DATA = [
    [Popup.Alignment.LEFT, 'left'],
    [Popup.Alignment.LEFTEDGE, 'leftedge'],
    [Popup.Alignment.CENTER, 'center'],
    [Popup.Alignment.RIGHT, 'right'],
    [Popup.Alignment.RIGHTEDGE, 'rightedge']
  ];
}
