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
import {StaticLookupCall} from '@eclipse-scout/core';
import {Chart} from '@eclipse-scout/chart';

export default class LegendPositionLookupCall extends StaticLookupCall {

  constructor() {
    super();
  }

  _data() {
    return LegendPositionLookupCall.DATA;
  }

  static DATA = [
    [Chart.Position.RIGHT, 'Right'],
    [Chart.Position.LEFT, 'Left'],
    [Chart.Position.TOP, 'Top'],
    [Chart.Position.BOTTOM, 'Bottom']
  ];
}
