/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {
  Action, CheckBoxField, Event, Form, FormModel, GroupBox, InitModelOf, Menu, models, PropertyChangeEvent, scout, SimpleTabAreaDisplayStyle, SimpleTabAreaPosition, SimpleTabBox, SimpleTabBoxViewActivateEvent, SimpleTabView, SmartField,
  Status, StatusSeverity, StringField
} from '@eclipse-scout/core';
import SimpleTabBoxFormModel from './SimpleTabBoxFormModel';
import {SimpleTabBoxFormWidgetMap} from '../index';

export class SimpleTabBoxForm extends Form {
  declare widgetMap: SimpleTabBoxFormWidgetMap;

  simpleTabBox: SimpleTabBox;
  addTabMenu: Menu;
  deleteTabMenu: Menu;

  simpleTabAreaPropertiesBox: GroupBox;
  displayStyleField: SmartField<SimpleTabAreaDisplayStyle>;
  positionField: SmartField<SimpleTabAreaPosition>;

  simpleTabPropertiesBox: GroupBox;
  titleField: StringField;
  subTitleField: StringField;
  iconIdField: SmartField<string>;
  closableField: CheckBoxField;
  statusField: SmartField<StatusSeverity>;

  protected _tabCounter = 0;

  protected override _jsonModel(): FormModel {
    return models.get(SimpleTabBoxFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.simpleTabBox = this.widget('SimpleTabBox');
    this.simpleTabBox.on('viewActivate', this._onSimpleTabBoxViewActivate.bind(this));

    this.addTabMenu = this.widget('AddTabMenu');
    this.addTabMenu.on('action', this._onAddTabMenuAction.bind(this));

    this.deleteTabMenu = this.widget('DeleteTabMenu');
    this.deleteTabMenu.on('action', this._onDeleteTabMenuAction.bind(this));

    this.simpleTabAreaPropertiesBox = this.widget('SimpleTabAreaPropertiesBox');

    this.displayStyleField = this.widget('DisplayStyleField');
    this.displayStyleField.setValue(this.simpleTabBox.tabArea.displayStyle);
    this.displayStyleField.on('propertyChange:value', this._onDisplayStyleFieldValueChange.bind(this));

    this.positionField = this.widget('PositionField');
    this.positionField.setValue(this.simpleTabBox.tabArea.position);
    this.positionField.on('propertyChange:value', this._onPositionFieldValueChange.bind(this));

    this.simpleTabPropertiesBox = this.widget('SimpleTabPropertiesBox');

    this.titleField = this.widget('TitleField');
    this.titleField.on('propertyChange:value', this._onTitleFieldValueChange.bind(this));

    this.subTitleField = this.widget('SubTitleField');
    this.subTitleField.on('propertyChange:value', this._onSubTitleFieldValueChange.bind(this));

    this.iconIdField = this.widget('IconIdField');
    this.iconIdField.on('propertyChange:value', this._onIconIdFieldValueChange.bind(this));

    this.closableField = this.widget('ClosableField');
    this.closableField.on('propertyChange:value', this._onClosableFieldValueChange.bind(this));

    this.statusField = this.widget('StatusField');
    this.statusField.on('propertyChange:value', this._onStatusFieldValueChange.bind(this));

    this.widget('WidgetActionsBox').setField(this.simpleTabBox);
    this.widget('EventsTab').setField(this.simpleTabBox);
  }

  protected _onSimpleTabBoxViewActivate(event: SimpleTabBoxViewActivateEvent) {
    this._updateSimpleTabPropertyFields();
  }

  protected _updateSimpleTabPropertyFields() {
    const activeTab = this.simpleTabBox.controller.getTab(this.simpleTabBox.currentView);

    this.titleField.setValue(activeTab?.title);
    this.subTitleField.setValue(activeTab?.subTitle);
    this.iconIdField.setValue(activeTab?.iconId);
    this.closableField.setValue(activeTab?.closable);
    this.statusField.setValue(activeTab?.status?.severity);
  }

  protected _onAddTabMenuAction(event: Event<Action>) {
    this.simpleTabBox.addView(this._createSampleTabView());
    this.simpleTabPropertiesBox.setEnabled(true);
  }

  protected _createSampleTabView() {
    const count = ++this._tabCounter;
    const view = scout.create(SimpleTabBoxViewGroupBox, {
      parent: this.simpleTabBox,
      title: 'Tab ' + count
    });
    (view as SimpleTabView).abort = () => this._removeView(view);

    return view;
  }

  protected _removeView(view: SimpleTabView) {
    this.simpleTabBox.removeView(view);

    if (this.simpleTabBox.tabArea.getTabs().length === 0) {
      this.simpleTabPropertiesBox.setEnabled(false);
      this._updateSimpleTabPropertyFields();
    }
  }

  protected _onDeleteTabMenuAction(event: Event<Action>) {
    this._removeView(this.simpleTabBox.currentView);
  }

  protected _onDisplayStyleFieldValueChange(event: PropertyChangeEvent<SimpleTabAreaDisplayStyle>) {
    this.simpleTabBox.tabArea.setDisplayStyle(event.newValue);
  }

  protected _onPositionFieldValueChange(event: PropertyChangeEvent<SimpleTabAreaPosition>) {
    this.simpleTabBox.tabArea.setPosition(event.newValue);
  }

  protected _onTitleFieldValueChange(event: PropertyChangeEvent<string>) {
    const activeTab = this.simpleTabBox.controller.getTab(this.simpleTabBox.currentView);
    activeTab?.setTitle(event.newValue);
  }

  protected _onSubTitleFieldValueChange(event: PropertyChangeEvent<string>) {
    const activeTab = this.simpleTabBox.controller.getTab(this.simpleTabBox.currentView);
    activeTab?.setSubTitle(event.newValue);
  }

  protected _onIconIdFieldValueChange(event: PropertyChangeEvent<string>) {
    const activeTab = this.simpleTabBox.controller.getTab(this.simpleTabBox.currentView);
    activeTab?.setIconId(event.newValue);
  }

  protected _onClosableFieldValueChange(event: PropertyChangeEvent<boolean>) {
    const activeTab = this.simpleTabBox.controller.getTab(this.simpleTabBox.currentView);
    activeTab?.setClosable(event.newValue);
  }

  protected _onStatusFieldValueChange(event: PropertyChangeEvent<StatusSeverity>) {
    const activeTab = this.simpleTabBox.controller.getTab(this.simpleTabBox.currentView);
    activeTab.setStatus(scout.create(Status, {
      severity: event.newValue,
      message: 'some status message..'
    }));
  }
}

export class SimpleTabBoxViewGroupBox extends GroupBox implements SimpleTabView {

  protected override _attach() {
    this.$parent.append(this.$container);
  }

  protected override _detach() {
    this.$container.detach();
  }
}
