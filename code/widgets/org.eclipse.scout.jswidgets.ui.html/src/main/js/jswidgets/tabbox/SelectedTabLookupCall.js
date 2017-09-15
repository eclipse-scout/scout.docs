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
jswidgets.SelectedTabLookupCall = function() {
  jswidgets.SelectedTabLookupCall.parent.call(this);
};
scout.inherits(jswidgets.SelectedTabLookupCall, scout.StaticLookupCall);

jswidgets.SelectedTabLookupCall.prototype._data = function() {
  return jswidgets.SelectedTabLookupCall.DATA;
};

jswidgets.SelectedTabLookupCall.DATA = [
  ['TabItem1', 'First Tab'],
  ['TabItem2', 'Second Tab']
];
