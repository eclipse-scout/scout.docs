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
import {StaticLookupCall} from '@eclipse-scout/core';

export default class ChildActionsLookupCall extends StaticLookupCall {

  constructor(action) {
    super();
    this._rebuildDataHandler = this._rebuildData.bind(this);
    this.data = [];
    this.setAction(action);
  }

  _data() {
    return this.data;
  }

  setAction(action) {
    if (this.action) {
      this.action.off('propertyChange:childActions', this._rebuildDataHandler);
      this.action.childActions.forEach(action => action.off('propertyChange:text', this._rebuildDataHandler));
    }
    this.action = action;
    if (this.action) {
      this.action.on('propertyChange:childActions', this._rebuildDataHandler);
      this.action.childActions.forEach(action => action.on('propertyChange:text', this._rebuildDataHandler));
    }
    this._rebuildData();
  }

  _rebuildData() {
    if (!this.action) {
      this.data = [];
      return;
    }
    this.data = this.action.childActions.map(menu => {
      return [menu, menu.text];
    });
  }
}
