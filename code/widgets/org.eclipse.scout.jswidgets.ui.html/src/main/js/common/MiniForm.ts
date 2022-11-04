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
import {Form, models} from '@eclipse-scout/core';
import MiniFormModel from './MiniFormModel';

export default class MiniForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(MiniFormModel);
  }

  _init(model) {
    super._init(model);
    this.widget('CloseButton').on('click', () => this.close());
  }
}
