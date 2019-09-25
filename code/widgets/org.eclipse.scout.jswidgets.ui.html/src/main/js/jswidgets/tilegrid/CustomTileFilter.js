jswidgets.CustomTileFilter = function(model) {
  model = model || {};
  this.text = null;
  this.setText(model.text);
};

jswidgets.CustomTileFilter.prototype.setText = function(text) {
  this.text = text || '';
  this.text = this.text.toLowerCase();
};

jswidgets.CustomTileFilter.prototype.accept = function(tile) {
  var label = '';
  if (tile instanceof jswidgets.CustomTile) {
    label = tile.label || '';
  } else if (tile instanceof scout.HtmlTile) {
    label = scout.strings.plainText(tile.content) || '';
  }
  var filterText = label.trim().toLowerCase();
  return filterText.indexOf(this.text) > -1;
};
