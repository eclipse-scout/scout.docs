jswidgets.SamplePageWithTableTileToggleMenu = function() {
  jswidgets.SamplePageWithTableTileToggleMenu.parent.call(this);
};
scout.inherits(jswidgets.SamplePageWithTableTileToggleMenu, scout.Menu);

jswidgets.SamplePageWithTableTileToggleMenu.prototype._init = function(model) {
  jswidgets.SamplePageWithTableTileToggleMenu.parent.prototype._init.call(this, model);

  this.on('action', function() {
    var table = model.parent;
    table.setTileMode(!table.tileMode);
  }.bind(this));
};
