/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
    let layoutConfig = this.tileGrid.layoutConfig.clone({
      columnWidth: 100,
      rowHeight: 100
    });
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
