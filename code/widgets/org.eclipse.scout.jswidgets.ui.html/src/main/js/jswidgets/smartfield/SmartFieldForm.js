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
jswidgets.SmartFieldForm = function() {
  jswidgets.SmartFieldForm.parent.call(this);
};
scout.inherits(jswidgets.SmartFieldForm, scout.Form);

jswidgets.SmartFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.SmartFieldForm');
};

jswidgets.SmartFieldForm.prototype._init = function(model) {
  jswidgets.SmartFieldForm.parent.prototype._init.call(this, model);

  this.smartField = this.widget('SmartField');

  var newLanguageMenu = this.widget('NewLanguageMenu');
  newLanguageMenu.on('action', this._onNewLanguageMenuAction.bind(this));

  this.widget('PropertiesBox').setField(this.smartField);
  this.widget('ValueField').setEnabled(true);
  this.widget('ValueFieldPropertiesBox').setField(this.smartField);
  this.widget('FormFieldPropertiesBox').setField(this.smartField);
  this.widget('GridDataBox').setField(this.smartField);
};

jswidgets.SmartFieldForm.prototype._onNewLanguageMenuAction = function(event) {
  return scout.MessageBoxes.createOk(this)
    .withHeader(this.session.text('ThanksForClickingMe'))
    .withBody(this.session.text('NewLanguageMessage'))
    .buildAndOpen();
};

jswidgets.SmartFieldForm.prototype._onNewLanguageMenuAction = function(event) {
  return scout.MessageBoxes.createOk(this)
    .withHeader(this.session.text('ThanksForClickingMe'))
    .withBody(this.session.text('NewLanguageMessage'))
    .buildAndOpen();
};
