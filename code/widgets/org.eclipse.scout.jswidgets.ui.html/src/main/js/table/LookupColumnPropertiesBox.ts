/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {GroupBoxModel, InitModelOf, LookupColumn, models} from '@eclipse-scout/core';
import model, {LookupColumnPropertiesBoxWidgetMap} from './LookupColumnPropertiesBoxModel';
import {LookupCallColumnPropertiesBox} from './LookupCallColumnPropertiesBox';

export class LookupColumnPropertiesBox extends LookupCallColumnPropertiesBox {
  declare widgetMap: LookupColumnPropertiesBoxWidgetMap;

  declare column: LookupColumn<any>;

  constructor() {
    super();
    this.column = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.extend(model(), super._jsonModel());
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setColumn(this.column);
  }

  override setColumn(column: LookupColumn<any>) {
    super.setColumn(column);
  }

  protected override _setColumn(column: LookupColumn<any>) {
    super._setColumn(column);
    if (!this.column) {
      return;
    }

    this.widget('DistinctField').setValue(this.column.distinct);
    this.widget('DistinctField').on('propertyChange:value', event => this.column.setDistinct(event.newValue));
  }
}
