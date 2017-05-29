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
jswidgets.DisabledStyleLookupCall = function() {
  jswidgets.DisabledStyleLookupCall.parent.call(this);
};
scout.inherits(jswidgets.DisabledStyleLookupCall, scout.StaticLookupCall);

jswidgets.DisabledStyleLookupCall.prototype._data = function() {
  return jswidgets.DisabledStyleLookupCall.DATA;
};

jswidgets.DisabledStyleLookupCall.DATA = [
  ['default', scout.Widget.DisabledStyle.DEFAULT],
  ['read only', scout.Widget.DisabledStyle.READ_ONLY]
];
