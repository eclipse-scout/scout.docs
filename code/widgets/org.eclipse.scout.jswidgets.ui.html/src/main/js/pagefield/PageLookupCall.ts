/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Page} from '@eclipse-scout/core';
import {ObjectTypeLookupCall, SamplePageWithNodes, SamplePageWithTable} from '../index';

export class PageLookupCall extends ObjectTypeLookupCall<Page> {

  protected override _data(): any[] {
    return [
      [SamplePageWithNodes, 'Sample page with nodes'],
      [SamplePageWithTable, 'Sample page with table']
    ];
  }
}
