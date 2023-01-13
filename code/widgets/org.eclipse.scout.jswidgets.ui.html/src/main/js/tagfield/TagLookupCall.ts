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

export class TagLookupCall extends StaticLookupCall<string> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    let tags = TagLookupCall.TAGS;
    tags.sort();

    let data = [];
    tags.forEach(tag => {
      data.push([tag, tag]);
    });
    return data;
  }

  static TAGS = ['scout', 'eclipse scout', 'scout js', 'eclipse', 'bsi', 'business systems integration ag', 'open source', 'widgets', 'js widgets'];
}
