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
