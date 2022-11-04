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
import {Event, Form, FormModel, GroupBox, InitModelOf, MessageBox, models, scout, StringField, StringFieldSelectionChangeEvent, ValueFieldFormatter, ValueFieldParser} from '@eclipse-scout/core';
import StringFieldFormModel from './StringFieldFormModel';
import {StringFieldFormWidgetMap} from '../index';

export class StringFieldForm extends Form {
  declare widgetMap: StringFieldFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(StringFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let stringField = this.widget('StringField');
    stringField.on('selectionChange', this._onFieldSelectionChange.bind(this));
    stringField.on('action', this._onFieldAction.bind(this));

    let hasActionField = this.widget('HasActionField');
    hasActionField.setValue(stringField.hasAction);
    hasActionField.on('propertyChange:value', event => this.widget('StringField').setHasAction(event.newValue));

    let inputMaskedField = this.widget('InputMaskedField');
    inputMaskedField.setValue(stringField.inputMasked);
    inputMaskedField.on('propertyChange:value', event => this.widget('StringField').setInputMasked(event.newValue));

    let multilineTextField = this.widget('MultilineTextField');
    multilineTextField.setValue(stringField.multilineText);
    multilineTextField.on('propertyChange:value', event => {
      let field = this.widget('StringField');
      field.setMultilineText(event.newValue);
      (field.parent as GroupBox).rerenderControls();
    });

    let spellCheckEnabledField = this.widget('SpellCheckEnabledField');
    spellCheckEnabledField.setValue(stringField.spellCheckEnabled);
    spellCheckEnabledField.on('propertyChange:value', event => this.widget('StringField').setSpellCheckEnabled(event.newValue));

    let trimTextField = this.widget('TrimTextField');
    trimTextField.setValue(stringField.trimText);
    trimTextField.on('propertyChange:value', event => this.widget('StringField').setTrimText(event.newValue));

    let updateDisplayTextOnModifyField = this.widget('UpdateDisplayTextOnModifyField');
    updateDisplayTextOnModifyField.setValue(stringField.updateDisplayTextOnModify);
    updateDisplayTextOnModifyField.on('propertyChange:value', event => this.widget('StringField').setUpdateDisplayTextOnModify(event.newValue));

    let formatField = this.widget('FormatField');
    formatField.setValue(stringField.format);
    formatField.on('propertyChange:value', event => this.widget('StringField').setFormat(event.newValue));

    let maxLengthField = this.widget('MaxLengthField');
    maxLengthField.setValue(stringField.maxLength);
    maxLengthField.on('propertyChange:value', event => this.widget('StringField').setMaxLength(event.newValue));

    let selectionTrackingEnabledField = this.widget('SelectionTrackingEnabledField');
    selectionTrackingEnabledField.setValue(stringField.selectionTrackingEnabled);
    selectionTrackingEnabledField.on('propertyChange:value', event => this.widget('StringField').setSelectionTrackingEnabled(event.newValue));

    let selectionStartField = this.widget('SelectionStartField');
    selectionStartField.setValue(stringField.selectionStart);
    selectionStartField.on('propertyChange:value', event => {
      let stringField = this.widget('StringField');
      stringField.focus();
      stringField.setSelectionStart(event.newValue);
    });

    let selectionEndField = this.widget('SelectionEndField');
    selectionEndField.setValue(stringField.selectionEnd);
    selectionEndField.on('propertyChange:value', event => {
      let stringField = this.widget('StringField');
      stringField.focus();
      stringField.setSelectionEnd(event.newValue);
    });

    let blockFormatField = this.widget('BlockFormatField');
    blockFormatField.setValue(!!stringField.format);
    blockFormatField.on('propertyChange:value', event => {
      let stringField = this.widget('StringField');
      if (event.newValue) {
        stringField.setFormatter(StringFieldForm.blockFormatter);
        stringField.setParser(StringFieldForm.blockParser);
      } else {
        stringField.setFormatter(null);
        stringField.setParser(null);
      }
    });

    this.widget('ValueField').setEnabled(true);
    this.widget('ValueFieldPropertiesBox').setField(stringField);
    this.widget('FormFieldPropertiesBox').setField(stringField);
    this.widget('GridDataBox').setField(stringField);
    this.widget('WidgetActionsBox').setField(stringField);
    this.widget('FormFieldActionsBox').setField(stringField);
    this.widget('EventsTab').setField(stringField);
  }

  protected _onFieldAction(event: Event<StringField>) {
    let msgBox = scout.create(MessageBox, {
      parent: this,
      yesButtonText: this.session.text('Thanks') + '!',
      body: this.session.text('StringFieldHasActionMessage')
    });
    msgBox.open();
    msgBox.on('action', () => {
      msgBox.close();
    });
  }

  protected _onFieldSelectionChange(event: StringFieldSelectionChangeEvent) {
    let selectionStartField = this.widget('SelectionStartField');
    selectionStartField.setValue(event.selectionStart);
    let selectionEndField = this.widget('SelectionEndField');
    selectionEndField.setValue(event.selectionEnd);
  }

  static blockFormatter(value: string, defaultFormatter: ValueFieldFormatter<string>): string {
    let displayText = defaultFormatter(value) as string;
    if (!displayText) {
      return displayText;
    }
    return displayText.match(/.{4}/g).join('-');
  }

  static blockParser(displayText: string, defaultParser: ValueFieldParser<string>): string {
    if (displayText) {
      return displayText.replace(/-/g, '');
    }
    return defaultParser(displayText);
  }
}
