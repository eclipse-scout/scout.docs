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
import {App, Desktop as Desktop_1, icons, models, scout} from '@eclipse-scout/core';
import DesktopModel from './DesktopModel';

export default class Desktop extends Desktop_1 {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(DesktopModel);
  }

  _init(model) {
    super._init(model);

    this.on('logoAction', this._onLogoAction.bind(this));
    let defaultThemeMenu = this.widget('DefaultThemeMenu');
    defaultThemeMenu.on('action', this._onDefaultThemeMenuAction.bind(this));
    let darkThemeMenu = this.widget('DarkThemeMenu');
    darkThemeMenu.on('action', this._onDarkThemeMenuAction.bind(this));
    let denseModeMenu = this.widget('DenseMenu');
    denseModeMenu.on('action', this._onDenseMenuAction.bind(this));

    if (this.theme === 'dark') {
      darkThemeMenu.setIconId(icons.CHECKED_BOLD);
    } else {
      defaultThemeMenu.setIconId(icons.CHECKED_BOLD);
    }
    if (this.dense) {
      denseModeMenu.setIconId(icons.CHECKED_BOLD);
    }
    this.on('propertyChange:dense', event => this.dense ? denseModeMenu.setIconId(icons.CHECKED_BOLD) : denseModeMenu.setIconId(null));
  }

  _onDefaultThemeMenuAction(event) {
    this.setTheme('default');
  }

  _onDarkThemeMenuAction(event) {
    this.setTheme('dark');
  }

  _onDenseMenuAction(event) {
    this.setDense(!this.dense);
  }

  _onLogoAction(event) {
    let form = scout.create('Form', {
      parent: this,
      resizable: false,
      title: 'Scout JS Widgets Application',
      rootGroupBox: {
        objectType: 'GroupBox',
        borderDecoration: 'empty',
        fields: [{
          objectType: 'LabelField',
          value: this.session.text('AboutText', App.get().scoutVersion),
          labelVisible: false,
          wrapText: true,
          htmlEnabled: true,
          cssClass: 'about-info',
          statusVisible: false,
          gridDataHints: {
            h: 2
          }
        }]
      }
    });
    form.open();
  }
}
