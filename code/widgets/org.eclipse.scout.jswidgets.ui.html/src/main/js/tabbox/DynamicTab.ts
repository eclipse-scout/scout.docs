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
import {InitModelOf, models, TabItem, TabItemModel} from '@eclipse-scout/core';
import DynamicTabModel from './DynamicTabModel';
import {DynamicTabWidgetMap} from '../index';

export class DynamicTab extends TabItem {
  declare widgetMap: DynamicTabWidgetMap;

  constructor() {
    super();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.widget('label').setValue('This is the content area of the \'TabItem\'. The selected tab is \'' + this.label + '\'.');
  }

  protected override _jsonModel(): TabItemModel {
    return models.get(DynamicTabModel);
  }
}
