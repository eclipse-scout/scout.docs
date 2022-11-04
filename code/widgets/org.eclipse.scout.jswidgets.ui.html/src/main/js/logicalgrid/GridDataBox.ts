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
import {FormField, GridData, GroupBox, GroupBoxModel, InitModelOf, models, PropertyChangeEvent} from '@eclipse-scout/core';
import GridDataBoxModel from './GridDataBoxModel';
import {GridDataBoxWidgetMap} from '../index';

export class GridDataBox extends GroupBox {
  declare widgetMap: GridDataBoxWidgetMap;

  field: FormField;
  useHints: boolean;

  constructor() {
    super();
    this.field = null;
    this.useHints = true;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(GridDataBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setField(this.field);
    this._setEnabled(this.useHints);

    if (!this.useHints) {
      return;
    }

    this.widget('WField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
    this.widget('HField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
    this.widget('XField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
    this.widget('YField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
    this.widget('WeightXField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
    this.widget('WeightYField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
    this.widget('UseUiWidthField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
    this.widget('UseUiHeightField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
    this.widget('HorizontalAlignmentField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
    this.widget('VerticalAlignmentField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
    this.widget('FillHorizontalField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
    this.widget('FillVerticalField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
    this.widget('WidthInPixelField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
    this.widget('HeightInPixelField').on('propertyChange:value', event => this._updateGridDataByEvent(event));
  }

  setField(field: FormField) {
    this.setProperty('field', field);
  }

  protected _setField(field: FormField) {
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }
    this.reloadGridData();
  }

  reloadGridData() {
    let gridData = this.field.gridData;
    if (this.useHints) {
      gridData = this.field.gridDataHints;
    }
    this.widget('WField').setValue(gridData.w);
    this.widget('HField').setValue(gridData.h);
    this.widget('XField').setValue(gridData.x);
    this.widget('YField').setValue(gridData.y);
    this.widget('WeightXField').setValue(gridData.weightX);
    this.widget('WeightYField').setValue(gridData.weightY);
    this.widget('UseUiWidthField').setValue(gridData.useUiWidth);
    this.widget('UseUiHeightField').setValue(gridData.useUiHeight);
    this.widget('HorizontalAlignmentField').setValue(gridData.horizontalAlignment);
    this.widget('VerticalAlignmentField').setValue(gridData.verticalAlignment);
    this.widget('FillHorizontalField').setValue(gridData.fillHorizontal);
    this.widget('FillVerticalField').setValue(gridData.fillVertical);
    this.widget('WidthInPixelField').setValue(gridData.widthInPixel);
    this.widget('HeightInPixelField').setValue(gridData.heightInPixel);
  }

  protected _updateGridDataByEvent(event: PropertyChangeEvent<any, FormField>) {
    let gridData = new GridData(this.field.gridDataHints);
    this._fillGridDataByEvent(gridData, event);
    this.field.setGridDataHints(gridData);
  }

  protected _fillGridDataByEvent(gridData: GridData, event: PropertyChangeEvent<any, FormField>) {
    if (event.source.id === 'WField') {
      gridData.w = event.newValue;
    } else if (event.source.id === 'HField') {
      gridData.h = event.newValue;
    } else if (event.source.id === 'XField') {
      gridData.x = event.newValue;
    } else if (event.source.id === 'YField') {
      gridData.y = event.newValue;
    } else if (event.source.id === 'WeightXField') {
      gridData.weightX = event.newValue;
    } else if (event.source.id === 'WeightYField') {
      gridData.weightY = event.newValue;
    } else if (event.source.id === 'UseUiWidthField') {
      gridData.useUiWidth = event.newValue;
    } else if (event.source.id === 'UseUiHeightField') {
      gridData.useUiHeight = event.newValue;
    } else if (event.source.id === 'HorizontalAlignmentField') {
      gridData.horizontalAlignment = event.newValue;
    } else if (event.source.id === 'VerticalAlignmentField') {
      gridData.verticalAlignment = event.newValue;
    } else if (event.source.id === 'FillVerticalField') {
      gridData.fillVertical = event.newValue;
    } else if (event.source.id === 'FillHorizontalField') {
      gridData.fillHorizontal = event.newValue;
    } else if (event.source.id === 'WidthInPixelField') {
      gridData.widthInPixel = event.newValue;
    } else if (event.source.id === 'HeightInPixelField') {
      gridData.heightInPixel = event.newValue;
    }
  }
}
