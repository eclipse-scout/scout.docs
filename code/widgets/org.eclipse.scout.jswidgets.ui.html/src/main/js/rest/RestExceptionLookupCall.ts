/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {StaticLookupCall} from '@eclipse-scout/core';

export class RestExceptionLookupCall extends StaticLookupCall<number> {
  protected override _data(): any[] {
    return RestExceptionLookupCall.DATA;
  }

  static DATA = [
    [1000, 'VetoException'],
    [2000, 'AccessForbiddenException'],
    [3000, 'ResourceNotFoundException'],
    [4000, 'InterruptionError'],
    [5000, 'JsonMappingException'],
    [6000, 'IllegalArgumentException'],
    [7000, 'RuntimeException'],
    [8000, 'ProcessingException'],
    [9000, 'RemoteSystemUnavailableException']
  ];
}
