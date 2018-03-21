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
jswidgets.StatusPositionLookupCall = function() {
  jswidgets.StatusPositionLookupCall.parent.call(this);
};
scout.inherits(jswidgets.StatusPositionLookupCall, scout.StaticLookupCall);

jswidgets.StatusPositionLookupCall.prototype._data = function() {
  return jswidgets.StatusPositionLookupCall.DATA;
};

jswidgets.StatusPositionLookupCall.DATA = [
  [scout.FormField.StatusPosition.DEFAULT, 'default'],
  [scout.FormField.StatusPosition.TOP, 'top']
];
