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
