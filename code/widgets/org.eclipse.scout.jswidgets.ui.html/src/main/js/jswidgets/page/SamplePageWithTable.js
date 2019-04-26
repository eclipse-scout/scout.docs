jswidgets.SamplePageWithTable = function() {
  jswidgets.SamplePageWithTable.parent.call(this);

  this._loadingTimeoutId = null;
};
scout.inherits(jswidgets.SamplePageWithTable, scout.PageWithTable);

jswidgets.SamplePageWithTable.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.SamplePageWithTable');
};

jswidgets.SamplePageWithTable.prototype._init = function(model) {
  jswidgets.SamplePageWithTable.parent.prototype._init.call(this, model);
  this.detailTable.widget('LoadingDelayCheckBox').on('propertyChange', function(event) {
    if (event.propertyName === 'value') {
      this.detailTable.widget('LoadingDelayField').setEnabled(event.newValue);
    }
  }.bind(this));
};

jswidgets.SamplePageWithTable.prototype._loadTableData = function(searchFilter) {
  var searchFormStringFieldValue = searchFilter.stringField;
  var filter = function(element) {
    if (!scout.strings.hasText(searchFormStringFieldValue)) {
      return true;
    }
    return scout.strings.contains(element.string, searchFormStringFieldValue);
  };
  var data = [{
    id: 1,
    string: 'string 01',
    number: 103012,
    bool: true
  }, {
    id: 2,
    string: 'string 02',
    number: 5685,
    bool: true
  }, {
    id: 3,
    string: 'string 03',
    number: 9214575,
    bool: false
  }, {
    id: 4,
    string: 'string 04',
    number: 168461,
    bool: false
  }, {
    id: 5,
    string: 'string 05',
    number: 959161,
    bool: true
  }].filter(filter);

  var deferred = $.Deferred();
  if (this._loadingTimeoutId) {
    clearTimeout(this._loadingTimeoutId);
    this._loadingTimeoutId = null;
  }
  var delay = 0;
  if (this.detailTable.widget('LoadingDelayCheckBox').value) {
    delay = this.detailTable.widget('LoadingDelayField').value;
  }
  if (delay) {
    // Simulate network
    this._loadingTimeoutId = setTimeout(function() {
      this._loadingTimeoutId = null;
      deferred.resolve(data);
    }.bind(this), delay);
  } else {
    deferred.resolve(data);
  }
  return deferred.promise();
};

jswidgets.SamplePageWithTable.prototype._transformTableDataToTableRows = function(tableData) {
  return tableData
    .map(function(row) {
      return {
        data: row,
        cells: [
          row.id,
          row.string,
          row.number,
          row.bool
        ]
      };
    });
};

jswidgets.SamplePageWithTable.prototype.createChildPage = function(row) {
  return scout.create('jswidgets.SamplePageWithDetailForm', {
    parent: this.getOutline(),
    row: row
  });
};
