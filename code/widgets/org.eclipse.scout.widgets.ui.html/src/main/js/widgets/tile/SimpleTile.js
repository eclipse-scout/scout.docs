widgets.SimpleTile = function() {
  widgets.SimpleTile.parent.call(this);
  this.label = null;
};
scout.inherits(widgets.SimpleTile, scout.Tile);

widgets.SimpleTile.prototype._render = function() {
  this.$container = this.$parent.appendDiv('simple-tile');
  this.htmlComp = scout.HtmlComponent.install(this.$container, this.session);
};

widgets.SimpleTile.prototype._renderProperties = function() {
  widgets.SimpleTile.parent.prototype._renderProperties.call(this);
  this._renderLabel();
};

widgets.SimpleTile.prototype._renderLabel = function() {
  this.$container.text(this.label);
};
