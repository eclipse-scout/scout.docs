jswidgets.StaticLookupCall = function() {
  jswidgets.StaticLookupCall.parent.call(this);
};
scout.inherits(jswidgets.StaticLookupCall, scout.LookupCall);

jswidgets.StaticLookupCall.MAX_ROW_COUNT = 100;

jswidgets.StaticLookupCall.prototype.getAll = function() {
  this._newDeferred();
  setTimeout(function() {
    var datas = this._data().slice(0, jswidgets.StaticLookupCall.MAX_ROW_COUNT + 1);
    this.resolveLookup({
      lookupRows: datas.map(this._dataToLookupRow)
    });
  }.bind(this), 300);
  return this.deferred.promise();
};

jswidgets.StaticLookupCall.prototype.getByText = function(text) {
  this._newDeferred();
  setTimeout(function() {
    var datas = this._data().filter(function(data) {
      return scout.strings.startsWith(data[0].toLowerCase(), text.toLowerCase());
    });
    this.resolveLookup({
      lookupRows: datas.map(this._dataToLookupRow)
    });
  }.bind(this), 200);
  return this.deferred.promise();
};

jswidgets.StaticLookupCall.prototype.getByKey = function(key) {
  this._newDeferred();
  setTimeout(function() {
    var data = scout.arrays.find(this._data(), function(data) {
      return data[1] === key;
    });
    if (data) {
      this.resolveLookup(this._dataToLookupRow(data));
    } else {
      this.deferred.reject();
    }
  }.bind(this), 100);
  return this.deferred.promise();
};


jswidgets.StaticLookupCall.prototype.resolveLookup = function(lookupResult) {
  this.deferred.resolve(lookupResult);
};

jswidgets.StaticLookupCall.prototype._newDeferred = function() {
  if (this.deferred) {
    this.deferred.reject({
      canceled: true
    });
  }
  this.deferred = $.Deferred();
};

jswidgets.StaticLookupCall.prototype._dataToLookupRow = function(data) {
  return new scout.LookupRow(data[1], data[0]);
};

jswidgets.StaticLookupCall.prototype._data = function() {
  throw new Error('_data not implemented');
};

