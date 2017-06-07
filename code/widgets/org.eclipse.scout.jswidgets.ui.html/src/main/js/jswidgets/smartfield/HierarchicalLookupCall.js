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
jswidgets.HierarchicalLookupCall = function() {
  jswidgets.HierarchicalLookupCall.parent.call(this);

  this.setDelay(250);
};
scout.inherits(jswidgets.HierarchicalLookupCall, scout.StaticLookupCall);

jswidgets.HierarchicalLookupCall.prototype._data = function() {
  return jswidgets.HierarchicalLookupCall.DATA;
};

jswidgets.HierarchicalLookupCall.prototype.getByText = function(text) {
  this._newDeferred();
  setTimeout(function() {
    // this map contains all data elements, for easier access by key (index 1)
    var dataMap = {};
    jswidgets.HierarchicalLookupCall.DATA.forEach(function(data) {
      dataMap[data[1]] = data;
    });

    // 1. find nodes that match the search text
    var datas = jswidgets.HierarchicalLookupCall.DATA.filter(function(data) {
      return scout.strings.startsWith(data[1].toLowerCase(), text.toLowerCase());
    });

    // 2. for each found node, make sure that all its parent nodes up to the root
    //    are in the search result. The map prevents duplicates
    var resultMap = {};
    datas.forEach(function(data) {
      resultMap[data[0]] = data;

      while(data[2]) {
        data = dataMap[data[2]];
        resultMap[data[0]] = data;
      }
    });

    // 3. convert the result in an array again
    datas = scout.objects.values(resultMap);

    this.resolveLookup({
      lookupRows: datas.map(this._dataToLookupRow)
    });
  }.bind(this), 200);
  return this.deferred.promise();
};

jswidgets.HierarchicalLookupCall.prototype._dataToLookupRow = function(data) {
  var lookupRow = new scout.LookupRow(data[0], data[1]);
  lookupRow.parentKey = data[2];
  if (lookupRow.parentKey) {
    lookupRow.iconId = scout.icons.WORLD;
  } else {
    lookupRow.iconId = scout.icons.FOLDER;
  }
  return lookupRow;
};
