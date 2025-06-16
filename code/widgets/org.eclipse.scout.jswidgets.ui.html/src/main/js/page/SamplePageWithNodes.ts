/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {models, Page, PageModel, PageWithNodes, scout} from '@eclipse-scout/core';
import SamplePageWithNodesModel from './SamplePageWithNodesModel';
import $ from 'jquery';
import {SamplePageWithTable} from '../index';

export class SamplePageWithNodes extends PageWithNodes {

  protected override _jsonModel(): PageModel {
    return models.get(SamplePageWithNodesModel);
  }

  protected override _createChildPages(): JQuery.Promise<Page[]> {
    return $.resolvedPromise([
      scout.create(SamplePageWithTable, {
        parent: this.outline
      }),
      scout.create(SamplePageWithNodes, {
        parent: this.outline
      })
    ]);
  }
}
