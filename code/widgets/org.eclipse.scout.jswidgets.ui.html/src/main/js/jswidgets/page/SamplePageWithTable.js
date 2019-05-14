jswidgets.SamplePageWithTable = function() {
  jswidgets.SamplePageWithTable.parent.call(this);
};
scout.inherits(jswidgets.SamplePageWithTable, scout.PageWithTable);

jswidgets.SamplePageWithTable.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.SamplePageWithTable');
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
  }];
  return $.resolvedPromise(data.filter(filter));
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
