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
jswidgets.FieldStyleLookupCall = function() {
  jswidgets.FieldStyleLookupCall.parent.call(this);
};
scout.inherits(jswidgets.FieldStyleLookupCall, scout.StaticLookupCall);

jswidgets.FieldStyleLookupCall.prototype._data = function() {
  return jswidgets.FieldStyleLookupCall.DATA;
};

jswidgets.FieldStyleLookupCall.DATA = [
  [scout.FormField.FieldStyle.CLASSIC, 'Classic'],
  [scout.FormField.FieldStyle.ALTERNATIVE, 'Alternative']
];
