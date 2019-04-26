jswidgets.SamplePageWithDetailForm = function() {
  jswidgets.SamplePageWithDetailForm.parent.call(this);
};
scout.inherits(jswidgets.SamplePageWithDetailForm, scout.PageWithTable);

jswidgets.SamplePageWithDetailForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.SamplePageWithDetailForm');
};

jswidgets.SamplePageWithDetailForm.prototype.createDetailForm = function() {
  return scout.create('jswidgets.SampleDetailForm', {
    parent: this.parent,
    page: this
  });
};

jswidgets.SamplePageWithDetailForm.prototype._loadTableData = function(searchFilter) {
  var data = [];
  for (var i = 0; i < 10; i++) {
    data.push({
      id: i,
      name: 'Person ' + String.fromCharCode('A'.charCodeAt(0) + i)
    });
  }
  return $.resolvedPromise(data);
};

jswidgets.SamplePageWithDetailForm.prototype._transformTableDataToTableRows = function(tableData) {
  return tableData.map(function(row) {
      return {
        data: row,
        cells: [
          row.id,
          row.name
        ]
      };
    });
};
