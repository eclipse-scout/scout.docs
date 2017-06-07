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
jswidgets.LabelWidthInPixelLookupCall = function() {
  jswidgets.LabelWidthInPixelLookupCall.parent.call(this);
};
scout.inherits(jswidgets.LabelWidthInPixelLookupCall, scout.StaticLookupCall);

jswidgets.LabelWidthInPixelLookupCall.prototype._data = function() {
  return jswidgets.LabelWidthInPixelLookupCall.DATA;
};

jswidgets.LabelWidthInPixelLookupCall.DATA = [
  [scout.FormField.LabelWidth.DEFAULT, 'default'],
  [scout.FormField.LabelWidth.UI, 'ui width'],
];
