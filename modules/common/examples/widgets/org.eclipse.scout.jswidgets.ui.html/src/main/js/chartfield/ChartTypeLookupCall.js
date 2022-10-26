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

export default class ChartTypeLookupCall extends StaticLookupCall {

  constructor() {
    super();
  }

  _data() {
    return ChartTypeLookupCall.DATA;
  }

  static DATA = [
    [Chart.Type.BAR, 'Bar'],
    [Chart.Type.BAR_HORIZONTAL, 'Bar Horizontal'],
    [Chart.Type.BUBBLE, 'Bubble'],
    [Chart.Type.COMBO_BAR_LINE, 'Combo Bar Line'],
    [Chart.Type.DOUGHNUT, 'Doughnut'],
    [Chart.Type.FULFILLMENT, 'Fulfillment'],
    [Chart.Type.LINE, 'Line'],
    [Chart.Type.PIE, 'Pie'],
    [Chart.Type.POLAR_AREA, 'Polar area'],
    [Chart.Type.RADAR, 'Radar'],
    [Chart.Type.SALESFUNNEL, 'Salesfunnel'],
    [Chart.Type.SCATTER, 'Scatter'],
    [Chart.Type.SPEEDO, 'Speedo'],
    [Chart.Type.VENN, 'Venn']
  ];
}
