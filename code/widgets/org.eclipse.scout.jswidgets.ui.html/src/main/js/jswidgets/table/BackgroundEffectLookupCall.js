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
jswidgets.BackgroundEffectLookupCall = function() {
  jswidgets.BackgroundEffectLookupCall.parent.call(this);
};
scout.inherits(jswidgets.BackgroundEffectLookupCall, scout.StaticLookupCall);

jswidgets.BackgroundEffectLookupCall.prototype._data = function() {
  return jswidgets.BackgroundEffectLookupCall.DATA;
};

jswidgets.BackgroundEffectLookupCall.DATA = [
  ['colorGradient1', 'colorGradient1'],
  ['colorGradient2', 'colorGradient2'],
  ['barChart', 'barChart']
];
