/*
 * Copyright (c) 2017 BSI Business Systems Integration AG. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0 which accompanies this
 * distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors: BSI Business Systems Integration AG - initial API and
 * implementation
 */
jswidgets.VirtualTileGridForm = function() {
  jswidgets.VirtualTileGridForm.parent.call(this);
};
scout.inherits(jswidgets.VirtualTileGridForm, jswidgets.TileGridForm);

jswidgets.VirtualTileGridForm.prototype._init = function(model) {
  jswidgets.VirtualTileGridForm.parent.prototype._init.call(this, model);

  this.tileGrid = this.widget('TileGrid');

  // Set virtual to true
  this.tileGrid.setVirtual(true);

  // Make tiles a little smaller and add a column to have more visible tiles on the screen
  this.tileGrid.setGridColumnCount(5);
  var layoutConfig = this.tileGrid.layoutConfig.clone();
  layoutConfig.columnWidth = 100;
  layoutConfig.rowWidth = 100;
  this.tileGrid.setLayoutConfig(layoutConfig);

  // Insert 1000 tiles
  var tiles = [];
  for (var i = 0; i < 1000; i++) {
    tiles.push(this._createTile({
      label: 'Tile ' + i
    }));
  }
  this.tileGrid.setTiles(tiles);

  // Update the property fields
  var virtualField = this.widget('VirtualField');
  virtualField.setValue(this.tileGrid.virtual);
};
