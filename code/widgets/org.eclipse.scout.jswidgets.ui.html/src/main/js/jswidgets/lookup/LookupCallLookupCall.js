/*******************************************************************************
 * Copyright (c) 2014-2018 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
jswidgets.LookupCallLookupCall = function() {
  jswidgets.LookupCallLookupCall.parent.call(this);
};
scout.inherits(jswidgets.LookupCallLookupCall, scout.StaticLookupCall);

jswidgets.LookupCallLookupCall.prototype._queryByKey = function(deferred, key) {
  if (key instanceof scout.LookupCall) {
    deferred.resolve({
      queryBy: scout.QueryBy.KEY,
      lookupRows: [{
        key: key,
        text: key.objectType,
        enabled: true
      }]
    });
    return;
  }
  var data = scout.arrays.find(this.data, function(data) {
    return data[0] === key;
  });
  if (data) {
    deferred.resolve({
      queryBy: scout.QueryBy.KEY,
      lookupRows: [this._dataToLookupRow(data)]
    });
  } else {
    deferred.reject();
  }
};

jswidgets.LookupCallLookupCall.prototype._data = function() {
  return [
    [scout.create('jswidgets.LocaleLookupCall', {
      session: this.session
    }), 'jswidgets.LocaleLookupCall', 1],
    [scout.create('jswidgets.RainbowLookupCall', {
      session: this.session
    }), 'jswidgets.RainbowLookupCall', 1],
    [scout.create('jswidgets.SalutationLookupCall', {
      session: this.session
    }), 'jswidgets.SalutationLookupCall', 1]
  ];
};
