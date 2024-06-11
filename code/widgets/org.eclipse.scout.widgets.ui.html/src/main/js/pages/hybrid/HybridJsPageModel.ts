/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

import {PageModel} from '@eclipse-scout/core';

export default (): PageModel => ({
  id: 'HybridJsPage',
  leaf: true,
  text: '<span class="hybrid-type-indicator"><span class="text">Hybrid Page</span><span class="type">JS</span></span>',
  uuid: 'c2caf895-9554-4f39-bff3-ed70822caf9c',
  htmlEnabled: true
});
