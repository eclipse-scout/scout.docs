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
jswidgets.ButtonForm = function() {
  jswidgets.ButtonForm.parent.call(this);
};
scout.inherits(jswidgets.ButtonForm, scout.Form);

jswidgets.ButtonForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.ButtonForm');
};

jswidgets.ButtonForm.prototype._init = function(model) {
  jswidgets.ButtonForm.parent.prototype._init.call(this, model);

  var button = this.widget('Button');
  button.on('click', this._onButtonClick.bind(this));

  var defaultButtonField = this.widget('DefaultButtonField');
  defaultButtonField.setValue(button.defaultButtonField);
  defaultButtonField.on('propertyChange', this._onDefaultButtonPropertyChange.bind(this));

  var processButtonField = this.widget('ProcessButtonField');
  processButtonField.setValue(button.processButtonField);
  processButtonField.on('propertyChange', this._onProcessButtonPropertyChange.bind(this));

  var selectedField = this.widget('SelectedField');
  selectedField.setValue(button.selectedField);
  selectedField.on('propertyChange', this._onSelectedPropertyChange.bind(this));

  var htmlEnabledField = this.widget('HtmlEnabledField');
  htmlEnabledField.setValue(button.htmlEnabled);
  htmlEnabledField.on('propertyChange', this._onHtmlEnabledPropertyChange.bind(this));

  var iconIdField = this.widget('IconIdField');
  iconIdField.setValue(button.iconId);
  iconIdField.on('propertyChange', this._onIconIdPropertyChange.bind(this));

  var keyStrokeField = this.widget('KeyStrokeField');
  keyStrokeField.setValue(button.keyStroke);
  keyStrokeField.on('propertyChange', this._onKeyStrokePropertyChange.bind(this));

  var displayStyleField = this.widget('DisplayStyleField');
  displayStyleField.setValue(button.displayStyle);
  displayStyleField.on('propertyChange', this._onDisplayStylePropertyChange.bind(this));

  this.widget('FormFieldPropertiesBox').setField(button);
  this.widget('GridDataBox').setField(button);
  this.widget('WidgetActionsBox').setField(button);
  this.widget('EventsTab').setField(button);
};

jswidgets.ButtonForm.prototype._onButtonClick = function(event) {
  if (event.source.displayStyle === scout.Button.DisplayStyle.TOGGLE) {
    // Don't show message box if it is a toggle button
    return;
  }
  var msgBox = scout.create('scout.MessageBox', {
    parent: this,
    yesButtonText: this.session.text('Thanks') + '!',
    body: this.session.text('ButtonClickMessage')
  });
  msgBox.open();
  msgBox.on('action', function() {
    msgBox.close();
  });
};

jswidgets.ButtonForm.prototype._onDefaultButtonPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('Button').setDefaultButton(event.newValue);
  }
};

jswidgets.ButtonForm.prototype._onProcessButtonPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var button = this.widget('Button');
    // ProcessButton property may not be changed during run time officially
    // As a work around we need to adjust the group box state and rerender the whole group box
    // DISCLAIMER: This is done for demo purpose, don't use it in production code since it is internal API
    button.processButton = event.newValue;
    button.parent._prepareFields();
    button.parent._updateMenuBar();
    this._rerenderGroupBox(button.parent);
  }
};

jswidgets.ButtonForm.prototype._onSelectedPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('Button').setSelected(event.newValue);
  }
};

jswidgets.ButtonForm.prototype._onHtmlEnabledPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('Button').setHtmlEnabled(event.newValue);
  }
};

jswidgets.ButtonForm.prototype._onIconIdPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('Button').setIconId(event.newValue);
  }
};

jswidgets.ButtonForm.prototype._onKeyStrokePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.widget('Button').setKeyStroke(event.newValue);
  }
};

jswidgets.ButtonForm.prototype._onDisplayStylePropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var button = this.widget('Button');
    // DisplayStyle may not be changed during run time officially, use this little hack to work around by rerendering the whole group box
    button.displayStyle = event.newValue;
    this._rerenderGroupBox(button.parent);
  }
};

jswidgets.ButtonForm.prototype._rerenderGroupBox = function(groupBox) {
  var mainBox = groupBox.parent;
  // Owner is the main box by default.
  // In that case the group box would be destroyed when calling deleteField and therefore could not be used anymore -> Set form as owner temporarily.
  groupBox.setOwner(this);
  mainBox.deleteField(groupBox);
  mainBox.insertField(groupBox, 0);
  groupBox.setOwner(mainBox);
  // Validate layout immediately to prevent flickering
  mainBox.validateLayoutTree();
};
