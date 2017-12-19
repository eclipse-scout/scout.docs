jswidgets.SimpleTile = function() {
  jswidgets.SimpleTile.parent.call(this);
  this.label = null;
};
scout.inherits(jswidgets.SimpleTile, scout.Tile);

jswidgets.SimpleTile.prototype._render = function() {
  this.$container = this.$parent.appendDiv('simple-tile');
  this.htmlComp = scout.HtmlComponent.install(this.$container, this.session);
};

jswidgets.SimpleTile.prototype._renderProperties = function() {
  jswidgets.SimpleTile.parent.prototype._renderProperties.call(this);
  this._renderLabel();
};

jswidgets.SimpleTile.prototype.setLabel = function(label) {
  this.setProperty('label', label);
};

jswidgets.SimpleTile.prototype._renderLabel = function() {
  this.$container.text(this.label);
};
