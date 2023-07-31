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
import * as self from './index';

export * from './ExampleBeanField';

export * from './ExampleBeanColumn';
export * from './ExampleBean';
export * from './WidgetsOutline';
export * from './WidgetsOutlineAdapter';
export * from './WidgetsTileOutlineOverview';
export * from './forms/hybrid/HybridJsForm';
export * from './forms/switch/SwitchDisplayStyleLookupCall';
export * from './forms/switch/SwitchJsForm';
export * from './forms/switch/SwitchJsFormModel';
export * from './tile/CustomTile';
export * from './tile/CustomTileAdapter';

export default self;
ObjectFactory.get().registerNamespace('widgets', self);
