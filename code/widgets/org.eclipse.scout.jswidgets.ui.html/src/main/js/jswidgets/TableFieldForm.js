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

  this.table = this.widget('Table');
  this.widget('AddRowsMenu').on('doAction', this._onAddRowsMenuAction.bind(this));
  this.widget('GroupingStyleTop').on('doAction', this._onGroupingStyleAction.bind(this, scout.Table.GroupingStyle.TOP));
  this.widget('GroupingStyleBottom').on('doAction', this._onGroupingStyleAction.bind(this, scout.Table.GroupingStyle.BOTTOM));
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

jswidgets.TableFieldForm.prototype._onGroupingStyleAction = function(groupingStyle) {
  this.table.setProperty('groupingStyle', groupingStyle);
};