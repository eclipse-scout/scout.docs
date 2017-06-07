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
jswidgets.LabelPositionLookupCall = function() {
  jswidgets.LabelPositionLookupCall.parent.call(this);
};
scout.inherits(jswidgets.LabelPositionLookupCall, scout.StaticLookupCall);

jswidgets.LabelPositionLookupCall.prototype._data = function() {
  return jswidgets.LabelPositionLookupCall.DATA;
};

jswidgets.LabelPositionLookupCall.DATA = [
  [scout.FormField.LabelPosition.DEFAULT, 'default'],
  [scout.FormField.LabelPosition.LEFT, 'left'],
  [scout.FormField.LabelPosition.ON_FIELD, 'on field'],
  [scout.FormField.LabelPosition.TOP, 'top']
];
