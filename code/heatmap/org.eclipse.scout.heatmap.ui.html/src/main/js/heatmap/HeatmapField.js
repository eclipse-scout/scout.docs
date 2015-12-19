scout.HeatmapField = function() {
  scout.HeatmapField.parent.call(this);
};
scout.inherits(scout.HeatmapField, scout.FormField);

scout.HeatmapField.prototype._renderProperties = function() {
  scout.HeatmapField.parent.prototype._renderProperties.call(this);
  this._renderViewParameter();
};

scout.HeatmapField.prototype._render = function($parent) {
  this.addContainer($parent, 'heatmap-field');
  this.addLabel();
  this.addMandatoryIndicator();
  
  this.$button = this.$container.makeElement('<button>')
    .text('Click me')
    .on('click', this._onButtonClick.bind(this));
    
  this.$info = this.$container.makeDiv('heatmap').text('Info');



  var $field = this.$container.makeDiv('heatmap')
    .append(this.$button)
    .append(this.$info);
  this.addField($field);
};

scout.HeatmapField.prototype._onButtonClick = function(event) {
  this._send('viewParameterChanged', {
    center: {
      x: 1,
      y: 2
    },
    zoomFactor: 10
  });
};

scout.HeatmapField.prototype._renderViewParameter = function() {
  this.$info.text(this.viewParameter.zoomFactor);
};