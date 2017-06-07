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
jswidgets.BorderDecorationLookupCall = function() {
  jswidgets.BorderDecorationLookupCall.parent.call(this);
};
scout.inherits(jswidgets.BorderDecorationLookupCall, scout.StaticLookupCall);

jswidgets.BorderDecorationLookupCall.prototype._data = function() {
  return jswidgets.BorderDecorationLookupCall.DATA;
};

jswidgets.BorderDecorationLookupCall.DATA = [
  [scout.GroupBox.BorderDecoration.AUTO, 'auto'],
  [scout.GroupBox.BorderDecoration.EMPTY, 'empty'],
  [scout.GroupBox.BorderDecoration.LINE, 'line']
];
