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
import {Button, comparators, Event, EventEmitter, EventListener, GroupBoxModel, InitModelOf, models, TabItem, Widget} from '@eclipse-scout/core';
import EventsTabModel from './EventsTabModel';
import {App, EventsTabWidgetMap} from '../index';

export class EventsTab extends TabItem {
  declare widgetMap: EventsTabWidgetMap;

  field: EventEmitter;
  protected _listener: EventListener;

  constructor() {
    super();
    this.field = null;
    this._listener = {
      func: this._onEvent.bind(this)
    };
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(EventsTabModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setField(this.field);
    this.widget('EventsOverviewField').setValue(this.session.text('EventsOverview', App.get().scoutVersion));
    this.widget('ClearEventLogButton').on('click', this._onClearEventLogClick.bind(this));
  }

  setField(field: EventEmitter) {
    this.setProperty('field', field);
  }

  protected _setField(field: EventEmitter) {
    if (this.field) {
      this.field.removeListener(this._listener);
    }
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }
    this.field.addListener(this._listener);
  }

  protected _onEvent(event: Event<EventEmitter>) {
    if (event.type === 'destroy') {
      this.field.removeListener(this._listener);
    }
    if (this.destroyed) {
      // Do nothing if field is being destroyed (e.g. on form close)
      return;
    }
    let logField = this.widget('EventLogField');
    let log = logField.value || '';
    if (log) {
      log += '\n';
    }
    let entry = '';
    let keys = Object.keys(event);
    keys.sort(this._createPropertySortFunc(['source', 'type', 'propertyName', 'oldValue', 'newValue']));
    keys.forEach(key => {
      if (key === 'preventDefault') {
        return;
      }
      if (entry) {
        entry += ', ';
      }
      let value = event[key];
      if (value instanceof Widget) {
        value = value.objectType;
      } else if (Array.isArray(value) && value.length > 10) {
        value = value.slice(0, 10) + '...' + value.length; // Cut array to not slow down the browser
      }
      entry += key + ': ' + value;
    });
    log += entry;
    logField.setValue(log);
  }

  // noinspection DuplicatedCode
  protected _createPropertySortFunc(order: string[]): (a: string, b: string) => number {
    return (a, b) => {
      let ia = order.indexOf(a);
      let ib = order.indexOf(b);
      if (ia > -1 && ib > -1) { // both are in the list
        return ia - ib;
      }
      if (ia > -1) { // B is not in list
        return -1;
      }
      if (ib > -1) { // A is not in list
        return 1;
      }
      return comparators.TEXT.compare(a, b); // both are not in list
    };
  }

  protected _onClearEventLogClick(event: Event<Button>) {
    this.widget('EventLogField').setValue('');
  }
}
