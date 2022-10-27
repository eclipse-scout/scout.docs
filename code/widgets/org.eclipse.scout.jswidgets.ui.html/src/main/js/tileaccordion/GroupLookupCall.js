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

export default class GroupLookupCall extends StaticLookupCall {

  constructor(accordion) {
    super();
    this._rebuildDataHandler = this._rebuildData.bind(this);
    this.data = [];
    this.setAccordion(accordion);
  }

  _data() {
    return this.data;
  }

  setAccordion(accordion) {
    if (this.accordion) {
      this.accordion.off('propertyChange:groups', this._rebuildDataHandler);
      this.accordion.groups.forEach(function(group) {
        group.off('propertyChange:title', this._rebuildDataHandler);
      }, this);
    }
    this.accordion = accordion;
    this.accordion.on('propertyChange:groups', this._rebuildDataHandler);
    this.accordion.groups.forEach(function(group) {
      group.on('propertyChange:title', this._rebuildDataHandler);
    }, this);
    this._rebuildData();
  }

  _rebuildData() {
    this.data = this.accordion.groups.map(group => {
      return [group, group.title];
    });
  }
}
