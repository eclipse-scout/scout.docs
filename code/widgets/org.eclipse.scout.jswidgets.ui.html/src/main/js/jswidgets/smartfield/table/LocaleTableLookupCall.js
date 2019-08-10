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
jswidgets.LocaleTableLookupCall = function() {
  jswidgets.LocaleTableLookupCall.parent.call(this);
};
scout.inherits(jswidgets.LocaleTableLookupCall, jswidgets.LocaleLookupCall);

jswidgets.LocaleTableLookupCall.prototype._dataToLookupRow = function(data) {
  var lookupRow = new scout.create('LookupRow', {
    key: data[0],
    text: data[1],
    additionalTableRowData: {
      tag: data[0]
    }
  });

  return lookupRow;
};
