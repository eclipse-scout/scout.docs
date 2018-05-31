widgets.CustomTile = function() {
  widgets.CustomTile.parent.call(this);
  this.label = null;
};
scout.inherits(widgets.CustomTile, scout.Tile);

widgets.CustomTile.prototype._render = function() {
  this.$container = this.$parent.appendDiv('custom-tile');
  this.htmlComp = scout.HtmlComponent.install(this.$container, this.session);
};

widgets.CustomTile.prototype._renderProperties = function() {
  widgets.CustomTile.parent.prototype._renderProperties.call(this);
  this._renderLabel();
};

widgets.CustomTile.prototype._renderLabel = function() {
  this.$container.text(this.label);
};
