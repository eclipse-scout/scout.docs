/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {StaticLookupCall, Status, StatusSeverity} from '@eclipse-scout/core';

export class StatusSeverityLookupCall extends StaticLookupCall<StatusSeverity> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return StatusSeverityLookupCall.DATA;
  }

  static DATA = [
    [Status.Severity.OK, 'ok'],
    [Status.Severity.INFO, 'info'],
    [Status.Severity.WARNING, 'warning'],
    [Status.Severity.ERROR, 'error']
  ];
}
