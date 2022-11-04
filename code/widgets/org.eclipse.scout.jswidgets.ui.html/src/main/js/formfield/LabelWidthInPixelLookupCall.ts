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
import {FormField, FormFieldLabelWidth, StaticLookupCall} from '@eclipse-scout/core';

export class LabelWidthInPixelLookupCall extends StaticLookupCall<FormFieldLabelWidth> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return LabelWidthInPixelLookupCall.DATA;
  }

  static DATA = [
    [FormField.LabelWidth.DEFAULT, 'default'],
    [FormField.LabelWidth.UI, 'ui width']
  ];
}
