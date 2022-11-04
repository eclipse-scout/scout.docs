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
import {GroupBox, GroupBoxModel, InitModelOf, models, Notification, objects, scout, Status} from '@eclipse-scout/core';
import GroupBoxPropertiesBoxModel from './GroupBoxPropertiesBoxModel';
import {GroupBoxPropertiesBoxWidgetMap} from '../index';

export class GroupBoxPropertiesBox extends GroupBox {
  declare widgetMap: GroupBoxPropertiesBoxWidgetMap;

  field: GroupBox;

  constructor() {
    super();
    this.field = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(GroupBoxPropertiesBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this._setField(this.field);
  }

  setField(field: GroupBox) {
    this.setProperty('field', field);
  }

  protected _setField(field: GroupBox) {
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }

    let subLabelField = this.widget('SubLabelField');
    subLabelField.setValue(this.field.subLabel);
    subLabelField.on('propertyChange:value', event => this.field.setSubLabel(event.newValue));

    let borderVisibleField = this.widget('BorderVisibleField');
    borderVisibleField.setValue(this.field.borderVisible);
    borderVisibleField.on('propertyChange:value', event => this.field.setBorderVisible(event.newValue));

    let borderDecorationField = this.widget('BorderDecorationField');
    borderDecorationField.setValue(this.field.borderDecoration);
    borderDecorationField.on('propertyChange:value', event => this.field.setBorderDecoration(event.newValue));

    let expandableField = this.widget('ExpandableField');
    expandableField.setValue(this.field.expandable);
    expandableField.on('propertyChange:value', event => this.field.setExpandable(event.newValue));

    let expandedField = this.widget('ExpandedField');
    expandedField.setValue(this.field.expanded);
    expandedField.on('propertyChange:value', event => this.field.setExpanded(event.newValue));

    let responsiveField = this.widget('ResponsiveField');
    responsiveField.setValue(this.field.responsive);
    responsiveField.on('propertyChange:value', event => this.field.setResponsive(event.newValue));

    let scrollableField = this.widget('ScrollableField');
    scrollableField.setValue(this.field.scrollable);
    scrollableField.on('propertyChange:value', event => this.field.setScrollable(event.newValue));

    let gridColumnCountField = this.widget('GridColumnCountField');
    gridColumnCountField.setValue(this.field.gridColumnCount);
    gridColumnCountField.on('propertyChange:value', event => this.field.setGridColumnCount(event.newValue));

    let logicalGridField = this.widget('LogicalGridField');
    logicalGridField.setValue(this.field.logicalGrid ? this.field.logicalGrid.objectType : null);
    logicalGridField.on('propertyChange:value', event => this.field.setLogicalGrid(event.newValue));

    let notificationField = this.widget('NotificationField');
    notificationField.setValue(this.field.notification ? this.field.notification.status.severity : null);
    notificationField.on('propertyChange:value', event => this.field.setNotification(this._createNotification()));

    let notificationIconField = this.widget('NotificationIconField');
    notificationIconField.setValue(this.field.notification ? this.field.notification.status.iconId : null);
    notificationIconField.on('propertyChange:value', event => this.field.setNotification(this._createNotification()));

    let menuBarVisibleField = this.widget('MenuBarVisibleField');
    menuBarVisibleField.setValue(this.field.menuBarVisible);
    menuBarVisibleField.on('propertyChange:value', event => this.field.setMenuBarVisible(event.newValue));

    let menuBarPositionField = this.widget('MenuBarPositionField');
    menuBarPositionField.setValue(this.field.menuBarPosition);
    menuBarPositionField.on('propertyChange:value', event => this.field.setMenuBarPosition(event.newValue));

    let menuBarEllipsisPositionField = this.widget('MenuBarEllipsisPositionField');
    menuBarEllipsisPositionField.setValue(this.field.menuBarEllipsisPosition);
    menuBarEllipsisPositionField.on('propertyChange:value', event => this.field.setMenuBarEllipsisPosition(event.newValue));
  }

  protected _createNotification(): Notification {
    let severity = this.widget('NotificationField').value;
    if (!severity) {
      return null;
    }
    return scout.create(Notification, {
      parent: this,
      severity: severity,
      iconId: this.widget('NotificationIconField').value,
      message: this.session.text('NotificationMessage', objects.keyByValue(Status.Severity, severity))
    });
  }
}
