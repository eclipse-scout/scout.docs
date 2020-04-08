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
import {Form, models, scout} from '@eclipse-scout/core';
import {DisplayParentLookupCall} from '../index';
import FormFormModel from './FormFormModel';

export default class FormForm extends Form {

  constructor() {
    super();
    this.openedByButton = false;
    this.currentFormPropertiesBox = null;
    this.LifecycleData = {};
  }

  _jsonModel() {
    return models.get(FormFormModel);
  }

  _init(model) {
    super._init(model);

    this.widget('OpenFormButton').on('click', this._onOpenFormButtonClick.bind(this));
    this.widget('OpenLifecycleFormButton').on('click', this._onOpenLifecycleFormButtonClick.bind(this));

    // form properties of the form will be opened with OpenFormButton
    this.propertiesBox = this.widget('PropertiesBox');
    this.propertiesBox.setForm(scout.create('Form', {parent: this}));
    this.propertiesBox.titleField.setValue('Title');

    // form properties of current form
    this.currentFormPropertiesBox = this.widget('CurrentFormPropertiesBox');
    this.currentFormPropertiesBox.setForm(this);
    this.currentFormPropertiesBox.cacheBoundsField.setEnabled(false);
    this.currentFormPropertiesBox.displayHintField.setEnabled(false);
    this.currentFormPropertiesBox.displayViewIdField.setEnabled(false);
    this.currentFormPropertiesBox.displayParentField.setEnabled(false);
    this.currentFormPropertiesBox.resizableField.setEnabled(false);
    this.widget('CurrentFormPropertiesTab').setVisible(!this.detailForm);

    if (this.closeMenuVisible) {
      this.widget('CloseMenu').setVisible(true);
    }

    if (this.openedByButton) {
      this.widget('EventsTab').setField(this);
      this.widget('WidgetActionsBox').setField(this);
    }
  }

  _onOpenFormButtonClick(model) {
    var form = scout.create('jswidgets.FormForm', {
      parent: this,
      title: this.propertiesBox.titleField.value,
      subTitle: this.propertiesBox.subTitleField.value,
      iconId: this.propertiesBox.iconIdField.value,
      displayHint: this.propertiesBox.displayHintField.value,
      displayViewId: this.propertiesBox.displayViewIdField.value,
      displayParent: DisplayParentLookupCall.displayParentForType(this, this.propertiesBox.displayParentField.value),
      modal: this.propertiesBox.modalField.value,
      askIfNeedSave: this.propertiesBox.askIfNeedSaveField.value,
      cacheBounds: this.propertiesBox.cacheBoundsField.value,
      closable: this.propertiesBox.closableField.value,
      resizable: this.propertiesBox.resizableField.value,
      openedByButton: true,
      closeMenuVisible: true
    });
    this.widget('EventsTab').setField(form);
    this.widget('WidgetActionsBox').setField(form);
    form.open();
  }

  _onOpenLifecycleFormButtonClick(model) {
    var form = scout.create('jswidgets.LifecycleForm', {
      parent: this,
      title: this.propertiesBox.titleField.value,
      subTitle: this.propertiesBox.subTitleField.value,
      iconId: this.propertiesBox.iconIdField.value,
      displayHint: this.propertiesBox.displayHintField.value,
      displayParent: DisplayParentLookupCall.displayParentForType(this, this.propertiesBox.displayParentField.value),
      modal: this.propertiesBox.modalField.value,
      closable: this.propertiesBox.closableField.value,
      resizable: this.propertiesBox.resizableField.value,
      data: this.LifecycleData
    });
    this.widget('EventsTab').setField(form);

    var lifecycleDataField = this.widget('LifecycleDataField');
    form.on('load', function(event) {
      var data = form.data;
      var text = 'Form loaded (' + this.lifecycleDataToString(data) + ')';
      lifecycleDataField.setValue(text);
    }.bind(this));
    form.on('save', function(event) {
      var data = form.data;
      var text = lifecycleDataField.value;
      text += '\n' + 'Form saved (' + this.lifecycleDataToString(data) + ')';
      lifecycleDataField.setValue(text);
    }.bind(this));
    form.on('reset', function(event) {
      var data = form.data;
      var text = lifecycleDataField.value;
      text += '\n' + 'Form reset (' + this.lifecycleDataToString(data) + ')';
      lifecycleDataField.setValue(text);
    }.bind(this));
    form.on('close', function(event) {
      var data = form.data;
      var text = lifecycleDataField.value;
      text += '\n' + 'Form closed (' + this.lifecycleDataToString(data) + ')';
      lifecycleDataField.setValue(text);
      this.lifecycleData = data;
    }.bind(this));

    lifecycleDataField.setVisible(true);
    form.open();
  }

  lifecycleDataToString(data) {
    return 'Name: ' + data.name + ', Birthday: ' + data.birthday;
  }
}
