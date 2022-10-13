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
import {GroupBox, models, objects, scout, Status} from '@eclipse-scout/core';
import GroupBoxPropertiesBoxModel from './GroupBoxPropertiesBoxModel';

export default class GroupBoxPropertiesBox extends GroupBox {

  constructor() {
    super();
    this.field = null;
  }

  _jsonModel() {
    return models.get(GroupBoxPropertiesBoxModel);
  }

  _init(model) {
    super._init(model);
    this._setField(this.field);
  }

  setField(field) {
    this.setProperty('field', field);
  }

  _setField(field) {
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }

    let subLabelField = this.widget('SubLabelField');
    subLabelField.setValue(this.field.subLabel);
    subLabelField.on('propertyChange', this._onPropertyChange.bind(this));

    let borderVisibleField = this.widget('BorderVisibleField');
    borderVisibleField.setValue(this.field.borderVisible);
    borderVisibleField.on('propertyChange', this._onPropertyChange.bind(this));

    let borderDecorationField = this.widget('BorderDecorationField');
    borderDecorationField.setValue(this.field.borderDecoration);
    borderDecorationField.on('propertyChange', this._onPropertyChange.bind(this));

    let expandableField = this.widget('ExpandableField');
    expandableField.setValue(this.field.expandable);
    expandableField.on('propertyChange', this._onPropertyChange.bind(this));

    let expandedField = this.widget('ExpandedField');
    expandedField.setValue(this.field.expanded);
    expandedField.on('propertyChange', this._onPropertyChange.bind(this));

    let responsiveField = this.widget('ResponsiveField');
    responsiveField.setValue(this.field.responsive);
    responsiveField.on('propertyChange', this._onPropertyChange.bind(this));

    let scrollableField = this.widget('ScrollableField');
    scrollableField.setValue(this.field.scrollable);
    scrollableField.on('propertyChange', this._onPropertyChange.bind(this));

    let gridColumnCountField = this.widget('GridColumnCountField');
    gridColumnCountField.setValue(this.field.gridColumnCount);
    gridColumnCountField.on('propertyChange', this._onPropertyChange.bind(this));

    let logicalGridField = this.widget('LogicalGridField');
    logicalGridField.setValue(this.field.logicalGrid ? this.field.logicalGrid.objectType : null);
    logicalGridField.on('propertyChange', this._onPropertyChange.bind(this));

    let notificationField = this.widget('NotificationField');
    notificationField.setValue(this.field.notification ? this.field.notification.status.severity : null);
    notificationField.on('propertyChange', this._onPropertyChange.bind(this));

    let notificationIconField = this.widget('NotificationIconField');
    notificationIconField.setValue(this.field.notification ? this.field.notification.iconId : null);
    notificationIconField.on('propertyChange', this._onPropertyChange.bind(this));

    let menuBarVisibleField = this.widget('MenuBarVisibleField');
    menuBarVisibleField.setValue(this.field.menuBarVisible);
    menuBarVisibleField.on('propertyChange', this._onPropertyChange.bind(this));

    let menuBarPositionField = this.widget('MenuBarPositionField');
    menuBarPositionField.setValue(this.field.menuBarPosition);
    menuBarPositionField.on('propertyChange', this._onPropertyChange.bind(this));

    let menuBarEllipsisPositionField = this.widget('MenuBarEllipsisPositionField');
    menuBarEllipsisPositionField.setValue(this.field.menuBarEllipsisPosition);
    menuBarEllipsisPositionField.on('propertyChange', this._onPropertyChange.bind(this));
  }

  _onPropertyChange(event) {
    if (event.propertyName === 'value' && event.source.id === 'SubLabelField') {
      this.field.setSubLabel(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'BorderVisibleField') {
      this.field.setBorderVisible(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'BorderDecorationField') {
      this.field.setBorderDecoration(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'ExpandableField') {
      this.field.setExpandable(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'ExpandedField') {
      this.field.setExpanded(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'ResponsiveField') {
      this.field.setResponsive(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'ScrollableField') {
      this.field.setScrollable(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'GridColumnCountField') {
      this.field.setGridColumnCount(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'LogicalGridField') {
      this.field.setLogicalGrid(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'NotificationField') {
      this.field.setNotification(this._createNotification());
    } else if (event.propertyName === 'value' && event.source.id === 'NotificationIconField') {
      this.field.setNotification(this._createNotification());
    } else if (event.propertyName === 'value' && event.source.id === 'MenuBarVisibleField') {
      this.field.setMenuBarVisible(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'MenuBarPositionField') {
      this.field.setMenuBarPosition(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'MenuBarEllipsisPositionField') {
      this.field.setMenuBarEllipsisPosition(event.newValue);
    }
  }

  _createNotification() {
    let severity = this.widget('NotificationField').value;
    if (!severity) {
      return null;
    }
    return scout.create('Notification', {
      parent: this,
      severity: severity,
      iconId: this.widget('NotificationIconField').value,
      message: this.session.text('NotificationMessage', objects.keyByValue(Status.Severity, severity))
    });
  }
}
