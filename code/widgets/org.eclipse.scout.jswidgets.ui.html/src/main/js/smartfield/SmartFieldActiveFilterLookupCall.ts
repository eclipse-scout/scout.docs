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
