/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {GroupBoxModel, InitModelOf, models, Table} from '@eclipse-scout/core';
import HierarchicalTablePropertiesBoxModel from './HierarchicalTablePropertiesBoxModel';
import {HierarchicalTablePropertiesBoxWidgetMap, TablePropertiesBox} from '../../index';

export class HierarchicalTablePropertiesBox extends TablePropertiesBox {
  declare widgetMap: HierarchicalTablePropertiesBoxWidgetMap;

  constructor() {
    super();
    this.table = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.extend(HierarchicalTablePropertiesBoxModel, super._jsonModel());
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setTable(this.table);
  }

  override setTable(table: Table) {
    this.setProperty('table', table);
  }

  protected override _setTable(table: Table) {
    super._setTable(table);
    this._setProperty('table', table);
    if (!this.table) {
      return;
    }

    let hierarchicalStyleField = this.widget('HierarchicalStyleField');
    hierarchicalStyleField.setValue(this.table.hierarchicalStyle);
    hierarchicalStyleField.on('propertyChange:value', event => this.table.setHierarchicalStyle(event.newValue));

    let extendedHierarchyPaddingField = this.widget('ExtendedHierarchyPaddingField');
    extendedHierarchyPaddingField.setValue(this.table.cssClassAsArray().indexOf('extended-row-level-padding') > -1);
    extendedHierarchyPaddingField.on('propertyChange:value', event => this.table.toggleCssClass('extended-row-level-padding', event.newValue));
  }
}
