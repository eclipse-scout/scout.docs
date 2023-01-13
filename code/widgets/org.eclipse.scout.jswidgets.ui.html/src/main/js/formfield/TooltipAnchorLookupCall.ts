/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {FormField, FormFieldTooltipAnchor, StaticLookupCall} from '@eclipse-scout/core';

export class TooltipAnchorLookupCall extends StaticLookupCall<FormFieldTooltipAnchor> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return TooltipAnchorLookupCall.DATA;
  }

  static DATA = [
    [FormField.TooltipAnchor.DEFAULT, 'default'],
    [FormField.TooltipAnchor.ON_FIELD, 'on field']
  ];
}
