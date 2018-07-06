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
jswidgets.CustomFieldForm = function() {
  jswidgets.CustomFieldForm.parent.call(this);
};
scout.inherits(jswidgets.CustomFieldForm, scout.Form);

jswidgets.CustomFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.CustomFieldForm');
};

jswidgets.CustomFieldForm.prototype._init = function(model) {
  jswidgets.CustomFieldForm.parent.prototype._init.call(this, model);
  var watchField = this.widget('WatchField');

  this.widget('FormFieldPropertiesBox').setField(watchField);
  this.widget('GridDataBox').setField(watchField);
  this.widget('WidgetActionsBox').setField(watchField);
  this.widget('EventsTab').setField(watchField);
};

jswidgets.CustomFieldForm.prototype._onSelectedTabChange = function(event) {
  if (event.propertyName === 'value') {
    if (event.newValue) {
      this.widget('TabBox').selectTabById(event.newValue);
    } else {
      this.widget('TabBox').setSelectedTab();
    }
  }
};

jswidgets.CustomFieldForm.prototype._onFieldPropertyChange = function(event) {
  if (event.propertyName === 'selectedTab') {
    this.widget('SelectedTabField').setValue((event.newValue) ? (event.newValue.id) : (null));
  }
};
