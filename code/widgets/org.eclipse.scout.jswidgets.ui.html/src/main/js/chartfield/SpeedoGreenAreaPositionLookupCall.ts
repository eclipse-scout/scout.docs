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
import {GreenAreaPosition, SpeedoChartRenderer} from '@eclipse-scout/chart';

export class SpeedoGreenAreaPositionLookupCall extends StaticLookupCall<GreenAreaPosition> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return SpeedoGreenAreaPositionLookupCall.DATA;
  }

  static DATA = [
    [SpeedoChartRenderer.Position.LEFT, 'Left'],
    [SpeedoChartRenderer.Position.CENTER, 'Center'],
    [SpeedoChartRenderer.Position.RIGHT, 'Right']
  ];
}
