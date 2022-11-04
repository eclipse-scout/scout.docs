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
import {Event, EventHandler, Form, FormField, FormModel, InitModelOf, Menu, MessageBoxes, models, scout} from '@eclipse-scout/core';
import {FormFieldLookupCall, GroupBoxFormWidgetMap} from '../index';
import GroupBoxFormModel from './GroupBoxFormModel';

export class GroupBoxForm extends Form {
  declare widgetMap: GroupBoxFormWidgetMap;

  protected _fieldFocusHandler: (event: JQuery.FocusEvent) => void;
  protected _fieldRenderHandler: EventHandler<Event<FormField>>;

  constructor() {
    super();
    this._fieldFocusHandler = this._onFieldFocus.bind(this);
    this._fieldRenderHandler = this._onFieldRender.bind(this);
  }

  protected override _jsonModel(): FormModel {
    return models.get(GroupBoxFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let menu1 = this.widget('Menu1');
    menu1.on('action', this._onMenuAction.bind(this));

    let configurationBox = this.widget('ConfigurationBox');
    configurationBox.on('propertyChange:selectedTab', event => {
      let targetField = this.widget('Field.TargetField').value;
      if (!targetField) {
        return;
      }
      this._updateHighlightedField(targetField);
    });

    let groupBox = this.widget('DetailBox');
    groupBox.on('propertyChange:fields', event => this._initFields(event.source.fields));
    this._initFields(groupBox.fields);

    // GroupBox Properties tab
    this.widget('GroupBoxPropertiesBox').setField(groupBox);
    this.widget('Actions.AddMenuBox').setField(groupBox);
    this.widget('Actions.DeleteMenuBox').setField(groupBox);
    this.widget('Actions.AddFieldBox').setField(groupBox);
    this.widget('Actions.DeleteFieldBox').setField(groupBox);
    this.widget('FormFieldPropertiesBox').setField(groupBox);

    let bodyLayoutConfigBox = this.widget('BodyLayoutConfigBox');
    bodyLayoutConfigBox.getLayoutConfig = function() {
      return this.field.bodyLayoutConfig;
    };
    bodyLayoutConfigBox.setLayoutConfig = function(layoutConfig) {
      this.field.setBodyLayoutConfig(layoutConfig);
    };
    bodyLayoutConfigBox.setField(groupBox);
    this.widget('GridDataBox').setField(groupBox);
    this.widget('WidgetActionsBox').setField(groupBox);
    this.widget('EventsTab').setField(groupBox);

    // Field tab
    let targetField = this.widget('Field.TargetField');
    targetField.setLookupCall(new FormFieldLookupCall(groupBox));
    targetField.on('propertyChange:value', event => {
      let oldField = event.oldValue;
      let newField = event.newValue;

      let fieldPropertiesBox = this.widget('Field.FormFieldPropertiesBox');
      fieldPropertiesBox.setField(newField);
      fieldPropertiesBox.setEnabled(!!newField);

      let fieldGridDataBox = this.widget('Field.GridDataBox');
      fieldGridDataBox.setField(newField);
      fieldGridDataBox.setEnabled(!!newField);
      this._updateHighlightedField(newField, oldField);
    });
    targetField.setValue(groupBox.fields[0]);
  }

  protected _initFields(fields: FormField[]) {
    fields.forEach(function(field) {
      field.off('render', this._fieldRenderHandler);
      field.on('render', this._fieldRenderHandler);
    }, this);
  }

  protected _onMenuAction(event: Event<Menu>) {
    MessageBoxes.createOk(this)
      .withBody('Menu with label \'' + event.source.text + '\' has been activated.')
      .buildAndOpen();
  }

  protected _updateHighlightedField(newTargetField: FormField, oldTargetField?: FormField) {
    let configurationBox = this.widget('ConfigurationBox');
    if (oldTargetField) {
      oldTargetField.removeCssClass('field-highlighted');
    }
    if (!newTargetField) {
      return;
    }

    // Only highlight field if field properties or actions tab is selected
    if (scout.isOneOf(configurationBox.selectedTab.id, 'FieldPropertiesTab', 'ActionsTab')) {
      newTargetField.addCssClass('field-highlighted');
    } else {
      newTargetField.removeCssClass('field-highlighted');
    }
  }

  protected _onFieldRender(event: Event<FormField>) {
    event.source.$field.off('focus', this._fieldFocusHandler);
    event.source.$field.on('focus', this._fieldFocusHandler);
  }

  protected _onFieldFocus(event: JQuery.FocusEvent) {
    let field: FormField = scout.widget(event.currentTarget);
    this.widget('Field.TargetField').setValue(field);
    this.widget('Actions.AddFieldBox').setTargetField(field);
    this.widget('Actions.DeleteFieldBox').setTargetField(field);
  }
}
