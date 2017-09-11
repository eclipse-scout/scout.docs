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
jswidgets.TilesForm = function() {
  jswidgets.TilesForm.parent.call(this);
};
scout.inherits(jswidgets.TilesForm, scout.Form);

jswidgets.TilesForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TilesForm');
};

jswidgets.TilesForm.prototype._init = function(model) {
  jswidgets.TilesForm.parent.prototype._init.call(this, model);

  var tiles = this.widget('Tiles');
  var withPlaceholdersField = this.widget('WithPlaceholdersField');
  withPlaceholdersField.setValue(tiles.withPlaceholders);
  withPlaceholdersField.on('propertyChange', this._onWithPlacehodersPropertyChange.bind(this));
};

jswidgets.TilesForm.prototype._onWithPlacehodersPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('Tiles').setWithPlaceholders(event.newValue);
  }
};
