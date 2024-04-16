/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {FormField, GroupBox, GroupBoxModel, InitModelOf, LogicalGridLayoutConfig, models, PropertyChangeEvent, RadioButtonGroup, SequenceBox, TileAccordion, TileGrid} from '@eclipse-scout/core';
import LogicalGridLayoutConfigBoxModel from './LogicalGridLayoutConfigBoxModel';
import {LogicalGridLayoutConfigBoxWidgetMap} from '../index';

export class LogicalGridLayoutConfigBox extends GroupBox {
  declare widgetMap: LogicalGridLayoutConfigBoxWidgetMap;

  field: SequenceBox | RadioButtonGroup<any>;

  constructor() {
    super();
    this.field = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(LogicalGridLayoutConfigBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setField(this.field);
    this.widget('HGapField').on('propertyChange:value', event => this._updateLayoutConfig(event));
    this.widget('VGapField').on('propertyChange:value', event => this._updateLayoutConfig(event));
    this.widget('RowHeightField').on('propertyChange:value', event => this._updateLayoutConfig(event));
    this.widget('ColumnWidthField').on('propertyChange:value', event => this._updateLayoutConfig(event));
    this.widget('MinWidthField').on('propertyChange:value', event => this._updateLayoutConfig(event));
  }

  setField(field: GroupBox | SequenceBox | RadioButtonGroup<any> | TileGrid | TileAccordion) {
    this.setProperty('field', field);
  }

  protected _setField(field: GroupBox | SequenceBox | RadioButtonGroup<any> | TileGrid | TileAccordion) {
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }
    // layout defaults are only known after the widget got rendered.
    field.one('render', this.initLayoutDefaults.bind(this));
  }

  initLayoutDefaults() {
    let layoutConfig = this.getLayoutConfig();
    this.widget('HGapField').setValue(layoutConfig.hgap);
    this.widget('VGapField').setValue(layoutConfig.vgap);
    this.widget('RowHeightField').setValue(layoutConfig.rowHeight);
    this.widget('ColumnWidthField').setValue(layoutConfig.columnWidth);
    this.widget('MinWidthField').setValue(layoutConfig.minWidth);
  }

  protected _updateLayoutConfig(event: PropertyChangeEvent<any, FormField>) {
    this.setLayoutConfig(this.getLayoutConfig().clone(this._readLayoutConfig()));
  }

  protected _readLayoutConfig(): InitModelOf<LogicalGridLayoutConfig> {
    return {
      hgap: this.widget('HGapField').value,
      vgap: this.widget('VGapField').value,
      rowHeight: this.widget('RowHeightField').value,
      columnWidth: this.widget('ColumnWidthField').value,
      minWidth: this.widget('MinWidthField').value
    };
  }

  // These methods will be replaced by GroupBoxForm and TileAccordionForm
  getLayoutConfig(): LogicalGridLayoutConfig {
    return this.field.layoutConfig;
  }

  setLayoutConfig(layoutConfig: LogicalGridLayoutConfig) {
    this.field.setLayoutConfig(layoutConfig);
  }
}
