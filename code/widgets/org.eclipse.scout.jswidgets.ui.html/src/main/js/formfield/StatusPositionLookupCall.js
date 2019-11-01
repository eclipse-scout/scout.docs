/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {StaticLookupCall, FormField} from '@eclipse-scout/core';

export default class StatusPositionLookupCall extends StaticLookupCall {

constructor() {
  super();
}


_data() {
  return StatusPositionLookupCall.DATA;
}

static DATA = [
  [FormField.StatusPosition.DEFAULT, 'default'],
  [FormField.StatusPosition.TOP, 'top']
];
}
