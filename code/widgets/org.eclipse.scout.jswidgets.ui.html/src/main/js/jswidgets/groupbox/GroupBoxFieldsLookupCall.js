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
jswidgets.GroupBoxFieldsLookupCall = function() {
  jswidgets.GroupBoxFieldsLookupCall.parent.call(this);
};
scout.inherits(jswidgets.GroupBoxFieldsLookupCall, scout.StaticLookupCall);

jswidgets.GroupBoxFieldsLookupCall.prototype._data = function() {
  return jswidgets.GroupBoxFieldsLookupCall.DATA;
};

jswidgets.GroupBoxFieldsLookupCall.DATA = [
  ['String Field 1', 'StringField1'],
  ['String Field 2', 'StringField2'],
  ['String Field 3', 'StringField3'],
  ['String Field 4', 'StringField4']
];
