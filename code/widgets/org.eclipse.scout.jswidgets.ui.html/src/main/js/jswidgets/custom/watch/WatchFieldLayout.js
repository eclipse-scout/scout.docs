jswidgets.WatchFieldLayout = function(watchField) {
  jswidgets.WatchFieldLayout.parent.call(this);
  this.watchField = watchField;
};
scout.inherits(jswidgets.WatchFieldLayout, scout.NullLayout);

jswidgets.WatchFieldLayout.prototype.layout = function($container) {
  var containerHeight = $container.height(),
    containerWidth = $container.width(),
    size = Math.min(containerHeight, containerWidth),
    $canvas = this.watchField.$canvas;
  // set width and height
  $canvas.prop('width', size);
  $canvas.prop('height', size);
  // set css size and position
  $canvas
    .cssTop((containerHeight - size) / 2)
    .cssRight((containerWidth - size) / 2)
    .cssHeight(size)
    .cssWidth(size);

  this.watchField._paintWatch();
  jswidgets.WatchFieldLayout.parent.prototype.layout.call(this, $container);
};
