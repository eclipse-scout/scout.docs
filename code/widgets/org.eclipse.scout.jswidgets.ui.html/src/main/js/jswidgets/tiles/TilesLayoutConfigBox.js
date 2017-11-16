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
jswidgets.TilesLayoutConfigBox = function() {
  jswidgets.TilesLayoutConfigBox.parent.call(this);
};
scout.inherits(jswidgets.TilesLayoutConfigBox, jswidgets.LogicalGridLayoutConfigBox);

jswidgets.TilesLayoutConfigBox.prototype._jsonModel = function() {
  return scout.models.extend('jswidgets.TilesLayoutConfigBox', jswidgets.TilesLayoutConfigBox.parent.prototype._jsonModel.call(this));
};

jswidgets.TilesLayoutConfigBox.prototype._init = function(model) {
  jswidgets.TilesLayoutConfigBox.parent.prototype._init.call(this, model);

  this.widget('MaxWidthField').on('propertyChange', this._onPropertyChange.bind(this));
};

jswidgets.TilesLayoutConfigBox.prototype.reloadLogicalGridLayoutConfig = function() {
  jswidgets.TilesLayoutConfigBox.parent.prototype.reloadLogicalGridLayoutConfig.call(this);
  this.widget('MaxWidthField').setValue(this.field.layoutConfig.maxWidth);
};

jswidgets.TilesLayoutConfigBox.prototype._fillLayoutConfigByEvent = function(layoutConfig, event) {
  jswidgets.TilesLayoutConfigBox.parent.prototype._fillLayoutConfigByEvent.call(this, layoutConfig, event);
  if (event.source.id === 'MaxWidthField') {
    layoutConfig.maxWidth = event.newValue;
  }
};
