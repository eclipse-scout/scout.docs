/*
 * Copyright (c) 2023 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
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
    this.closeMenuVisible = false;
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
    let form = scout.create('jswidgets.FormForm', $.extend({
      parent: this,
      openedByButton: true,
      closeMenuVisible: true
    }, this._settings()));
    this.widget('EventsTab').setField(form);
    this.widget('WidgetActionsBox').setField(form);
    form.open();
  }

  _onOpenLifecycleFormButtonClick(model) {
    let form = scout.create('jswidgets.LifecycleForm', $.extend({
      parent: this,
      data: this.LifecycleData
    }, this._settings()));
    this.widget('EventsTab').setField(form);

    let lifecycleDataField = this.widget('LifecycleDataField');
    form.on('load', event => {
      let data = form.data;
      let text = 'Form loaded (' + this.lifecycleDataToString(data) + ')';
      lifecycleDataField.setValue(text);
    });
    form.on('save', event => {
      let data = form.data;
      let text = lifecycleDataField.value;
      text += '\n' + 'Form saved (' + this.lifecycleDataToString(data) + ')';
      lifecycleDataField.setValue(text);
    });
    form.on('reset', event => {
      let data = form.data;
      let text = lifecycleDataField.value;
      text += '\n' + 'Form reset (' + this.lifecycleDataToString(data) + ')';
      lifecycleDataField.setValue(text);
    });
    form.on('close', event => {
      let data = form.data;
      let text = lifecycleDataField.value;
      text += '\n' + 'Form closed (' + this.lifecycleDataToString(data) + ')';
      lifecycleDataField.setValue(text);
      this.lifecycleData = data;
    });

    lifecycleDataField.setVisible(true);
    form.open();
  }

  lifecycleDataToString(data) {
    return 'Name: ' + data.name + ', Birthday: ' + data.birthday;
  }

  _settings() {
    return {
      title: this.propertiesBox.titleField.value,
      subTitle: this.propertiesBox.subTitleField.value,
      notificationCount: this.propertiesBox.notificationCountField.value,
      iconId: this.propertiesBox.iconIdField.value,
      displayHint: this.propertiesBox.displayHintField.value,
      displayViewId: this.propertiesBox.displayViewIdField.value,
      displayParent: DisplayParentLookupCall.displayParentForType(this, this.propertiesBox.displayParentField.value),
      modal: this.propertiesBox.modalField.value,
      headerVisible: this.propertiesBox.headerVisibleField.value,
      askIfNeedSave: this.propertiesBox.askIfNeedSaveField.value,
      cacheBounds: this.propertiesBox.cacheBoundsField.value,
      closable: this.propertiesBox.closableField.value,
      movable: this.propertiesBox.movableField.value,
      resizable: this.propertiesBox.resizableField.value,
      maximized: this.propertiesBox.maximizedField.value
    };
  }
}
