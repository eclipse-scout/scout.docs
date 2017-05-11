jswidgets.WorldLookupCall = function() {
  jswidgets.WorldLookupCall.parent.call(this);
};
scout.inherits(jswidgets.WorldLookupCall, jswidgets.StaticLookupCall);

jswidgets.WorldLookupCall.prototype._data = function() {
  return jswidgets.WorldLookupCall.DATA;
};

jswidgets.WorldLookupCall.prototype.getByText = function(text) {
  this._newDeferred();
  setTimeout(function() {
    // this map contains all data elements, for easier access by key (index 1)
    var dataMap = {};
    jswidgets.WorldLookupCall.DATA.forEach(function(data) {
      dataMap[data[1]] = data;
    });

    // 1. find nodes that match the search text
    var datas = jswidgets.WorldLookupCall.DATA.filter(function(data) {
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
  }.bind(this), 200);
  return this.deferred.promise();
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
