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
jswidgets.MultilineSmartFieldForm = function () {
  jswidgets.MultilineSmartFieldForm.parent.call(this);
};
scout.inherits(jswidgets.MultilineSmartFieldForm, scout.Form);

jswidgets.MultilineSmartFieldForm.prototype._jsonModel = function () {
  return scout.models.getModel('jswidgets.MultilineSmartFieldForm');
};

jswidgets.MultilineSmartFieldForm.prototype._init = function (model) {
  jswidgets.MultilineSmartFieldForm.parent.prototype._init.call(this, model);

  this.smartField = this.widget('MultilineSmartField');

  this.widget('SmartFieldPropertiesBox').setField(this.smartField);
  this.widget('LookupCallField').setVisible(false);
  this.widget('ValueFieldPropertiesBox').setField(this.smartField);
  this.widget('FormFieldPropertiesBox').setField(this.smartField);
  this.widget('GridDataBox').setField(this.smartField);
  this.widget('WidgetActionsBox').setField(this.smartField);
  this.widget('EventsTab').setField(this.smartField);
};
