/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Group, InitModelOf, StaticLookupCall, StaticLookupCallModel, TileAccordion, TileGrid} from '@eclipse-scout/core';

export class GroupLookupCall extends StaticLookupCall<Group<TileGrid>> implements GroupLookupCallModel {
  declare model: GroupLookupCallModel;
  accordion: TileAccordion;
  protected _rebuildDataHandler: () => void;

  constructor() {
    super();
    this._rebuildDataHandler = this._rebuildData.bind(this);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this.setAccordion(this.accordion);
  }

  setAccordion(accordion: TileAccordion) {
    if (this.accordion) {
      this.accordion.off('propertyChange:groups', this._rebuildDataHandler);
      this.accordion.groups.forEach(group => group.off('propertyChange:title', this._rebuildDataHandler));
    }
    this.accordion = accordion;
    this.accordion.on('propertyChange:groups', this._rebuildDataHandler);
    this.accordion.groups.forEach(group => group.on('propertyChange:title', this._rebuildDataHandler));
    this._rebuildData();
  }

  protected _rebuildData() {
    this.data = this.accordion.groups.map(group => {
      return [group, group.title];
    });
  }
}

export interface GroupLookupCallModel extends StaticLookupCallModel<Group<TileGrid>> {
  accordion?: TileAccordion;
}
