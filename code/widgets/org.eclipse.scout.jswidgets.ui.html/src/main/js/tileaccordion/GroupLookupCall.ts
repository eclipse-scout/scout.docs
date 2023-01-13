/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Group, StaticLookupCall, TileAccordion, TileGrid} from '@eclipse-scout/core';

export class GroupLookupCall extends StaticLookupCall<Group<TileGrid>> {
  accordion: TileAccordion;
  protected _rebuildDataHandler: () => void;

  constructor(accordion: TileAccordion) {
    super();
    this._rebuildDataHandler = this._rebuildData.bind(this);
    this.data = [];
    this.setAccordion(accordion);
  }

  protected override _data(): any[] {
    return this.data;
  }

  setAccordion(accordion: TileAccordion) {
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

  protected _rebuildData() {
    this.data = this.accordion.groups.map(group => {
      return [group, group.title];
    });
  }
}
