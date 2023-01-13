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

export class ImageLookupCall extends StaticLookupCall<string> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return ImageLookupCall.DATA;
  }

  static DATA = [
    ['img/eclipse_scout_logo.png', 'img/eclipse_scout_logo.png'],
    ['img/fish.jpg', 'img/fish.jpg'],
    ['img/bird.jpg', 'img/bird.jpg']
  ];
}
