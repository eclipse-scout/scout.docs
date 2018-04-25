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
jswidgets.SequenceBoxForm = function() {
  jswidgets.SequenceBoxForm.parent.call(this);
};
scout.inherits(jswidgets.SequenceBoxForm, scout.Form);

jswidgets.SequenceBoxForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.SequenceBoxForm');
};

jswidgets.SequenceBoxForm.prototype._init = function(model) {
  jswidgets.SequenceBoxForm.parent.prototype._init.call(this, model);

  var sequenceBox = this.widget('SequenceBox');

  // sequence box properties tab
  var formFieldPropertiesBox = this.widget('FormFieldPropertiesBox');
  formFieldPropertiesBox.setField(sequenceBox);
  this.widget('GridDataBox').setField(sequenceBox);
  this.widget('LayoutConfigBox').setField(sequenceBox);
  this.widget('EventsTab').setField(sequenceBox);

  // FieldProperties tab
  var targetField = this.widget('Field.TargetField');
  targetField.setLookupCall(new jswidgets.FormFieldLookupCall(sequenceBox));
  targetField.on('propertyChange', this._onTargetPropertyChange.bind(this));
  targetField.setValue(sequenceBox.fields[0]);

  this._onTargetPropertyChange({
    propertyName: 'value',
    newValue: targetField.value
  });
};

jswidgets.SequenceBoxForm.prototype._onTargetPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var targetField = event.newValue;

    var fieldPropertiesBox = this.widget('Field.FormFieldPropertiesBox');
    fieldPropertiesBox.setField(targetField);
    fieldPropertiesBox.setEnabled(!!targetField);

    var fieldGridDataBox = this.widget('Field.GridDataBox');
    fieldGridDataBox.setField(targetField);
    fieldGridDataBox.setEnabled(!!targetField);
  }
};
