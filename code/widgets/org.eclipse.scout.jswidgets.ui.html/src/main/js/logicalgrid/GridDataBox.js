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
import {GridData, GroupBox, models} from '@eclipse-scout/core';
import GridDataBoxModel from './GridDataBoxModel';

export default class GridDataBox extends GroupBox {

  constructor() {
    super();
    this.field = null;
    this.useHints = true;
  }

  _jsonModel() {
    return models.get(GridDataBoxModel);
  }

  _init(model) {
    super._init(model);

    this._setField(this.field);
    this._setEnabled(this.useHints);

    if (!this.useHints) {
      return;
    }

    this.widget('WField').on('propertyChange', this._onPropertyChange.bind(this));
    this.widget('HField').on('propertyChange', this._onPropertyChange.bind(this));
    this.widget('XField').on('propertyChange', this._onPropertyChange.bind(this));
    this.widget('YField').on('propertyChange', this._onPropertyChange.bind(this));
    this.widget('WeightXField').on('propertyChange', this._onPropertyChange.bind(this));
    this.widget('WeightYField').on('propertyChange', this._onPropertyChange.bind(this));
    this.widget('UseUiWidthField').on('propertyChange', this._onPropertyChange.bind(this));
    this.widget('UseUiHeightField').on('propertyChange', this._onPropertyChange.bind(this));
    this.widget('HorizontalAlignmentField').on('propertyChange', this._onPropertyChange.bind(this));
    this.widget('VerticalAlignmentField').on('propertyChange', this._onPropertyChange.bind(this));
    this.widget('FillHorizontalField').on('propertyChange', this._onPropertyChange.bind(this));
    this.widget('FillVerticalField').on('propertyChange', this._onPropertyChange.bind(this));
    this.widget('WidthInPixelField').on('propertyChange', this._onPropertyChange.bind(this));
    this.widget('HeightInPixelField').on('propertyChange', this._onPropertyChange.bind(this));
  }

  setField(field) {
    this.setProperty('field', field);
  }

  _setField(field) {
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

  _onPropertyChange(event) {
    if (event.propertyName !== 'value') {
      return;
    }
    let gridData = new GridData(this.field.gridDataHints);
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
    this.field.setGridDataHints(gridData);
  }
}
