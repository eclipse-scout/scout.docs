jswidgets.SamplePageWithTableDetailTable = function() {
  jswidgets.SamplePageWithTableDetailTable.parent.call(this);
};
scout.inherits(jswidgets.SamplePageWithTableDetailTable, scout.Table);

jswidgets.SamplePageWithTableDetailTable.prototype.createTileForRow = function(row) {
  var model = {
    parent: this,
    content: '<br><b>ID:</b> ' +
      row.data.id + '<br><b>String Column:</b> ' +
      row.data.string + '<br><b>Number Column:</b> ' +
      row.data.number + '<br><b>Boolean Column:</b> ' +
      row.data.bool
  };
  return new scout.create('HtmlTile', model);
};
