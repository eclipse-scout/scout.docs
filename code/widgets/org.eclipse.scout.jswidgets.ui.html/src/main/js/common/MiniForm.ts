/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, models} from '@eclipse-scout/core';
import MiniFormModel from './MiniFormModel';
import {MiniFormWidgetMap} from '../index';

export class MiniForm extends Form {
  declare widgetMap: MiniFormWidgetMap;

  protected override _jsonModel(): FormModel {
    return models.get(MiniFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this.widget('CloseButton').on('click', () => this.close());
  }
}
