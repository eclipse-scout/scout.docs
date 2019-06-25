jswidgets.SamplePageWithTableDetailTable = function() {
  jswidgets.SamplePageWithTableDetailTable.parent.call(this);
};
scout.inherits(jswidgets.SamplePageWithTableDetailTable, scout.Table);

jswidgets.SamplePageWithTableDetailTable.prototype._init = function(model) {
  jswidgets.SamplePageWithTableDetailTable.parent.prototype._init.call(this, model);

  this.widget('AddRowMenu').on('action', this._onAddRowMenuAction.bind(this));
  this.widget('AddManyMenu').on('action', this._onAddManyMenuAction.bind(this));
  this.widget('DeleteRowMenu').on('action', this._onDeleteRowMenuAction.bind(this));
};

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

jswidgets.SamplePageWithTableDetailTable.prototype._onAddRowMenuAction = function() {
  this.insertRow(this._createRow());
};

jswidgets.SamplePageWithTableDetailTable.prototype._onAddManyMenuAction = function() {
  for (var i = 0; i < 10; i++) {
    this.insertRow(this._createRow());
  }
};

jswidgets.SamplePageWithTableDetailTable.prototype._onDeleteRowMenuAction = function() {
  this.deleteRows(this.selectedRows);
};

jswidgets.SamplePageWithTableDetailTable.prototype._createRow = function() {
  var strings = ['string 01', 'string 02', 'string 03', 'string 04', 'string 05'];
  var randomNumber = Math.floor(Math.random() * strings.length);

  var row = {
    id: this.rows.length + 1,
    //    string: 'string ' + (this.rows.length + 1),
    string: strings[randomNumber],
//    number: Math.floor(Math.random() * Math.floor(999999)),
    number: 2000,
    bool: Math.random() >= 0.5
  };
  return {
    data: row,
    cells: [
      row.id,
      row.string,
      row.number,
      row.bool
    ]
  };
};
