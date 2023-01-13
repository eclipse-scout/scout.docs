/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {SmartField, SmartFieldDisplayStyle, StaticLookupCall} from '@eclipse-scout/core';

export class SmartFieldDisplayStyleLookupCall extends StaticLookupCall<SmartFieldDisplayStyle> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return SmartFieldDisplayStyleLookupCall.DATA;
  }

  static DATA = [
    [SmartField.DisplayStyle.DEFAULT, 'default'],
    [SmartField.DisplayStyle.DROPDOWN, 'dropdown']
  ];
}
