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
jswidgets.ConfigurationBox = function() {
  jswidgets.ConfigurationBox.parent.call(this);
  this.cssClass = 'jswidgets-configuration';
};
scout.inherits(jswidgets.ConfigurationBox, scout.TabBox);

jswidgets.ConfigurationBox.prototype._init = function(model) {
  jswidgets.ConfigurationBox.parent.prototype._init.call(this, model);

  this.toggleMenu = scout.create('Menu', {
    parent: this,
    menuTypes: [
      'TabBox.Header'
    ]
  });
  this.toggleMenu.on('action', this._onToggleMenuAction.bind(this));
  this._updateToggleIcon();
  this.setMenus([this.toggleMenu]);
};

jswidgets.ConfigurationBox.prototype._onToggleMenuAction = function(event) {
  var expanded = !this.tabItems[0].expanded;
  this.tabItems.forEach(function(tab) {
    tab.setExpanded(expanded);
  });
  var gridData = new scout.GridData(this.gridDataHints);
  var weightY;
  if (expanded) {
    if (this._origWeightY !== undefined) {
      gridData.weightY = this._origWeightY;
    }
  } else {
    this._origWeightY = gridData.weightY;
    gridData.weightY = 0;
  }
  this.setGridDataHints(gridData);
  this._updateToggleIcon();
};

jswidgets.ConfigurationBox.prototype._updateToggleIcon = function() {
  var iconId = scout.icons.ANGLE_DOWN_BOLD;
  if (!this.tabItems[0].expanded) {
    iconId = scout.icons.ANGLE_UP_BOLD;
  }
  this.toggleMenu.setIconId(iconId);
};
