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
jswidgets.SalutationLookupCall = function() {
  jswidgets.SalutationLookupCall.parent.call(this);
};
scout.inherits(jswidgets.SalutationLookupCall, scout.StaticLookupCall);

jswidgets.SalutationLookupCall.prototype._data = function() {
  return jswidgets.SalutationLookupCall.DATA;
};

jswidgets.SalutationLookupCall.DATA = [
  ['female', 'Weiblich'],
  ['male', 'Männlich'],
  ['impersonal', 'Unpersönlich'],
  ['unknown', 'Unbekannt']
];

/**
 * Use the key property as cssClass. Used to demonstrate styling possibilities with lookup-rows in smart-fields.
 */
jswidgets.SalutationLookupCall.prototype._dataToLookupRow = function(data) {
  var lookupRow = jswidgets.SalutationLookupCall.parent.prototype._dataToLookupRow.call(this, data);
  lookupRow.cssClass = lookupRow.key;
  return lookupRow;
};
