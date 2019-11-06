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
import {GridData, icons, scout, TabBox} from '@eclipse-scout/core';

export default class ConfigurationBox extends TabBox {

  constructor() {
    super();
    this.cssClass = 'jswidgets-configuration';
  }

  _init(model) {
    super._init(model);

    this.toggleMenu = scout.create('Menu', {
      parent: this,
      menuTypes: [
        'TabBox.Header'
      ]
    });
    this.toggleMenu.on('action', this._onToggleMenuAction.bind(this));
    this._updateToggleIcon();
    this.setMenus([this.toggleMenu]);
  }

  _onToggleMenuAction(event) {
    var expanded = !this.tabItems[0].expanded;
    this.tabItems.forEach(function(tab) {
      tab.setExpanded(expanded);
    });
    var gridData = new GridData(this.gridDataHints);
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
  }

  _updateToggleIcon() {
    var iconId = icons.ANGLE_DOWN_BOLD;
    if (!this.tabItems[0].expanded) {
      iconId = icons.ANGLE_UP_BOLD;
    }
    this.toggleMenu.setIconId(iconId);
  }
}
