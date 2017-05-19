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
jswidgets.WorldLookupCall = function() {
  jswidgets.WorldLookupCall.parent.call(this);

  this.setDelay(250);
  this.setLoadIncremental(true);
  this.setHierarchical(true);
};
scout.inherits(jswidgets.WorldLookupCall, scout.StaticLookupCall);

jswidgets.WorldLookupCall.prototype._data = function() {
  return jswidgets.WorldLookupCall.DATA;
};

jswidgets.WorldLookupCall.prototype._queryAll = function() {
  var lookupRows;
  if (this.loadIncremental) {
    // only select root nodes
    lookupRows = [];
    this._data().forEach(function(data) {
      if (data[2] === null) {
        lookupRows.push(this._dataToLookupRow(data));
      }
    }, this);
  } else {
    lookupRows = this._data().map(this._dataToLookupRow);
  }
  this.resolveLookup({
    lookupRows: lookupRows
  });
};

jswidgets.WorldLookupCall.prototype.getByRec = function(rec) {
  this._newDeferred();
  setTimeout(function() {

    var lookupRows = [];
    this._data().forEach(function(data) {
      if (data[2] === rec) {
        lookupRows.push(this._dataToLookupRow(data));
      }
    }, this);

    this.resolveLookup({
      lookupRows: lookupRows
    });

  }.bind(this), this._delay);
  return this.deferred.promise();
};

/**
 * Creates a map that contains all data elements, for easier access by key (index 1)
 */
jswidgets.WorldLookupCall.prototype._createDataMap = function() {
  var dataMap = {};
  this._data().forEach(function(data) {
    dataMap[data[1]] = data;
  });
  return dataMap;
};

jswidgets.WorldLookupCall.prototype._queryByText = function(text) {
  var dataMap = this._createDataMap();

  // 1. find nodes that match the search text
  var datas = this._data().filter(function(data) {
    return scout.strings.startsWith(data[0].toLowerCase(), text.toLowerCase());
  });

  // 2. for each found node, make sure that all its parent nodes up to the root
  //    are in the search result. The map prevents duplicates
  var resultMap = {};
  datas.forEach(function(data) {
    resultMap[data[1]] = data;

    while(data[2]) {
      data = dataMap[data[2]];
      resultMap[data[1]] = data;
    }
  });

  // 3. convert the result in an array again
  datas = scout.objects.values(resultMap);

  this.resolveLookup({
    lookupRows: datas.map(this._dataToLookupRow)
  });
};

jswidgets.WorldLookupCall.prototype._dataToLookupRow = function(data) {
  var lookupRow = new scout.LookupRow(data[1], data[0]);
  lookupRow.parentKey = data[2];
  if (lookupRow.parentKey) {
    lookupRow.iconId = scout.icons.WORLD;
  } else {
    lookupRow.iconId = scout.icons.FOLDER;
  }
  return lookupRow;
};

// 0: text
// 1: key
// 2: [parentKey]
jswidgets.WorldLookupCall.DATA = [
  ['Africa', 'AF', null],
  ['Eastern Africa', 'EAF', 'AF'],
  ['Middle Africa', 'MAF', 'AF'],
  ['Northern Africa', 'NAF', 'AF'],
  ['Southern Africa', 'SAF', 'AF'],
  ['Western Africa', 'WAF', 'AF'],
  ['Americas', 'AM', null],
  ['Latin America', 'LAM', 'AM'],
  ['South America', 'SAM', 'LAM'],
  ['Caribbean', 'CARAM', 'LAM'],
  ['Central America', 'CAM', 'LAM'],
  ['Northern America', 'NAM', 'AM'],
  ['Antarctica', 'AN', null],
  ['Asia', 'AS', null],
  ['Central Asia', 'CAS', 'AS'],
  ['Eastern Asia', 'EAS', 'AS'],
  ['Southern Asia', 'SAS', 'AS'],
  ['South-Eastern Asia', 'SEAS', 'AS'],
  ['Western Asia', 'WAS', 'AS'],
  ['Europe', 'ER', null],
  ['Eastern Europe', 'EER', 'ER'],
  ['Northern Europe', 'NER', 'ER'],
  ['Southern Europe', 'SER', 'ER'],
  ['Western Europe', 'WER', 'ER'],
  ['Oceania', 'OC', null],
];
