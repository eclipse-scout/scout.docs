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
jswidgets.FormFieldTypeLookupCall = function() {
  jswidgets.FormFieldTypeLookupCall.parent.call(this);
};
scout.inherits(jswidgets.FormFieldTypeLookupCall, scout.StaticLookupCall);

jswidgets.FormFieldTypeLookupCall.prototype._data = function() {
  return jswidgets.FormFieldTypeLookupCall.DATA;
};

jswidgets.FormFieldTypeLookupCall.DATA = [
  ['StringField', 'String Field'],
  ['NumberField', 'Number Field'],
  ['DateField', 'Date Field'],
  ['SmartField', 'Smart Field'],
  ['BooleanField', 'Checkbox']
];
