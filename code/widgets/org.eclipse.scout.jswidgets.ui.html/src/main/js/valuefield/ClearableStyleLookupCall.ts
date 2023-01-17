/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {StaticLookupCall, ValueField, ValueFieldClearable} from '@eclipse-scout/core';

export class ClearableStyleLookupCall extends StaticLookupCall<ValueFieldClearable> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return ClearableStyleLookupCall.DATA;
  }

  static DATA = [
    [ValueField.Clearable.FOCUSED, 'Focused'],
    [ValueField.Clearable.ALWAYS, 'Always'],
    [ValueField.Clearable.NEVER, 'Never']
  ];
}
