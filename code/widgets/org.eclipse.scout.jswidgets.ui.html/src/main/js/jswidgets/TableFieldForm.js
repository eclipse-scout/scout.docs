jswidgets.TableFieldForm = function() {
  jswidgets.TableFieldForm.parent.call(this);

  this.seqNo = 1;
  this.groupSeqNo = 1;
};
scout.inherits(jswidgets.TableFieldForm, scout.Form);

jswidgets.TableFieldForm.GROUP_SIZE = 2;

jswidgets.TableFieldForm.prototype._init = function(model) {
  jswidgets.TableFieldForm.parent.prototype._init.call(this, model);

  var bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.widget('DetailBox'));

  this.widget('AddRowsMenu').on('doAction', this._onAddRowsMenuAction.bind(this));
  this.widget('AggregationStyleTop').on('doAction', function(event) {
    this.table.aggregateStyle = scout.Table.AggregateStyle.TOP;
  }.bind(this));
  this.widget('AggregationStyleBottom').on('doAction', function(event) {
    this.table.aggregateStyle = scout.Table.AggregateStyle.BOTTOM;
  }.bind(this));

  this.table = this.widget('Table');
};

jswidgets.TableFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TableFieldForm');
};

jswidgets.TableFieldForm.prototype._onAddRowsMenuAction = function(event) {
  this.table.insertRow({
    cells: ['Row #' + this.seqNo, this.groupSeqNo]
  });
  if (this.seqNo % jswidgets.TableFieldForm.GROUP_SIZE === 0) {
    this.groupSeqNo++;
  }
  this.seqNo++;
};
