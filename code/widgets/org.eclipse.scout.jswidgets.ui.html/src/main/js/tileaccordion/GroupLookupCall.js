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
import {StaticLookupCall} from '@eclipse-scout/core';

export default class GroupLookupCall extends StaticLookupCall {

  constructor(accordion) {
    super();
    this._accordionPropertyChangeHandler = this._onAccordionPropertyChange.bind(this);
    this._groupPropertyChangeHandler = this._onGroupPropertyChange.bind(this);
    this.data = [];
    this.setAccordion(accordion);
  }

  _data() {
    return this.data;
  }

  setAccordion(accordion) {
    if (this.accordion) {
      this.accordion.off('propertyChange', this._accordionPropertyChangeHandler);
      this.accordion.groups.forEach(function(group) {
        group.off('propertyChange', this._groupPropertyChangeHandler);
      }, this);
    }
    this.accordion = accordion;
    this.accordion.on('propertyChange', this._accordionPropertyChangeHandler);
    this.accordion.groups.forEach(function(group) {
      group.on('propertyChange', this._groupPropertyChangeHandler);
    }, this);
    this._rebuildData();
  }

  _rebuildData() {
    this.data = this.accordion.groups.map(group => {
      return [group, group.title];
    });
  }

  _onAccordionPropertyChange(event) {
    if (event.propertyName === 'groups') {
      this._rebuildData();
    }
  }

  _onGroupPropertyChange(event) {
    if (event.propertyName === 'title') {
      this._rebuildData();
    }
  }
}
