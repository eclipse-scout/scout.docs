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
import {FormField, GroupBox, GroupBoxModel, InitModelOf, LogicalGridLayout, LogicalGridLayoutConfig, models, PropertyChangeEvent, RadioButtonGroup, SequenceBox, TileAccordion, TileGrid} from '@eclipse-scout/core';
import LogicalGridLayoutConfigBoxModel from './LogicalGridLayoutConfigBoxModel';
import {LogicalGridLayoutConfigBoxWidgetMap} from '../index';

export class LogicalGridLayoutConfigBox extends GroupBox {
  declare widgetMap: LogicalGridLayoutConfigBoxWidgetMap;

  field: GroupBox | SequenceBox | RadioButtonGroup<any> | TileGrid | TileAccordion;

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
    this.widget('HGapField').on('propertyChange:value', event => this._updateLayoutConfigByEvent(event));
    this.widget('VGapField').on('propertyChange:value', event => this._updateLayoutConfigByEvent(event));
    this.widget('RowHeightField').on('propertyChange:value', event => this._updateLayoutConfigByEvent(event));
    this.widget('ColumnWidthField').on('propertyChange:value', event => this._updateLayoutConfigByEvent(event));
    this.widget('MinWidthField').on('propertyChange:value', event => this._updateLayoutConfigByEvent(event));
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
    let bodyLayout = this.getBodyLayout();
    this.widget('HGapField').setValue(bodyLayout.hgap);
    this.widget('VGapField').setValue(bodyLayout.vgap);
    this.widget('RowHeightField').setValue(bodyLayout.rowHeight);
    this.widget('ColumnWidthField').setValue(bodyLayout.columnWidth);
    this.widget('MinWidthField').setValue(bodyLayout.minWidth);
  }

  protected _updateLayoutConfigByEvent(event: PropertyChangeEvent<any, FormField>) {
    let layoutConfig = this.getLayoutConfig().clone();
    this._fillLayoutConfigByEvent(layoutConfig, event);
    this.setLayoutConfig(layoutConfig);
  }

  protected _fillLayoutConfigByEvent(layoutConfig: LogicalGridLayoutConfig, event: PropertyChangeEvent<any, FormField>) {
    if (event.source.id === 'HGapField') {
      layoutConfig.hgap = event.newValue;
    } else if (event.source.id === 'VGapField') {
      layoutConfig.vgap = event.newValue;
    } else if (event.source.id === 'RowHeightField') {
      layoutConfig.rowHeight = event.newValue;
    } else if (event.source.id === 'ColumnWidthField') {
      layoutConfig.columnWidth = event.newValue;
    } else if (event.source.id === 'MinWidthField') {
      layoutConfig.minWidth = event.newValue;
    }
  }

  /**
   * Return the body layout of the widget. Used to initialized the config box with the default values.
   */
  getBodyLayout(): LogicalGridLayout {
    return (this.field as GroupBox | SequenceBox | RadioButtonGroup<any>).htmlBody.layout as LogicalGridLayout;
  }

  getLayoutConfig(): LogicalGridLayoutConfig {
    return (this.field as SequenceBox | RadioButtonGroup<any> | TileGrid).layoutConfig;
  }

  setLayoutConfig(layoutConfig: LogicalGridLayoutConfig) {
    (this.field as SequenceBox | RadioButtonGroup<any>).setLayoutConfig(layoutConfig);
  }
}
