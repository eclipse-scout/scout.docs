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
jswidgets.ButtonDisplayStyleLookupCall = function() {
  jswidgets.ButtonDisplayStyleLookupCall.parent.call(this);
};
scout.inherits(jswidgets.ButtonDisplayStyleLookupCall, scout.StaticLookupCall);

jswidgets.ButtonDisplayStyleLookupCall.prototype._data = function() {
  return jswidgets.ButtonDisplayStyleLookupCall.DATA;
};

jswidgets.ButtonDisplayStyleLookupCall.DATA = [
  [scout.Button.DisplayStyle.DEFAULT, 'default'],
  [scout.Button.DisplayStyle.TOGGLE, 'toggle'],
  [scout.Button.DisplayStyle.LINK, 'link']
];
