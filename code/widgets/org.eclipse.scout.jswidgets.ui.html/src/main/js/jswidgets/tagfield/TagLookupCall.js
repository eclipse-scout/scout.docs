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
jswidgets.TagLookupCall = function() {
  jswidgets.TagLookupCall.parent.call(this);
};
scout.inherits(jswidgets.TagLookupCall, scout.StaticLookupCall);

jswidgets.TagLookupCall.prototype._data = function() {
  var tags = jswidgets.TagLookupCall.TAGS;
  tags.sort();

  var data = [];
  tags.forEach(function(tag) {
    data.push([tag, tag]);
  });
  return data;
};

jswidgets.TagLookupCall.TAGS = ['scout','eclipse scout','scout js','eclipse','bsi','business systems integration ag','open source','widgets','js widgets'];
