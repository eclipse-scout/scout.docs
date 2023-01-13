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
import {Chart, ChartType} from '@eclipse-scout/chart';

export class ChartTypeLookupCall extends StaticLookupCall<ChartType> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
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
