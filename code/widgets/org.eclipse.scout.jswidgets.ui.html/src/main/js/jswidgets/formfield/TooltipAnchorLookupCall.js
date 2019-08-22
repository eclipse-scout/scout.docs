/*
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
jswidgets.TooltipAnchorLookupCall = function() {
  jswidgets.TooltipAnchorLookupCall.parent.call(this);
};
scout.inherits(jswidgets.TooltipAnchorLookupCall, scout.StaticLookupCall);

jswidgets.TooltipAnchorLookupCall.prototype._data = function() {
  return jswidgets.TooltipAnchorLookupCall.DATA;
};

jswidgets.TooltipAnchorLookupCall.DATA = [
  [scout.FormField.TooltipAnchor.DEFAULT, 'default'],
  [scout.FormField.TooltipAnchor.ON_FIELD, 'on field']
];
