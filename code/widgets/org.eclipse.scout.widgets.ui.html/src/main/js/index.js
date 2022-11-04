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
import {ObjectFactory} from '@eclipse-scout/core';

export * from './ExampleBeanField';

export * from './ExampleBeanColumn';
export * from './WidgetsOutline';
export * from './WidgetsOutlineAdapter';
export * from './WidgetsTileOutlineOverview';
export * from './tile/CustomTile';
export * from './tile/CustomTileAdapter';

import * as self from './index.js';

export default self;
ObjectFactory.get().registerNamespace('widgets', self);
