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
import {TileGridForm} from '../index';
import {InitModelOf} from '@eclipse-scout/core';

export class VirtualTileGridForm extends TileGridForm {

  constructor() {
    super();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.tileGrid = this.widget('TileGrid');

    // Set virtual to true
    this.tileGrid.setVirtual(true);

    // Make tiles a little smaller and add a column to have more visible tiles on the screen
    this.tileGrid.setGridColumnCount(5);
    let layoutConfig = this.tileGrid.layoutConfig.clone();
    layoutConfig.columnWidth = 100;
    layoutConfig.rowHeight = 100;
    this.tileGrid.setLayoutConfig(layoutConfig);

    // Insert 1000 tiles
    let tiles = [];
    for (let i = 0; i < 1000; i++) {
      tiles.push(this._createTile({
        label: 'Tile ' + i
      }));
    }
    this.tileGrid.setTiles(tiles);

    // Update the property fields
    let virtualField = this.widget('VirtualField');
    virtualField.setValue(this.tileGrid.virtual);
  }
}
