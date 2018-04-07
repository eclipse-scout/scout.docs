jswidgets.SimpleTileFilter = function(model) {
  model = model || {};
  this.text = null;
  this.setText(model.text);
};

jswidgets.SimpleTileFilter.prototype.setText = function(text) {
  this.text = text || '';
  this.text = this.text.toLowerCase();
};

jswidgets.SimpleTileFilter.prototype.accept = function(tile) {
  var label = tile.label || '';
  var filterText = label.trim().toLowerCase();
  return filterText.indexOf(this.text) > -1;
};
