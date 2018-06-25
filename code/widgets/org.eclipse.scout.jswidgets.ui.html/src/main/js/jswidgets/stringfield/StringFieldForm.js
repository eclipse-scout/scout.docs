/*******************************************************************************
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
jswidgets.StringFieldForm = function() {
  jswidgets.StringFieldForm.parent.call(this);
};
scout.inherits(jswidgets.StringFieldForm, scout.Form);

jswidgets.StringFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.StringFieldForm');
};

jswidgets.StringFieldForm.prototype._init = function(model) {
  jswidgets.StringFieldForm.parent.prototype._init.call(this, model);

  var stringField = this.widget('StringField');
  stringField.on('selectionChange', this._onFieldSelectionChange.bind(this));
  stringField.on('action', this._onFieldAction.bind(this));

  var hasActionField = this.widget('HasActionField');
  hasActionField.setValue(stringField.hasActionField);
  hasActionField.on('propertyChange', this._onHasActionPropertyChange.bind(this));

  var inputMaskedField = this.widget('InputMaskedField');
  inputMaskedField.setValue(stringField.inputMasked);
  inputMaskedField.on('propertyChange', this._onInputMaskedPropertyChange.bind(this));

  var multilineTextField = this.widget('MultilineTextField');
  multilineTextField.setValue(stringField.multilineText);
  multilineTextField.on('propertyChange', this._onMultilineTextPropertyChange.bind(this));

  var spellCheckEnabledField = this.widget('SpellCheckEnabledField');
  spellCheckEnabledField.setValue(stringField.spellCheckEnabled);
  spellCheckEnabledField.on('propertyChange', this._onSpellCheckEnabledPropertyChange.bind(this));

  var trimTextField = this.widget('TrimTextField');
  trimTextField.setValue(stringField.trimText);
  trimTextField.on('propertyChange', this._onTrimTextPropertyChange.bind(this));

  var updateDisplayTextOnModifyField = this.widget('UpdateDisplayTextOnModifyField');
  updateDisplayTextOnModifyField.setValue(stringField.updateDisplayTextOnModify);
  updateDisplayTextOnModifyField.on('propertyChange', this._onUpdateDisplayTextOnModifyPropertyChange.bind(this));

  var formatField = this.widget('FormatField');
  formatField.setValue(stringField.format);
  formatField.on('propertyChange', this._onFormatPropertyChange.bind(this));

  var maxLengthField = this.widget('MaxLengthField');
  maxLengthField.setValue(stringField.maxLength);
  maxLengthField.on('propertyChange', this._onMaxLengthPropertyChange.bind(this));

  var selectionTrackingEnabledField = this.widget('SelectionTrackingEnabledField');
  selectionTrackingEnabledField.setValue(stringField.selectionTrackingEnabled);
  selectionTrackingEnabledField.on('propertyChange', this._onSelectionTrackingEnabledPropertyChange.bind(this));

  var selectionStartField = this.widget('SelectionStartField');
  selectionStartField.setValue(stringField.selectionStart);
  selectionStartField.on('propertyChange', this._onSelectionStartPropertyChange.bind(this));

  var selectionEndField = this.widget('SelectionEndField');
  selectionEndField.setValue(stringField.selectionEnd);
  selectionEndField.on('propertyChange', this._onSelectionEndPropertyChange.bind(this));

  var blockFormatField = this.widget('BlockFormatField');
  blockFormatField.setValue(stringField.format);
  blockFormatField.on('propertyChange', this._onBlockFormatPropertyChange.bind(this));

  this.widget('ValueField').setEnabled(true);
  this.widget('ValueFieldPropertiesBox').setField(stringField);
  this.widget('FormFieldPropertiesBox').setField(stringField);
  this.widget('GridDataBox').setField(stringField);
  this.widget('EventsTab').setField(stringField);
};

jswidgets.StringFieldForm.prototype._onHasActionPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('StringField').setHasAction(event.newValue);
  }
};

jswidgets.StringFieldForm.prototype._onInputMaskedPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('StringField').setInputMasked(event.newValue);
  }
};

jswidgets.StringFieldForm.prototype._onMultilineTextPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var field = this.widget('StringField');
    field.multilineText = event.newValue;
    field.parent.rerenderControls();
    // Validate layout immediately to prevent flickering
    field.parent.htmlBody.validateLayoutTree();
  }
};

jswidgets.StringFieldForm.prototype._onSpellCheckEnabledPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('StringField').setSpellCheckEnabled(event.newValue);
  }
};

jswidgets.StringFieldForm.prototype._onTrimTextPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('StringField').setTrimText(event.newValue);
  }
};

jswidgets.StringFieldForm.prototype._onUpdateDisplayTextOnModifyPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('StringField').setUpdateDisplayTextOnModify(event.newValue);
  }
};

jswidgets.StringFieldForm.prototype._onFormatPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('StringField').setFormat(event.newValue);
  }
};

jswidgets.StringFieldForm.prototype._onMaxLengthPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('StringField').setMaxLength(event.newValue);
  }
};

jswidgets.StringFieldForm.prototype._onSelectionTrackingEnabledPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('StringField').setSelectionTrackingEnabled(event.newValue);
  }
};

jswidgets.StringFieldForm.prototype._onSelectionStartPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var stringField = this.widget('StringField');
    stringField.focus();
    stringField.setSelectionStart(event.newValue);
  }
};

jswidgets.StringFieldForm.prototype._onSelectionEndPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var stringField = this.widget('StringField');
    stringField.focus();
    stringField.setSelectionEnd(event.newValue);
  }
};

jswidgets.StringFieldForm.prototype._onBlockFormatPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var stringField = this.widget('StringField');
    if (event.newValue) {
      stringField.setFormatter(jswidgets.StringFieldForm.blockFormatter);
      stringField.setParser(jswidgets.StringFieldForm.blockParser);
    } else {
      stringField.setFormatter(null);
      stringField.setParser(null);
    }
  }
};

jswidgets.StringFieldForm.prototype._onFieldAction = function(event) {
  var msgBox = scout.create('scout.MessageBox', {
    parent: this,
    yesButtonText: this.session.text('Thanks') + '!',
    body: this.session.text('StringFieldHasActionMessage')
  });
  msgBox.open();
  msgBox.on('action', function() {
    msgBox.close();
  });
};

jswidgets.StringFieldForm.prototype._onFieldSelectionChange = function(event) {
  var selectionStartField = this.widget('SelectionStartField');
  selectionStartField.setValue(event.selectionStart);
  var selectionEndField = this.widget('SelectionEndField');
  selectionEndField.setValue(event.selectionEnd);
};

jswidgets.StringFieldForm.blockFormatter = function(value, defaultFormatter) {
  var displayText = defaultFormatter(value);
  if (!displayText) {
    return displayText;
  }
  return displayText.match(/.{4}/g).join('-');
};

jswidgets.StringFieldForm.blockParser = function(displayText, defaultParser) {
  if (displayText) {
    return displayText.replace(/-/g, '');
  }
  return defaultParser(displayText);
};
