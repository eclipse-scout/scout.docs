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
import ButtonFormModel from './ButtonFormModel';
import {Button, Form, models, scout, Status} from '@eclipse-scout/core';

export default class ButtonForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(ButtonFormModel);
  }

  // noinspection DuplicatedCode
  _init(model) {
    super._init(model);

    let button = this.widget('Button');
    button.on('click', this._onButtonClick.bind(this));

    let defaultButtonField = this.widget('DefaultButtonField');
    defaultButtonField.setValue(button.defaultButton);
    defaultButtonField.on('propertyChange:value', event => this.widget('Button').setDefaultButton(event.newValue));

    let processButtonField = this.widget('ProcessButtonField');
    processButtonField.setValue(button.processButton);
    processButtonField.on('propertyChange:value', event => {
      let button = this.widget('Button');
      // ProcessButton property may not be changed during run time officially
      // As a workaround we need to adjust the group box state and rerender the whole group box
      // DISCLAIMER: This is done for demo purpose, don't use it in production code since it is internal API
      button.processButton = event.newValue;
      button.parent._prepareFields();
      button.parent._updateMenuBar();
      this._rerenderGroupBox(button.parent);
    });

    let selectedField = this.widget('SelectedField');
    selectedField.setValue(button.selected);
    selectedField.on('propertyChange:value', event => this.widget('Button').setSelected(event.newValue));

    let preventDoubleClickField = this.widget('PreventDoubleClickField');
    preventDoubleClickField.setValue(button.preventDoubleClick);
    preventDoubleClickField.on('propertyChange:value', event => this.widget('Button').setPreventDoubleClick(event.newValue));

    let iconIdField = this.widget('IconIdField');
    iconIdField.setValue(button.iconId);
    iconIdField.on('propertyChange:value', event => this.widget('Button').setIconId(event.newValue));

    let keyStrokeField = this.widget('KeyStrokeField');
    keyStrokeField.setValue(button.keyStroke);
    keyStrokeField.on('propertyChange:value', event => this.widget('Button').setKeyStroke(event.newValue));

    let displayStyleField = this.widget('DisplayStyleField');
    displayStyleField.setValue(button.displayStyle);
    displayStyleField.on('propertyChange:value', event => {
      let button = this.widget('Button');
      // DisplayStyle may not be changed during run time officially, use this little hack to work around by rerendering the whole group box
      button.displayStyle = event.newValue;
      this._rerenderGroupBox(button.parent);
    });

    this.widget('FormFieldPropertiesBox').setField(button);
    this.widget('GridDataBox').setField(button);
    this.widget('WidgetActionsBox').setField(button);
    this.widget('FormFieldActionsBox').setField(button);
    this.widget('EventsTab').setField(button);
  }

  _onButtonClick(event) {
    if (event.source.displayStyle === Button.DisplayStyle.TOGGLE) {
      // Don't show message box if it is a toggle button
      return;
    }
    scout.create('DesktopNotification', {
      parent: this,
      duration: 7000,
      status: {
        severity: Status.Severity.OK,
        message: this.session.text('ButtonClickMessage')
      }
    }).show();
  }

  _rerenderGroupBox(groupBox) {
    let mainBox = groupBox.parent;
    // Owner is the main box by default.
    // In that case the group box would be destroyed when calling deleteField and therefore could not be used anymore -> Set form as owner temporarily.
    groupBox.setOwner(this);
    mainBox.deleteField(groupBox);
    mainBox.insertField(groupBox, 0);
    groupBox.setOwner(mainBox);
  }
}
