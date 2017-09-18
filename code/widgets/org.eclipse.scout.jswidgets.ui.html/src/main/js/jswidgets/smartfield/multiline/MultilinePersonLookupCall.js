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
jswidgets.MultilinePersonLookupCall = function() {
  jswidgets.MultilinePersonLookupCall.parent.call(this);
};
scout.inherits(jswidgets.MultilinePersonLookupCall, scout.StaticLookupCall);

jswidgets.MultilinePersonLookupCall.prototype._data = function() {
  return jswidgets.MultilinePersonLookupCall.DATA;
};

jswidgets.MultilinePersonLookupCall.DATA = [
  [1, 'Rachel Olson\nEcuador'],
  [2, 'Stephanie Simmons\nBrazil'],
  [3, 'Larry Hudson\nJapan'],
  [4, 'Phyllis Chapman\nRussia'],
  [5, 'Melissa Nichols\nIndonesia']
];
