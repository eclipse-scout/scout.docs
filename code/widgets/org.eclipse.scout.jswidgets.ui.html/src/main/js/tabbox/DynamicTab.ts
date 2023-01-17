/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
