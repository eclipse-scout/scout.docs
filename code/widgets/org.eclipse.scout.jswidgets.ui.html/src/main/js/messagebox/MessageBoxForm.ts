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
import {Button, CheckBoxField, Event, Form, FormModel, InitModelOf, MessageBoxes, models, SmartField, Status, StatusSeverity, StringField, strings} from '@eclipse-scout/core';
import MessageBoxFormModel from './MessageBoxFormModel';
import {MessageBoxFormWidgetMap} from '../index';

export class MessageBoxForm extends Form {
  declare widgetMap: MessageBoxFormWidgetMap;

  button: Button;
  severityField: SmartField<StatusSeverity>;
  headerField: StringField;
  iconField: SmartField<string>;
  bodyField: StringField;
  htmlField: CheckBoxField;
  yesButtonTextField: StringField;
  noButtonTextField: StringField;
  cancelButtonTextField: StringField;
  resultField: StringField;

  constructor() {
    super();
    this.button = null;
    this.severityField = null;
    this.headerField = null;
    this.iconField = null;
    this.bodyField = null;
    this.htmlField = null;
    this.yesButtonTextField = null;
    this.noButtonTextField = null;
    this.cancelButtonTextField = null;
    this.resultField = null;
  }

  protected override _jsonModel(): FormModel {
    return models.get(MessageBoxFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.button = this.widget('Button');
    this.button.on('click', this._onButtonClick.bind(this));
    this.severityField = this.widget('SeverityField');
    this.headerField = this.widget('HeaderField');
    this.iconField = this.widget('IconField');
    this.bodyField = this.widget('BodyField');
    this.htmlField = this.widget('HtmlField');
    this.yesButtonTextField = this.widget('YesButtonTextField');
    this.noButtonTextField = this.widget('NoButtonTextField');
    this.cancelButtonTextField = this.widget('CancelButtonTextField');
    this.resultField = this.widget('ResultField');

    this._initDefaults();
  }

  protected _initDefaults() {
    this.severityField.setValue(Status.Severity.INFO);
    this.headerField.setValue('Lorem Ipsum');
    this.bodyField.setValue('Please answer the question using the appropriate button.');
    this.yesButtonTextField.setValue('Yes');
    this.noButtonTextField.setValue('No');
    this.cancelButtonTextField.setValue('Cancel');
  }

  protected _onButtonClick(event: Event<Button>) {
    let box = MessageBoxes.create(this)
      .withSeverity(this.severityField.value)
      .withHeader(this.headerField.value)
      .withBody(this.bodyField.value, this.htmlField.value)
      .withIconId(this.iconField.value);

    let yesButtonText = this.yesButtonTextField.value;
    if (strings.hasText(yesButtonText)) {
      box.withYes(yesButtonText);
    }

    let noButtonText = this.noButtonTextField.value;
    if (strings.hasText(noButtonText)) {
      box.withNo(noButtonText);
    }

    let cancelButtonText = this.cancelButtonTextField.value;
    if (strings.hasText(cancelButtonText)) {
      box.withCancel(cancelButtonText);
    }

    box.buildAndOpen()
      .then(result => this.resultField.setValue(result));
  }
}

