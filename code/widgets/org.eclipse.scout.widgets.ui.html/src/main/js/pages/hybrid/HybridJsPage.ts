/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

import {HybridManager, InitModelOf, models, pageParam, PageWithNodes} from '@eclipse-scout/core';
import HybridJsPageModel from './HybridJsPageModel';
import {HybridJsPageParamDo} from '../../index';

@pageParam(HybridJsPageParamDo)
export class HybridJsPage extends PageWithNodes {

  protected override _jsonModel(): Record<string, any> {
    return models.get(HybridJsPageModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    HybridManager.get(this.session, true)
      .then(hybridManager => hybridManager.createForm('Hybrid'))
      .then(form => {
        this.setDetailForm(form);
        this.one('destroying', e => form.close());
      });
  }
}
