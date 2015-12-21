scout.HeatmapFieldLayout = function(field) {
  this.field = field;
};
scout.inherits(scout.HeatmapFieldLayout, scout.AbstractLayout);

/**
 * @override AbstractLayout.js
 */
scout.HeatmapFieldLayout.prototype.layout = function($container) {
  this.field.heatmap._onResize();
};
