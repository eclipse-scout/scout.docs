/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {StaticLookupCall, TabBox, TabBoxMarkStrategy} from '@eclipse-scout/core';

export class TabBoxMarkStrategyLookupCall extends StaticLookupCall<TabBoxMarkStrategy> {

  protected override _data(): any[] {
    return [
      [TabBox.MarkStrategy.NOT_EMPTY, 'Not Empty'],
      [TabBox.MarkStrategy.SAVE_NEEDED, 'Save Needed']
    ];
  }
}
