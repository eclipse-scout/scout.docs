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
jswidgets.ActionStyleLookupCall = function() {
  jswidgets.ActionStyleLookupCall.parent.call(this);
};
scout.inherits(jswidgets.ActionStyleLookupCall, scout.StaticLookupCall);

jswidgets.ActionStyleLookupCall.prototype._data = function() {
  return jswidgets.ActionStyleLookupCall.DATA;
};

jswidgets.ActionStyleLookupCall.DATA = [
  [scout.Action.ActionStyle.DEFAULT, 'default'],
  [scout.Action.ActionStyle.BUTTON, 'button']
];
