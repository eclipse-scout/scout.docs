/*******************************************************************************
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
jswidgets.TileGridLayoutConfigBox = function() {
  jswidgets.TileGridLayoutConfigBox.parent.call(this);
};
scout.inherits(jswidgets.TileGridLayoutConfigBox, jswidgets.LogicalGridLayoutConfigBox);

jswidgets.TileGridLayoutConfigBox.prototype._jsonModel = function() {
  return scout.models.extend('jswidgets.TileGridLayoutConfigBox', jswidgets.TileGridLayoutConfigBox.parent.prototype._jsonModel.call(this));
};

jswidgets.TileGridLayoutConfigBox.prototype._init = function(model) {
  jswidgets.TileGridLayoutConfigBox.parent.prototype._init.call(this, model);

  this.widget('MaxWidthField').on('propertyChange', this._onPropertyChange.bind(this));
};

jswidgets.TileGridLayoutConfigBox.prototype.initLayoutDefaults = function() {
  jswidgets.TileGridLayoutConfigBox.parent.prototype.initLayoutDefaults.call(this);
  this.widget('MaxWidthField').setValue(this.getBodyLayout().maxWidth);
};

jswidgets.TileGridLayoutConfigBox.prototype._fillLayoutConfigByEvent = function(layoutConfig, event) {
  jswidgets.TileGridLayoutConfigBox.parent.prototype._fillLayoutConfigByEvent.call(this, layoutConfig, event);
  if (event.source.id === 'MaxWidthField') {
    layoutConfig.maxWidth = event.newValue;
  }
};
