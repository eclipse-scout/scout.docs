jswidgets.SmartField2Form = function() {
  jswidgets.SmartField2Form.parent.call(this);
};
scout.inherits(jswidgets.SmartField2Form, scout.Form);

jswidgets.SmartField2Form.GROUP_SIZE = 2;

jswidgets.SmartField2Form.prototype._init = function(model) {
  jswidgets.SmartField2Form.parent.prototype._init.call(this, model);

  var bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.widget('DetailBox'));

  this.smartField = this.widget('DefaultField');
  this.valueField = this.widget('ValueField');

  this.widget('SetValueButton').on('click', function(event) {
    var value = this.valueField.value;
    this.smartField.setValue(value);
  }.bind(this));
};

jswidgets.SmartField2Form.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.SmartField2Form');
};

