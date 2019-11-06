/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {StaticLookupCall} from '@eclipse-scout/core';
import {Chart} from '../../index';

export default class ChartTypeLookupCall extends StaticLookupCall {

  constructor() {
    super();
  }

  _data() {
    return ChartTypeLookupCall.DATA;
  }

  static DATA = [
    [Chart.Type.PIE, 'Pie'],
    [Chart.Type.LINE, 'Line'],
    [Chart.Type.DOUGHNUT, 'Doughnut'],
    [Chart.Type.POLAR_AREA, 'Polar Area']

  ];
}
