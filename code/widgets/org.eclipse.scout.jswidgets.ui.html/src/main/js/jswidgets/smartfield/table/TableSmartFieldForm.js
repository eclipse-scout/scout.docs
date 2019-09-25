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
jswidgets.TableSmartFieldForm = function() {
  jswidgets.TableSmartFieldForm.parent.call(this);
};
scout.inherits(jswidgets.TableSmartFieldForm, scout.Form);

jswidgets.TableSmartFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TableSmartFieldForm');
};

jswidgets.TableSmartFieldForm.prototype._init = function(model) {
  jswidgets.TableSmartFieldForm.parent.prototype._init.call(this, model);

  this.smartField = this.widget('TableSmartField');

  this.widget('SmartFieldPropertiesBox').setField(this.smartField);
  this.widget('LookupCallField').setVisible(false);
  this.widget('ValueFieldPropertiesBox').setField(this.smartField);
  this.widget('FormFieldPropertiesBox').setField(this.smartField);
  this.widget('GridDataBox').setField(this.smartField);
  this.widget('WidgetActionsBox').setField(this.smartField);
  this.widget('EventsTab').setField(this.smartField);
};
