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
 

  var heatmapId = scout.objectFactory.createUniqueId();
  var $field = this.$container
    .makeDiv('heatmap')
    .attr('id', heatmapId);
  this.addField($field);
  
  var fieldHtmlComp = new scout.HtmlComponent($field, this.session);
  fieldHtmlComp.setSize(new scout.Dimension(1,1));
  fieldHtmlComp.setLayout(new scout.HeatmapFieldLayout(this));
   
  this.heatmap = L.map(heatmapId, {trackResize: false});
  var tiles = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
  }).addTo(this.heatmap);
  
  var addressPoints = [
                       [47.3914, 8.51180, 3.0],
                       [47.39155, 8.5119, 3.0],
                       [47.3915, 8.51185, 3.0],
                       [47.3916, 8.51170, 2.0]
                       ];
  
//  addressPoints = addressPoints.map(function (p) { return [p[0], p[1]]; });
  var heat = L.heatLayer(addressPoints, {radius: 20, max: 5.0}).addTo(this.heatmap);

  this.heatmap.on('zoomend', this._handleViewParameterChanged.bind(this));
  this.heatmap.on('moveend', this._handleViewParameterChanged.bind(this));
  
  
};

scout.HeatmapField.prototype._handleViewParameterChanged = function() {
	this._send('viewParameterChanged', {
		center: {
			x: this.heatmap.getCenter().lat,
			y: this.heatmap.getCenter().lng
		},
		zoomFactor: this.heatmap.getZoom()
	});
};

scout.HeatmapField.prototype._renderViewParameter = function() {
	  this.heatmap.setView([this.viewParameter.center.x, this.viewParameter.center.y],this.viewParameter.zoomFactor);
};