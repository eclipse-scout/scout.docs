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
import {GroupBox, models} from '@eclipse-scout/core';
import LogicalGridLayoutConfigBoxModel from './LogicalGridLayoutConfigBoxModel';

export default class LogicalGridLayoutConfigBox extends GroupBox {

  constructor() {
    super();
    this.field = null;
  }

  _jsonModel() {
    return models.get(LogicalGridLayoutConfigBoxModel);
  }

  _init(model) {
    super._init(model);

    this._setField(this.field);
    this.widget('HGapField').on('propertyChange:value', event => this._updateLayoutConfigByEvent(event));
    this.widget('VGapField').on('propertyChange:value', event => this._updateLayoutConfigByEvent(event));
    this.widget('RowHeightField').on('propertyChange:value', event => this._updateLayoutConfigByEvent(event));
    this.widget('ColumnWidthField').on('propertyChange:value', event => this._updateLayoutConfigByEvent(event));
    this.widget('MinWidthField').on('propertyChange:value', event => this._updateLayoutConfigByEvent(event));
  }

  setField(field) {
    this.setProperty('field', field);
  }

  _setField(field) {
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

  _updateLayoutConfigByEvent(event) {
    let layoutConfig = this.getLayoutConfig().clone();
    this._fillLayoutConfigByEvent(layoutConfig, event);
    this.setLayoutConfig(layoutConfig);
  }

  _fillLayoutConfigByEvent(layoutConfig, event) {
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
  getBodyLayout() {
    return this.field.htmlBody.layout;
  }

  getLayoutConfig() {
    return this.field.layoutConfig;
  }

  setLayoutConfig(layoutConfig) {
    this.field.setLayoutConfig(layoutConfig);
  }
}
