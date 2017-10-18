jswidgets.WatchFieldLayout = function(watchField) {
  jswidgets.WatchFieldLayout.parent.call(this);
  this.watchField = watchField;
};
scout.inherits(jswidgets.WatchFieldLayout, scout.NullLayout);

jswidgets.WatchFieldLayout.prototype.layout = function($container) {
  var size = Math.min($container.height(), $container.width());
  this.watchField.$canvas.prop('width', size);
  this.watchField.$canvas.prop('height', size);
  this.watchField._paintWatch();
  jswidgets.WatchFieldLayout.parent.prototype.layout.call(this, $container);
};
