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
import {TabItem, models} from '@eclipse-scout/core';
import DynamicTabModel from './DynamicTabModel';

export default class DynamicTab extends TabItem {

  constructor() {
    super();
  }


  _init(model) {
    super._init(model);

    this.widget('label').setValue('This is the content area of the \'TabItem\'. The selected tab is \'' + this.label + '\'.');
  }

  _jsonModel() {
    return models.get(DynamicTabModel);
  }
}
