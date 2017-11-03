jswidgets.TilesFilter = function(model) {
  model = model || {};
  this.text = null;
  this.setText(model.text);
};

jswidgets.TilesFilter.prototype.setText = function(text) {
  this.text = text || '';
  this.text = this.text.toLowerCase();
};

jswidgets.TilesFilter.prototype.accept = function(tile) {
  if (tile instanceof scout.PlaceholderTile) {
    return true;
  }
  var filterText = tile.label.trim().toLowerCase() || '';
  return filterText.indexOf(this.text) > -1;
};
