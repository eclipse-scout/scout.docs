/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {SmartField, SmartFieldActiveFilter, StaticLookupCall} from '@eclipse-scout/core';

export class SmartFieldActiveFilterLookupCall extends StaticLookupCall<SmartFieldActiveFilter> {

  constructor() {
    super();
  }

  // Cannot use static DATA because we need access to this.session for texts
  protected override _data(): any[] {
    return [
      [SmartField.ACTIVE_FILTER_VALUES[0], this.session.text('ui.All')],
      [SmartField.ACTIVE_FILTER_VALUES[1], this.session.text('ui.Inactive')],
      [SmartField.ACTIVE_FILTER_VALUES[2], this.session.text('ui.Active')]
    ];
  }
}
