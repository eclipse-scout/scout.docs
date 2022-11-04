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
import {Action, Event, GridData, icons, InitModelOf, Menu, scout, TabBox} from '@eclipse-scout/core';

export class ConfigurationBox extends TabBox {

  toggleMenu: Menu;
  protected _origWeightY: number;

  constructor() {
    super();
    this.cssClass = 'jswidgets-configuration';
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.toggleMenu = scout.create(Menu, {
      parent: this,
      cssClass: 'configuration-box-toggle-menu',
      menuTypes: [
        'TabBox.Header'
      ]
    });
    this.toggleMenu.on('action', this._onToggleMenuAction.bind(this));
    this._updateToggleIcon();
    this.setMenus([this.toggleMenu]);
  }

  protected _onToggleMenuAction(event: Event<Action>) {
    let expanded = !this.tabItems[0].expanded;
    this.tabItems.forEach(tab => {
      tab.setExpanded(expanded);
    });
    let gridData = new GridData(this.gridDataHints);
    if (expanded) {
      if (this._origWeightY) {
        gridData.weightY = this._origWeightY;
      }
    } else {
      this._origWeightY = gridData.weightY;
      gridData.weightY = 0;
    }
    this.setGridDataHints(gridData);
    this._updateToggleIcon();
  }

  protected _updateToggleIcon() {
    let iconId = icons.ANGLE_DOWN;
    if (!this.tabItems[0].expanded) {
      iconId = icons.ANGLE_UP;
    }
    this.toggleMenu.setIconId(iconId);
  }
}
