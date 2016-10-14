jswidgets.TableFieldForm = function() {
  jswidgets.TableFieldForm.parent.call(this);
};
scout.inherits(jswidgets.TableFieldForm, scout.Form);

jswidgets.TableFieldForm.prototype._init = function(model) {
  jswidgets.TableFieldForm.parent.prototype._init.call(this, model);

  var bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.getWidgetById("DetailBox"));

  this.addRowsMenu = this.getWidgetById("AddRowsMenu");
  this.addRowsMenu.on('doAction', this._onAddRowsMenuDoAction.bind(this));

  this.table = this.getWidgetById("Table");
};

jswidgets.TableFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.TableFieldForm');
};

jswidgets.TableFieldForm.prototype._onAddRowsMenuDoAction = function(event) {
  this.table.insertRow({
    cells: ['First cell', 4]
  });
};
