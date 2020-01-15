/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {SmartField, StaticLookupCall} from '@eclipse-scout/core';

export default class SmartFieldActiveFilterLookupCall extends StaticLookupCall {

  constructor() {
    super();
  }

  // Cannot use static DATA because we need access to this.session for texts
  _data() {
    return [
      [SmartField.ACTIVE_FILTER_VALUES[0], this.session.text('ui.All')],
      [SmartField.ACTIVE_FILTER_VALUES[1], this.session.text('ui.Inactive')],
      [SmartField.ACTIVE_FILTER_VALUES[2], this.session.text('ui.Active')]
    ];
  }

}
