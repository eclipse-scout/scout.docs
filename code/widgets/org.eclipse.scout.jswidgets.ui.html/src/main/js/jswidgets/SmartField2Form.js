jswidgets.SmartField2Form = function() {
  jswidgets.SmartField2Form.parent.call(this);
};
scout.inherits(jswidgets.SmartField2Form, scout.Form);

jswidgets.SmartField2Form.GROUP_SIZE = 2;

jswidgets.SmartField2Form.prototype._init = function(model) {
  jswidgets.SmartField2Form.parent.prototype._init.call(this, model);

  var bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.widget('MainBox'));
  bodyGrid.validate(this.widget('TableSmartFieldBox'));
  bodyGrid.validate(this.widget('TreeSmartFieldBox'));

  this.smartField = this.widget('TableSmartField');
  this.valueField = this.widget('ValueField');

  this.widget('SetValueButton').on('click', function(event) {
    var value = this.valueField.value;
    this.smartField.setValue(value);
  }.bind(this));


  var treeSmartField = this.widget('TreeSmartField');
  var expandTreeNodesField = this.widget('ExpandTreeNodesField');

  expandTreeNodesField.setValue(treeSmartField.browseAutoExpandAll);
  expandTreeNodesField.on('propertyChange', function(event) {
    if (scout.arrays.containsAny(event.changedProperties, ['value'])) {
      treeSmartField.setBrowseAutoExpandAll(event.source.value);
    }
  });
};

jswidgets.SmartField2Form.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.SmartField2Form');
};

