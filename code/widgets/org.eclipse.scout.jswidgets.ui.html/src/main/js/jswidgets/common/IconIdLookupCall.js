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
jswidgets.IconIdLookupCall = function() {
  jswidgets.IconIdLookupCall.parent.call(this);
};
scout.inherits(jswidgets.IconIdLookupCall, scout.StaticLookupCall);

jswidgets.IconIdLookupCall.prototype._data = function() {
  return Object.keys(scout.icons)
    .filter(function(name) {
      var value = scout.icons[name];
      return typeof value === 'string' && scout.strings.startsWith(value, 'font:');
    }).map(function(name, i) {
      var iconId = scout.icons[name];
      return [iconId, name];
    });
};
