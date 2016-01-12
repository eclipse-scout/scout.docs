scout.HeatmapFieldLayout = function(field) {
  this.field = field;
};
scout.inherits(scout.HeatmapFieldLayout, scout.AbstractLayout);

/**
 * @override AbstractLayout.js
 */
scout.HeatmapFieldLayout.prototype.layout = function($container) {
  var htmlContainer = scout.HtmlComponent.get($container);

  // Because of a bug (?) in Leaflet.js, the canvas size must not get smaller than
  // 1x1 pixels, otherwise an exception is thrown: "Failed to execute 'getImageData'
  // on 'CanvasRenderingContext2D': The source width is 0."
  var size = htmlContainer.getSize();
  if (size.width === 0 || size.height === 0) {
    size.width = Math.max(size.width, 1);
    size.height = Math.max(size.height, 1);
    scout.graphics.setSize($container, size);
  }

  this.field.heatmap.invalidateSize();
};
