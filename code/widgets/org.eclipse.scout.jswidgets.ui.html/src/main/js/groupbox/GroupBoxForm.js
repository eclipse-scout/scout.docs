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
import {Form, MessageBoxes, models, scout} from '@eclipse-scout/core';
import {FormFieldLookupCall} from '../index';
import GroupBoxFormModel from './GroupBoxFormModel';

export default class GroupBoxForm extends Form {

  constructor() {
    super();
    this._fieldFocusHandler = this._onFieldFocus.bind(this);
    this._fieldRenderHandler = this._onFieldRender.bind(this);
  }

  _jsonModel() {
    return models.get(GroupBoxFormModel);
  }

  _init(model) {
    super._init(model);

    var menu1 = this.widget('Menu1');
    menu1.on('action', this._onMenuAction.bind(this));

    var configurationBox = this.widget('ConfigurationBox');
    configurationBox.on('propertyChange', this._onConfigurationBoxPropertyChange.bind(this));

    var groupBox = this.widget('DetailBox');
    groupBox.on('propertyChange', this._onGroupBoxPropertyChange.bind(this));
    this._initFields(groupBox.fields);

    // GroupBox Properties tab
    this.widget('GroupBoxPropertiesBox').setField(groupBox);
    this.widget('Actions.AddMenuBarItemBox').setField(groupBox);
    this.widget('Actions.DeleteMenuBarItemBox').setField(groupBox);
    this.widget('Actions.AddFieldBox').setField(groupBox);
    this.widget('Actions.DeleteFieldBox').setField(groupBox);
    this.widget('FormFieldPropertiesBox').setField(groupBox);

    var bodyLayoutConfigBox = this.widget('BodyLayoutConfigBox');
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
    var targetField = this.widget('Field.TargetField');
    targetField.setLookupCall(new FormFieldLookupCall(groupBox));
    targetField.setValue(groupBox.fields[0]);
    targetField.on('propertyChange', this._onTargetPropertyChange.bind(this));

    this._onTargetPropertyChange({
      propertyName: 'value',
      newValue: targetField.value
    });
  }

  _initFields(fields) {
    fields.forEach(function(field) {
      field.off('render', this._fieldRenderHandler);
      field.on('render', this._fieldRenderHandler);
    }, this);
  }

  _onMenuAction(event) {
    MessageBoxes.createOk(this)
      .withBody('Menu with label \'' + event.source.text + '\' has been activated.')
      .buildAndOpen();
  }

  _onTargetPropertyChange(event) {
    if (event.propertyName === 'value') {
      var oldField = event.oldValue;
      var newField = event.newValue;

      var fieldPropertiesBox = this.widget('Field.FormFieldPropertiesBox');
      fieldPropertiesBox.setField(newField);
      fieldPropertiesBox.setEnabled(!!newField);

      var fieldGridDataBox = this.widget('Field.GridDataBox');
      fieldGridDataBox.setField(newField);
      fieldGridDataBox.setEnabled(!!newField);
      this._updateHighlightedField(newField, oldField);
    }
  }

  _updateHighlightedField(newTargetField, oldTargetField) {
    var configurationBox = this.widget('ConfigurationBox');
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

  _onFieldRender(event) {
    event.source.$field.off('focus', this._fieldFocusHandler);
    event.source.$field.on('focus', this._fieldFocusHandler);
  }

  _onFieldFocus(event) {
    var field = scout.widget(event.currentTarget);
    this.widget('Field.TargetField').setValue(field);
    this.widget('Actions.AddFieldBox').setTargetField(field);
    this.widget('Actions.DeleteFieldBox').setTargetField(field);
  }

  _onConfigurationBoxPropertyChange(event) {
    if (event.propertyName === 'selectedTab') {
      var targetField = this.widget('Field.TargetField').value;
      if (!targetField) {
        return;
      }
      this._updateHighlightedField(targetField);
    }
  }

  _onGroupBoxPropertyChange(event) {
    if (event.propertyName === 'fields') {
      this._initFields(event.source.fields);
    }
  }
}
