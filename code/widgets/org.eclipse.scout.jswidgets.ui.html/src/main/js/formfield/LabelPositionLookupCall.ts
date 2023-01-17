/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {FormField, FormFieldLabelPosition, StaticLookupCall} from '@eclipse-scout/core';

export class LabelPositionLookupCall extends StaticLookupCall<FormFieldLabelPosition> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return LabelPositionLookupCall.DATA;
  }

  static DATA = [
    [FormField.LabelPosition.DEFAULT, 'default'],
    [FormField.LabelPosition.LEFT, 'left'],
    [FormField.LabelPosition.ON_FIELD, 'on field'],
    [FormField.LabelPosition.TOP, 'top']
  ];
}
