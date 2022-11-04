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
