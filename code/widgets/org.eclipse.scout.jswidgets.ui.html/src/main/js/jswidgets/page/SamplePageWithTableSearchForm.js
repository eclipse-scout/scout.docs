jswidgets.SamplePageWithTableSearchForm = function() {
  jswidgets.SamplePageWithTableSearchForm.parent.call(this);
};
scout.inherits(jswidgets.SamplePageWithTableSearchForm, scout.Form);

jswidgets.SamplePageWithTableSearchForm.prototype._init = function(model) {
  jswidgets.SamplePageWithTableSearchForm.parent.prototype._init.call(this, model);
  this._initListeners();
};

jswidgets.SamplePageWithTableSearchForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.SamplePageWithTableSearchForm');
};

jswidgets.SamplePageWithTableSearchForm.prototype._initListeners = function() {
  var parentTable = this.parent.table;
  this.widget('SearchButton').on('action', parentTable.reload.bind(parentTable), scout.Table.ReloadReason.SEARCH);
};

jswidgets.SamplePageWithTableSearchForm.prototype.exportData = function() {
  return {
    stringField: this.widget('StringField').value
  };
};
