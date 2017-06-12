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

  this.setDelay(0);
  this.setLoadIncremental(true);
  this.setHierarchical(true);
};
scout.inherits(jswidgets.WorldLookupCall, scout.StaticLookupCall);

jswidgets.WorldLookupCall.prototype._data = function() {
  return jswidgets.WorldLookupCall.DATA;
};

jswidgets.WorldLookupCall.prototype._queryAll = function(deferred) {
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
  deferred.resolve({
    lookupRows: lookupRows
  });
};

/**
 * Creates a map that contains all data elements, for easier access by key (index 1)
 */
jswidgets.WorldLookupCall.prototype._createDataMap = function() {
  var dataMap = {};
  this._data().forEach(function(data) {
    dataMap[data[0]] = data;
  });
  return dataMap;
};

jswidgets.WorldLookupCall.prototype._dataToLookupRow = function(data) {
  var lookupRow = new scout.LookupRow(data[0], data[1]);
  lookupRow.parentKey = data[2];
  if (lookupRow.parentKey) {
    lookupRow.iconId = scout.icons.WORLD;
  } else {
    lookupRow.iconId = scout.icons.FOLDER;
  }
  return lookupRow;
};

// 0: key
// 1: text
// 2: [parentKey]
jswidgets.WorldLookupCall.DATA = [
  ['AF', 'Africa', null],
  ['EAF', 'Eastern Africa', 'AF'],
  ['MAF', 'Middle Africa', 'AF'],
  ['NAF', 'Northern Africa', 'AF'],
  ['SAF', 'Southern Africa', 'AF'],
  ['WAF', 'Western Africa', 'AF'],
  ['AM', 'Americas', null],
  ['LAM', 'Latin America', 'AM'],
  ['SAM', 'South America', 'LAM'],
  ['CARAM', 'Caribbean', 'LAM'],
  ['CAM', 'Central America', 'LAM'],
  ['NAM', 'Northern America', 'AM'],
  ['AN', 'Antarctica', null],
  ['AS', 'Asia', null],
  ['CAS', 'Central Asia', 'AS'],
  ['EAS', 'Eastern Asia', 'AS'],
  ['SAS', 'Southern Asia', 'AS'],
  ['SEAS', 'South-Eastern Asia', 'AS'],
  ['WAS', 'Western Asia', 'AS'],
  ['ER', 'Europe', null],
  ['EER', 'Eastern Europe', 'ER'],
  ['NER', 'Northern Europe', 'ER'],
  ['SER', 'Southern Europe', 'ER'],
  ['WER', 'Western Europe', 'ER'],
  ['OC', 'Oceania', null],
];
