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
jswidgets.ClearableStyleLookupCall = function() {
  jswidgets.ClearableStyleLookupCall.parent.call(this);
};
scout.inherits(jswidgets.ClearableStyleLookupCall, scout.StaticLookupCall);

jswidgets.ClearableStyleLookupCall.prototype._data = function() {
  return jswidgets.ClearableStyleLookupCall.DATA;
};

jswidgets.ClearableStyleLookupCall.DATA = [
  [scout.ValueField.Clearable.FOCUSED, 'Focused'],
  [scout.ValueField.Clearable.ALWAYS, 'Always'],
  [scout.ValueField.Clearable.NEVER, 'Never']
];
