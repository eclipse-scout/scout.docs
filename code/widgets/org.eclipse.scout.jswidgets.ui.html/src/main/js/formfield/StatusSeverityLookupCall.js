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
import {StaticLookupCall, Status} from '@eclipse-scout/core';

export default class StatusSeverityLookupCall extends StaticLookupCall {

constructor() {
  super();
}


_data() {
  return StatusSeverityLookupCall.DATA;
}

static DATA = [
  [Status.Severity.INFO, 'info'],
  [Status.Severity.WARNING, 'warning'],
  [Status.Severity.ERROR, 'error']
];
}
