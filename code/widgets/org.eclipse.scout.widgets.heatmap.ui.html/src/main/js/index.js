/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {ObjectFactory} from '@eclipse-scout/core';
import 'leaflet';
import 'leaflet.heat';

import * as self from './index.js';

export * from './heatmap/HeatmapField';
export * from './heatmap/HeatmapFieldAdapter';
export * from './heatmap/HeatmapFieldLayout';

export default self;
ObjectFactory.get().registerNamespace('widgets', self);
