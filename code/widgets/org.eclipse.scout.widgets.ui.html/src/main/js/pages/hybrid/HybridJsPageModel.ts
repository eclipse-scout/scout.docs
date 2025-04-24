/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
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
  uuid: 'b03fa823-7164-4e29-a176-cf0c0f5e241a',
  htmlEnabled: true
});
