jswidgets.CustomTile = function() {
  jswidgets.CustomTile.parent.call(this);
  this.label = null;
  this.displayStyle = scout.Tile.DisplayStyle.PLAIN;
};
scout.inherits(jswidgets.CustomTile, scout.Tile);

jswidgets.CustomTile.prototype._render = function() {
  this.$container = this.$parent.appendDiv('custom-tile');
  this.htmlComp = scout.HtmlComponent.install(this.$container, this.session);
};

jswidgets.CustomTile.prototype._renderProperties = function() {
  jswidgets.CustomTile.parent.prototype._renderProperties.call(this);
  this._renderLabel();
};

jswidgets.CustomTile.prototype.setLabel = function(label) {
  this.setProperty('label', label);
};

jswidgets.CustomTile.prototype._renderLabel = function() {
  this.$container.text(this.label);
};
