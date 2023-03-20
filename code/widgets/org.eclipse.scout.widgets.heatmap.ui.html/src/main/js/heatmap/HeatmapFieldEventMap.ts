/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Event, FormFieldEventMap} from '@eclipse-scout/core';
import {HeatmapField, MapPoint} from '../index';

export interface HeatmapFieldClickEvent<TSource = HeatmapField> extends Event<TSource> {
  point: MapPoint;
}

export interface HeatmapFieldViewParameterChangeEvent<TSource = HeatmapField> extends Event<TSource> {
  center: MapPoint;
  zoomFactor: number;
}

export interface HeatmapFieldEventMap extends FormFieldEventMap {
  'click': HeatmapFieldClickEvent;
  'viewParameterChange': HeatmapFieldViewParameterChangeEvent;
}
